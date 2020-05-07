package hoge.hogehoge.presentation.user

import hoge.hogehoge.domain.entity.User
import hoge.hogehoge.domain.result.Result
import hoge.hogehoge.domain.result.map
import hoge.hogehoge.domain.usecase.GithubUseCase
import hoge.hogehoge.presentation.base.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : BaseViewModel() {
    data class Control(
        val eventOfTransitionAnimationFinished: Flowable<Boolean>
    )

    //region event

    private val eventOfGettingUserProcessor = BehaviorProcessor.createDefault<Result<Boolean>>(Result.onReady())
    val eventOfGettingUser: Flowable<Result<Boolean>> = eventOfGettingUserProcessor.observeOn(AndroidSchedulers.mainThread())

    //endregion

    //region value

    private val isLoadingProcessor = PublishProcessor.create<Boolean>()
    val isLoading: Flowable<Boolean> = isLoadingProcessor.observeOn(AndroidSchedulers.mainThread())

    private val userProcessor = PublishProcessor.create<User>()
    lateinit var user: Flowable<User>

    //endregion

    /**
     * 画面表示時に必ず呼ぶこと
     *
     * @param control Control
     */
    fun setup(control: Control) {
        // transitionAnimationが終了してからUserデータを通知する
        user = Flowables.zip(
            control.eventOfTransitionAnimationFinished,
            userProcessor
        ) { t1, t2 -> t2 }.observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchUser(userName: String, needLoading: Boolean = true) {
        githubUseCase.fetchUser(userName)
            .doOnNext { if (it is Result.Success) userProcessor.onNext(it.value) }
            .doOnNext { if (needLoading) isLoadingProcessor.onNext(it is Result.Loading) }
            .map { it.map { true } }
            .subscribe { result ->
                eventOfGettingUserProcessor.onNext(result)
            }
            .addTo(compositeDisposable)
    }
}
