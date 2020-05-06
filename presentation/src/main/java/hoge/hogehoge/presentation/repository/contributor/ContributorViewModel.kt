package hoge.hogehoge.presentation.repository.contributor

import hoge.hogehoge.domain.usecase.GithubUseCase
import hoge.hogehoge.presentation.base.BaseViewModel
import javax.inject.Inject

class ContributorViewModel @Inject constructor(
    private val githubUseCase: GithubUseCase
) : BaseViewModel()
