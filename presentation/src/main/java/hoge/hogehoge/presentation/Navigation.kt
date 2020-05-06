package hoge.hogehoge.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import hoge.hogehoge.presentation.repository.contributors.ContributorsFragment
import hoge.hogehoge.presentation.repository.contributors.ContributorsFragmentDirections
import hoge.hogehoge.presentation.repository.repository.RepositoryFragment
import hoge.hogehoge.presentation.repository.repository.RepositoryFragmentDirections

//region repository

fun RepositoryFragment.navigateToContributorsFragment(owner: String, repository: String) {
    val action = RepositoryFragmentDirections.actionRepositoryFragmentToContributorsFragment(owner, repository)
    findNavController().navigate(action)
}

fun ContributorsFragment.navigateToContributorFragment(nameTransitionPack: Pair<TextView, String>, avaterTransitionPack: Pair<ImageView, String>) {
    // 画像のtransition animationさせるのに画面間で共通のtransitionName設定が必要
    nameTransitionPack.first.transitionName = nameTransitionPack.second
    avaterTransitionPack.first.transitionName = avaterTransitionPack.second
    val action = ContributorsFragmentDirections.actionContributorsFragmentToUserFragment(nameTransitionPack.second, avaterTransitionPack.second)
    val extra = FragmentNavigatorExtras(
        nameTransitionPack,
        avaterTransitionPack
    )
    findNavController().navigate(action, extra)
}

//endregion
