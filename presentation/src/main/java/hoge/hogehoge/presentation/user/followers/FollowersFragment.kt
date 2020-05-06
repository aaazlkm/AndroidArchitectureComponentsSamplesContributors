package hoge.hogehoge.presentation.user.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentUserFollowersBinding

class FollowersFragment : BaseFragment() {
    lateinit var binding: FragmentUserFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserFollowersBinding.inflate(inflater)

        return binding.root
    }
}
