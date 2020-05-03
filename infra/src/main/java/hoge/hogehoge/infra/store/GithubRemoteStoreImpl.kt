package hoge.hogehoge.infra.store

import hoge.hogehoge.core.extension.checkStatusCodeAndThrowIfNeeded
import hoge.hogehoge.infra.api.github.GithubService
import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import io.reactivex.Single
import javax.inject.Inject

class GithubRemoteStoreImpl @Inject constructor(
    private val githubService: GithubService
) : GithubRemoteStore {
    override fun fetchContributors(request: GetContributorsAPI.Request): Single<GetContributorsAPI.Response> {
        return githubService.fetchContributors(request.path, request.parameter)
            .checkStatusCodeAndThrowIfNeeded()
            .map { GetContributorsAPI.Response.from(it) }
    }
}
