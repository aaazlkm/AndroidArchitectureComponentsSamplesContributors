package hoge.hogehoge.domain.usecase

import hoge.hogehoge.domain.entity.Contributor
import hoge.hogehoge.domain.entity.LinkHeader
import hoge.hogehoge.domain.entity.User
import hoge.hogehoge.domain.result.Result
import io.reactivex.Observable

interface GithubUseCase {
    fun fetchUser(userName: String): Observable<Result<User>>

    fun fetchContributors(owner: String, repository: String, page: Int? = null): Observable<Result<Pair<LinkHeader, List<Contributor>>>>
}
