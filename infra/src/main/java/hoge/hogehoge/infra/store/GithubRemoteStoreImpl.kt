package hoge.hogehoge.infra.store

import hoge.hogehoge.core.extension.checkStatusCodeAndThrowIfNeeded
import hoge.hogehoge.infra.api.github.GithubService
import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import hoge.hogehoge.infra.api.github.request.GetUserAPI
import io.reactivex.Single
import javax.inject.Inject

class GithubRemoteStoreImpl @Inject constructor(
    private val githubService: GithubService
) : GithubRemoteStore {
    override fun fetchUser(request: GetUserAPI.Request): Single<GetUserAPI.Response> {
        return githubService.fetchUser(request.path, request.parameter)
            .checkStatusCodeAndThrowIfNeeded()
            .map { GetUserAPI.Response.from(it) }
    }

    override fun fetchContributors(request: GetContributorsAPI.Request): Single<GetContributorsAPI.Response> {
        return githubService.fetchContributors(request.path, request.parameter)
            .checkStatusCodeAndThrowIfNeeded()
            .map { GetContributorsAPI.Response.from(it) }
    }
}
