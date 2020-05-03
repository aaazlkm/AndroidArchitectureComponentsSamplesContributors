package hoge.hogehoge.infra.store

import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import io.reactivex.Single

interface GithubRemoteStore {

    fun fetchContributors(request: GetContributorsAPI.Request): Single<GetContributorsAPI.Response>
}
