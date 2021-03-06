package hoge.hogehoge.presentation.repository.contributors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import hoge.hogehoge.core.di.viewmodel.ViewModelFactory
import hoge.hogehoge.domain.entity.Contributor
import hoge.hogehoge.domain.result.Result.Failure
import hoge.hogehoge.presentation.R
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentContributorsBinding
import hoge.hogehoge.presentation.databinding.ItemContributorBinding
import hoge.hogehoge.presentation.navigateToUserFragment
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import timber.log.Timber

class ContributorsFragment : BaseFragment() {
    //region arguments

    private val args: ContributorsFragmentArgs by navArgs()

    private val owner: String
        get() = args.owner

    private val repository: String
        get() = args.repository

    //endregion

    private lateinit var binding: FragmentContributorsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ContributorsViewModel

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContributorsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ContributorsViewModel::class.java)

        bindUI()
        bindViewModelEvent()
        bindViewModelValue()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        prepareForInitializationOfBack()
    }

    //endregion

    //region setup methods

    private fun bindUI() {
        with(binding.swipeRefreshLayout) {
            setColorSchemeResources(R.color.colorAccent)
            setOnRefreshListener {
                viewModel.initializeToFirstTime()
            }
        }

        with(binding.contributorRecyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ContributorsFragment.context)
            adapter = ContributorsAdapter().apply {
                setOnItemClickListener(object : ContributorsAdapter.OnItemClickListener {
                    override fun onItemClicked(binding: ItemContributorBinding, contributor: Contributor) {
                        navigateToUserFragment(
                            binding.nameText to contributor.login,
                            binding.imageView to (contributor.avatarUrl ?: "") // avaterは必須情報ではないため無視する
                        )
                    }
                })
                setOnLoadMoreListener(object : ContributorsAdapter.OnLoadMoreListener {
                    override fun onLoadMore() {
                        viewModel.fetchContributorsInNextPage(owner, repository, needLoading = false) // adapter側でloadingを出しているため
                    }
                })
            }
        }
    }

    private fun bindViewModelEvent() {
        viewModel.eventOfContributors
            .subscribe { result ->
                Timber.d("eventOfContributors: $result")
                if (result is Failure) {
                    handleErrorForEventOfContributors(result.error)
                }
            }
            .addTo(compositeDisposable)
    }

    private fun bindViewModelValue() {
        viewModel.isLoading
            .subscribe {
                binding.swipeRefreshLayout.isRefreshing = it
            }
            .addTo(compositeDisposable)

        viewModel.initialization
            .subscribe { initialization ->
                when (initialization) {
                    is ContributorsViewModel.Initialization.ForFirstTime -> {
                        (binding.contributorRecyclerView.adapter as? ContributorsAdapter)?.run {
                            clearContributors()
                        }
                        viewModel.fetchContributorsInitial(owner, repository)
                    }
                    is ContributorsViewModel.Initialization.ForBacked -> {
                        (binding.contributorRecyclerView.adapter as? ContributorsAdapter)?.run {
                            val (contributors, lastShowedPosition) = initialization
                            insertContributorsAndResetProgress(contributors)
                            binding.contributorRecyclerView.scrollToPosition(lastShowedPosition)
                        }
                    }
                }
            }
            .addTo(compositeDisposable)

        viewModel.contributorsInNextPage
            .subscribe {
                (binding.contributorRecyclerView.adapter as? ContributorsAdapter)?.run {
                    if (it.isEmpty()) {
                        // 新しく読み込んだページにContributorが存在しない場合progressViewを取り除く
                        removeProgressView()
                    } else {
                        insertContributorsAndResetProgress(it)
                    }
                }
            }
            .addTo(compositeDisposable)
    }

    private fun prepareForInitializationOfBack() {
        val contributors = (binding.contributorRecyclerView.adapter as? ContributorsAdapter)?.getContributors() ?: listOf()
        val lastScrollPosition = (binding.contributorRecyclerView.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition() ?: 0
        viewModel.prepareForInitializationOfBack(contributors, lastScrollPosition)
    }

    //endregion

    //region handle error

    private fun handleErrorForEventOfContributors(e: Throwable) {
        Timber.e(e)
        val message = getString(R.string.fragment_contributors_error_get_contributors_message)
        handleError(message)
    }

    private fun handleError(message: String) {
        with(binding.retryView) {
            binding.retryView.root.visibility = View.VISIBLE
            messageText.text = message
            retryButton.setOnClickListener {
                binding.retryView.root.visibility = View.GONE
                viewModel.initializeToFirstTime()
            }
        }
    }

    //endregion
}
