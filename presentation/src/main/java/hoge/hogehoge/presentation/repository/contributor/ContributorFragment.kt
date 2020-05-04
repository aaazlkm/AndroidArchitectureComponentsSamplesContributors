package hoge.hogehoge.presentation.repository.contributor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import hoge.hogehoge.domain.entity.Contributor
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentContributorBinding

class ContributorFragment : BaseFragment() {

    private lateinit var binding: FragmentContributorBinding

    //region arguments

    private val args: ContributorFragmentArgs by navArgs()

    private val contributor: Contributor
        get() = args.contributor

    //endregion

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContributorBinding.inflate(inflater)

        return binding.root
    }

    //endregion
}
