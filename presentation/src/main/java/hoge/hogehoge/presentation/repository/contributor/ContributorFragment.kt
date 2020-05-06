package hoge.hogehoge.presentation.repository.contributor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.api.load
import coil.transform.CircleCropTransformation
import hoge.hogehoge.presentation.R
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentContributorBinding

class ContributorFragment : BaseFragment() {

    private lateinit var binding: FragmentContributorBinding

    //region arguments

    private val args: ContributorFragmentArgs by navArgs()

    private val userName: String
        get() = args.userName

    private val avatarUrl: String
        get() = args.avatarUrl

    //endregion

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContributorBinding.inflate(inflater)

        bindUI()
        bindViewModelEvent()
        bindViewModelValue()
        setTransitionAnimation()

        return binding.root
    }

    //endregion

    private fun bindUI() {
    }

    private fun bindViewModelEvent() {
    }

    private fun bindViewModelValue() {
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
    }
}
