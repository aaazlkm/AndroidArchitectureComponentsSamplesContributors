package hoge.hogehoge.presentation.user.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentUserRepositoriesBinding

class RepositoriesFragment : BaseFragment() {
    lateinit var binding: FragmentUserRepositoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserRepositoriesBinding.inflate(inflater)

        return binding.root
    }
}
