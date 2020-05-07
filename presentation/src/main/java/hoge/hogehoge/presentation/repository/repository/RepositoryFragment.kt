package hoge.hogehoge.presentation.repository.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoge.hogehoge.presentation.base.BaseFragment
import hoge.hogehoge.presentation.databinding.FragmentRepositoryBinding
import hoge.hogehoge.presentation.navigateToContributorsFragment

class RepositoryFragment : BaseFragment() {
    companion object {
        const val OWNER_GOOGLE_SAMPLES = "googlesamples"
        const val REPOSITORY_ANDROID_ARCHITECTURE_COMPONENTS = "android-architecture-components"
    }

    private lateinit var binding: FragmentRepositoryBinding

    //region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRepositoryBinding.inflate(inflater)

        bindUI()

        return binding.root
    }

    //endregion

    private fun bindUI() {
        binding.navigateButton
            .setOnClickListener {
                navigateToContributorsFragment(OWNER_GOOGLE_SAMPLES, REPOSITORY_ANDROID_ARCHITECTURE_COMPONENTS)
            }
    }
}
