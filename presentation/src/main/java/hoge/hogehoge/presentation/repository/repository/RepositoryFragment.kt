package hoge.hogehoge.presentation.repository.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentRepositoryBinding
import hoge.hogehoge.presentation.toContributorsFragment

class RepositoryFragment : BaseFragment() {

    private lateinit var binding: FragmentRepositoryBinding

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRepositoryBinding.inflate(inflater)

        toContributorsFragment("googlesamples", "android-architecture-components")

        return binding.root
    }

    //endregion
}
