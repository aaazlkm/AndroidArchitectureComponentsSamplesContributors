package hoge.hogehoge.presentation.repository.contributors

import hoge.hogehoge.domain.entity.Contributor
import hoge.hogehoge.domain.entity.LinkHeader
import hoge.hogehoge.domain.result.Result
import hoge.hogehoge.domain.result.map
import hoge.hogehoge.domain.usecase.GithubUseCase
import hoge.hogehoge.presentation.base.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ContributorsViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : BaseViewModel() {
    sealed class Status {
        object Initialized : Status()
        data class Saved(
            var contributors: List<Contributor>,
            var lastScrollPosition: Int
        ) : Status()
    }

    private var linkHeader: LinkHeader? = null

    //region event

    private val eventOfContributorsProcessor = BehaviorProcessor.createDefault<Result<Boolean>>(Result.onReady())
    val eventOfContributors: Flowable<Result<Boolean>> = eventOfContributorsProcessor.observeOn(AndroidSchedulers.mainThread())

    //endregion

    //region value

    private val isLoadingProcessor = PublishProcessor.create<Boolean>()
    val isLoading: Flowable<Boolean> = isLoadingProcessor.observeOn(AndroidSchedulers.mainThread())

    private var statusProcessor = BehaviorProcessor.createDefault<Status>(Status.Initialized)
    val status: Flowable<Status> = statusProcessor.observeOn(AndroidSchedulers.mainThread())

    private val contributorsInNextPageProcessor = PublishProcessor.create<List<Contributor>>()
    val contributorsInNextPage: Flowable<List<Contributor>> = contributorsInNextPageProcessor.observeOn(AndroidSchedulers.mainThread())

    //endregion

    fun fetchContributorsInNextPage(owner: String, repository: String, needLoading: Boolean = true) {
        linkHeader?.nextPage?.let {
            fetchContributors(owner, repository, it, needLoading)
        } ?: run {
            contributorsInNextPageProcessor.onNext(listOf())
        }
    }

    fun fetchContributors(owner: String, repository: String, page: Int? = null, needLoading: Boolean = true) {
        githubUseCase.fetchContributors(owner, repository, page)
            .subscribe { result ->
                eventOfContributorsProcessor.onNext(result.map { true })

                if (needLoading) isLoadingProcessor.onNext(result is Result.Loading)

                if (result is Result.Success) {
                    this.linkHeader = result.value.first
                    this.contributorsInNextPageProcessor.onNext(result.value.second)
                }
            }
            .addTo(compositeDisposable)
    }

    fun initializeStatus() {
        linkHeader = null
        compositeDisposable.clear()
        statusProcessor.onNext(Status.Initialized)
    }

    fun saveStatus(contributors: List<Contributor>, lastShowedPosition: Int) {
        statusProcessor.onNext(Status.Saved(contributors, lastShowedPosition))
    }
}
