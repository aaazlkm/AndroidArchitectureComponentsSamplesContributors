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
    /** 初期化処理 */
    sealed class Initialization {
        /** 初めて画面を開いた時 */
        object ForFirstTime : Initialization()

        /** 他の画面から戻った時 */
        data class ForBacked(
            var contributors: List<Contributor>,
            var lastScrollPosition: Int
        ) : Initialization()
    }

    private var linkHeader: LinkHeader? = null

    //region event

    private val eventOfContributorsProcessor = BehaviorProcessor.createDefault<Result<Boolean>>(Result.onReady())
    val eventOfContributors: Flowable<Result<Boolean>> = eventOfContributorsProcessor.observeOn(AndroidSchedulers.mainThread())

    //endregion

    //region value

    private val isLoadingProcessor = PublishProcessor.create<Boolean>()
    val isLoading: Flowable<Boolean> = isLoadingProcessor.observeOn(AndroidSchedulers.mainThread())

    private var initializationProcessor = BehaviorProcessor.createDefault<Initialization>(Initialization.ForFirstTime)
    val initialization: Flowable<Initialization> = initializationProcessor.observeOn(AndroidSchedulers.mainThread())

    private val contributorsInNextPageProcessor = PublishProcessor.create<List<Contributor>>()
    val contributorsInNextPage: Flowable<List<Contributor>> = contributorsInNextPageProcessor.observeOn(AndroidSchedulers.mainThread())

    //endregion

    //region fetch contributors methods

    fun fetchContributorsInitial(owner: String, repository: String, needLoading: Boolean = true) {
        // 初回はページを指定しない
        fetchContributors(owner, repository, null, needLoading)
    }

    fun fetchContributorsInNextPage(owner: String, repository: String, needLoading: Boolean = true) {
        linkHeader?.nextPage?.let {
            fetchContributors(owner, repository, it, needLoading)
        } ?: run {
            contributorsInNextPageProcessor.onNext(listOf())
        }
    }

    private fun fetchContributors(owner: String, repository: String, page: Int? = null, needLoading: Boolean = true) {
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

    //endregion

    //region initialization methods

    fun initializeToFirstTime() {
        linkHeader = null
        compositeDisposable.clear()
        initializationProcessor.onNext(Initialization.ForFirstTime)
    }

    fun prepareForInitializationOfBack(contributors: List<Contributor>, lastShowedPosition: Int) {
        initializationProcessor.onNext(Initialization.ForBacked(contributors, lastShowedPosition))
    }

    //endregion
}
