package hoge.hogehoge.infra.store

import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import hoge.hogehoge.infra.api.github.request.GetUserAPI
import io.reactivex.Single

interface GithubRemoteStore {

    fun fetchUser(request: GetUserAPI.Request): Single<GetUserAPI.Response>

    fun fetchContributors(request: GetContributorsAPI.Request): Single<GetContributorsAPI.Response>
}
