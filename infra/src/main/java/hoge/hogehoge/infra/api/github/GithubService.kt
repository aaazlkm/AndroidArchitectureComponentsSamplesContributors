package hoge.hogehoge.infra.api.github

import hoge.hogehoge.infra.api.github.model.body.RawContributor
import hoge.hogehoge.infra.api.github.model.body.RawUser
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface GithubService {
    @GET
    fun fetchUser(@Url url: String, @QueryMap parameters: Map<String, String>): Single<Response<RawUser>>

    @GET
    fun fetchContributors(@Url url: String, @QueryMap parameters: Map<String, String>): Single<Response<List<RawContributor>>>
}
