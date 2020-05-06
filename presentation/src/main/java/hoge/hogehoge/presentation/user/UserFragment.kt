package hoge.hogehoge.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import coil.api.load
import coil.transform.CircleCropTransformation
import hoge.hogehoge.core.di.viewmodel.ViewModelFactory
import hoge.hogehoge.domain.entity.User
import hoge.hogehoge.domain.result.Result
import hoge.hogehoge.presentation.R
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentUserBinding
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import timber.log.Timber

class UserFragment : BaseFragment() {
    //region arguments

    private val args: UserFragmentArgs by navArgs()

    private val userName: String
        get() = args.userName

    private val avatarUrl: String
        get() = args.avatarUrl

    //endregion

    private lateinit var binding: FragmentUserBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: UserViewModel

    private val eventOfTransitionAnimationFinishedProcessor = PublishProcessor.create<Boolean>()

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)

        viewModel.setup(
            UserViewModel.Control(eventOfTransitionAnimationFinishedProcessor)
        )
        bindUI()
        bindViewModelEvent()
        bindViewModelValue()
        setTransitionAnimation()
        fetchData()

        return binding.root
    }

    //endregion

    //region setup methods

    private fun bindUI() {
        with(binding.viewPager) {
            adapter = UserFragmentPagerAdapter(context, childFragmentManager)
            offscreenPageLimit = TabItem.values().size
        }

        with(binding.tabLayout) {
            setupWithViewPager(binding.viewPager)
            TabItem.values().forEach {
                val index = TabItem.values().indexOf(it)
                getTabAt(index)?.orCreateBadge?.run {
                    context?.getColor(R.color.colorAccent)?.let { backgroundColor = it }
                    isVisible = false
                }
            }
        }
    }

    private fun bindViewModelEvent() {
        viewModel.eventOfGettingUser
            .subscribe {
                Timber.d("eventOfGettingUser $it")
                if (it is Result.Failure) handleErrorOfEventGettingUser(it.error)
            }
            .addTo(compositeDisposable)
    }

    private fun bindViewModelValue() {
        viewModel.isLoading
            .subscribe {
                setLocadingView(it)
            }
            .addTo(compositeDisposable)

        viewModel.user
            .subscribe {
                applyUserToView(it)
            }
            .addTo(compositeDisposable)
    }

    private fun setTransitionAnimation() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        binding.userNameText.transitionName = userName
        binding.userNameText.text = userName

        binding.userImageView.transitionName = avatarUrl
        binding.userImageView.load(avatarUrl) {
            crossfade(true)
            error(R.drawable.placeholder)
            transformations(CircleCropTransformation())
        }

        // TranstionAnimationの終了をViewModelに伝える
        (sharedElementEnterTransition as Transition?)?.addListener(object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                eventOfTransitionAnimationFinishedProcessor.onNext(true)
            }
        })
    }

    private fun fetchData() {
        // 初回データ取得時はアニメーションを見せたいのでローディングを表示しない
        viewModel.fetchUser(userName, needLoading = false)
    }

    //endregion

    private fun applyUserToView(user: User) {
        TransitionManager.beginDelayedTransition(binding.container)

        if (user.bio.isNullOrBlank()) {
            binding.bioTextView.visibility = View.GONE
        } else {
            binding.bioTextView.text = user.bio
            binding.bioTextView.visibility = View.VISIBLE
        }

        if (user.location.isNullOrBlank()) {
            binding.locationContainer.visibility = View.GONE
        } else {
            binding.locationTextView.text = user.location
            binding.locationContainer.visibility = View.VISIBLE
        }

        if (user.blog.isNullOrBlank()) {
            binding.blogContainer.visibility = View.GONE
        } else {
            binding.blogTextView.text = user.blog
            binding.blogContainer.visibility = View.VISIBLE
        }

        if (user.email.isNullOrBlank()) {
            binding.mailContainer.visibility = View.GONE
        } else {
            binding.mailTextView.text = user.email
            binding.mailContainer.visibility = View.VISIBLE
        }

        val repositoriesTabIndex = TabItem.values().indexOf(TabItem.REPOSITORIES)
        binding.tabLayout.getTabAt(repositoriesTabIndex)?.orCreateBadge?.run {
            if (user.publicRepos != 0) {
                isVisible = true
                number = user.publicRepos
            } else {
                isVisible = false
            }
        }

        val followersTabIndex = TabItem.values().indexOf(TabItem.FOLLOWERS)
        binding.tabLayout.getTabAt(followersTabIndex)?.orCreateBadge?.run {
            if (user.followers != 0) {
                isVisible = true
                number = user.followers
            } else {
                isVisible = false
            }
        }

        val followingTabIndex = TabItem.values().indexOf(TabItem.FOLLOWING)
        binding.tabLayout.getTabAt(followingTabIndex)?.orCreateBadge?.run {
            if (user.following != 0) {
                isVisible = true
                number = user.following
            } else {
                isVisible = false
            }
        }
    }

    //region handle error

    private fun handleErrorOfEventGettingUser(exception: Throwable) {
        Timber.e(exception)
        val message = getString(R.string.fragment_user_error_get_user, userName)
        handleError(message)
    }

    private fun handleError(message: String) {
        with(binding.retryView) {
            binding.retryView.root.visibility = View.VISIBLE
            messageText.text = message
            retryButton.setOnClickListener {
                binding.retryView.root.visibility = View.GONE
                viewModel.fetchUser(userName)
            }
        }
    }

    //endregion
}
