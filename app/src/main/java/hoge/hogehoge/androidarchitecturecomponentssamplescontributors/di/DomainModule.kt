package hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di

import dagger.Module
import dagger.Provides
import hoge.hogehoge.domain.usecase.GithubUseCase
import hoge.hogehoge.domain.usecase.GithubUseCaseImpl
import hoge.hogehoge.infra.repository.GithubRepository

@Module
open class DomainModule {
    @Provides
    fun provideGithubUseCase(githubRepository: GithubRepository): GithubUseCase = GithubUseCaseImpl(githubRepository)
}
