package hoge.hogehoge.infra.api.github.request

import hoge.hogehoge.infra.api.github.GithubAPIDefinition
import hoge.hogehoge.infra.api.github.model.body.RawContributor
import hoge.hogehoge.infra.api.github.model.header.RawLinkHeader
import hoge.hogehoge.infra.api.template.APIDefinition
import hoge.hogehoge.infra.api.template.APIRequest
import hoge.hogehoge.infra.api.template.APIResponse

object GetContributorsAPI {
    data class Request(
        val owner: String,
        val repository: String,
        val page: Int = 0,
        /** Set to 1 or true to include anonymous contributors in results.*/
        val needAnonymous: Boolean = true
    ) : APIRequest<Response> {
        enum class QueryName(val queryName: String) {
            Page("page"),
            ANON("anon");
        }

        override var definition: APIDefinition = GithubAPIDefinition.GET_CONTRIBUTORS

        override val path = "${definition.path}/$owner/$repository/contributors"

        override val parameter: Map<String, String>
            get() = mapOf(
                QueryName.Page.queryName to "$page",
                QueryName.ANON.queryName to "$needAnonymous"
            )
    }

    data class Response(
        val rawLinkHeader: RawLinkHeader,
        val rawContributors: List<RawContributor>
    ) : APIResponse {
        companion object {
            fun from(rawResponse: retrofit2.Response<List<RawContributor>>): Response {
                val linkHeader = rawResponse.headers()[RawLinkHeader.NAME_IN_HEADER]?.let { RawLinkHeader.from(it) } ?: RawLinkHeader()
                return Response(linkHeader, rawResponse.body() ?: listOf())
            }
        }
    }
}
