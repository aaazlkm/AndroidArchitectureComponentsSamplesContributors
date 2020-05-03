package hoge.hogehoge.infra.repository

import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import io.reactivex.Single

interface GithubRepository {
    fun fetchContributors(request: GetContributorsAPI.Request): Single<GetContributorsAPI.Response>
}
