package hoge.hogehoge.presentation.repository.contributors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentContributorsBinding

class ContributorsFragment : BaseFragment() {

    private lateinit var binding: FragmentContributorsBinding

    //region arguments

    private val args: ContributorsFragmentArgs by navArgs()

    private val owner: String
        get() = args.owner

    private val repository: String
        get() = args.repository

    //endregion

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContributorsBinding.inflate(inflater)

        return binding.root
    }

    //endregion
}
