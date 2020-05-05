package hoge.hogehoge.presentation

import androidx.navigation.fragment.findNavController
import hoge.hogehoge.domain.entity.Contributor
import hoge.hogehoge.presentation.repository.contributors.ContributorsFragment
import hoge.hogehoge.presentation.repository.contributors.ContributorsFragmentDirections
import hoge.hogehoge.presentation.repository.repository.RepositoryFragment
import hoge.hogehoge.presentation.repository.repository.RepositoryFragmentDirections

//region repository

fun RepositoryFragment.navigateToContributorsFragment(owner: String, repository: String) {
    val action = RepositoryFragmentDirections.actionRepositoryFragmentToContributorsFragment(owner, repository)
    findNavController().navigate(action)
}

fun ContributorsFragment.navigateToContributorFragment(contributor: Contributor) {
    val action = ContributorsFragmentDirections.actionContributorsFragmentToContributorFragment(contributor)
    findNavController().navigate(action)
}

//endregion
