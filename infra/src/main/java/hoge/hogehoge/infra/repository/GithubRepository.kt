package hoge.hogehoge.infra.repository

import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import hoge.hogehoge.infra.api.github.request.GetUserAPI
import io.reactivex.Single

interface GithubRepository {
    fun fetchUser(request: GetUserAPI.Request): Single<GetUserAPI.Response>

    fun fetchContributors(request: GetContributorsAPI.Request): Single<GetContributorsAPI.Response>
}
