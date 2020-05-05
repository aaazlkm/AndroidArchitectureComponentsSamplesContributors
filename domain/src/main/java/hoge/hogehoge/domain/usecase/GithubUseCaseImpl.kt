package hoge.hogehoge.domain.usecase

import hoge.hogehoge.domain.entity.Contributor
import hoge.hogehoge.domain.entity.LinkHeader
import hoge.hogehoge.domain.result.Result
import hoge.hogehoge.domain.result.toResult
import hoge.hogehoge.infra.api.github.request.GetContributorsAPI
import hoge.hogehoge.infra.repository.GithubRepository
import io.reactivex.Observable
import javax.inject.Inject

class GithubUseCaseImpl @Inject constructor(
    private val githubRepository: GithubRepository
) : GithubUseCase {
    override fun fetchContributors(owner: String, repository: String, page: Int?): Observable<Result<Pair<LinkHeader, List<Contributor>>>> {
        val request = GetContributorsAPI.Request(owner = owner, repository = repository, page = page)

        return githubRepository.fetchContributors(request)
            .map {
                val linkHeader = LinkHeader.from(it.rawLinkHeader)
                val contributors = it.rawContributors.map { Contributor.from(it) }
                linkHeader to contributors
            }
            .toResult()
    }
}
