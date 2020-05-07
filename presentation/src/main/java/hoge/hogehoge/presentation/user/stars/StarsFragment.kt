package hoge.hogehoge.presentation.user.stars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentUserStarsBinding

class StarsFragment : BaseFragment() {
    lateinit var binding: FragmentUserStarsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserStarsBinding.inflate(inflater)

        return binding.root
    }
}
