package hoge.hogehoge.presentation.user.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentUserFollowingBinding

class FollowingFragment : BaseFragment() {
    lateinit var binding: FragmentUserFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserFollowingBinding.inflate(inflater)

        return binding.root
    }
}
