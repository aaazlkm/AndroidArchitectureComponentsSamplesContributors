package hoge.hogehoge.androidarchitecturecomponentssamplescontributors.di

import dagger.Module
import dagger.Provides
import hoge.hogehoge.infra.api.github.GithubService
import hoge.hogehoge.infra.repository.GithubRepository
import hoge.hogehoge.infra.repository.GithubRepositoryImpl
import hoge.hogehoge.infra.store.GithubRemoteStore
import hoge.hogehoge.infra.store.GithubRemoteStoreImpl

@Module
open class InfraModule {
    //region repository

    @Provides
    fun provideGithubRepository(
        githubRemoteStore: GithubRemoteStore
    ): GithubRepository = GithubRepositoryImpl(githubRemoteStore)

    //endregion

    //region store

    @Provides
    fun provideGithubRemoteStore(
        githubService: GithubService
    ): GithubRemoteStore = GithubRemoteStoreImpl(githubService)

    //endregion
}
