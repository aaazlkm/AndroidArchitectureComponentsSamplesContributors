package hoge.hogehoge.infra.repository

import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import hoge.hogehoge.infra.api.github.request.GetUserAPI
import hoge.hogehoge.infra.store.GithubRemoteStore
import io.reactivex.Single
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubRemoteStore: GithubRemoteStore
) : GithubRepository {
    override fun fetchUser(request: GetUserAPI.Request): Single<GetUserAPI.Response> {
        return githubRemoteStore.fetchUser(request)
    }

    override fun fetchContributors(request: GetContributorsAPI.Request): Single<GetContributorsAPI.Response> {
        return githubRemoteStore.fetchContributors(request)
    }
}
