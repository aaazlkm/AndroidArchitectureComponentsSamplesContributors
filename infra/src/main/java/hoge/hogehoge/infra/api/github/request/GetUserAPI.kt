package hoge.hogehoge.infra.api.github.request

import hoge.hogehoge.infra.api.github.GithubAPIDefinition
import hoge.hogehoge.infra.api.github.model.body.RawUser
import hoge.hogehoge.infra.api.template.APIDefinition
import hoge.hogehoge.infra.api.template.APIRequest
import hoge.hogehoge.infra.api.template.APIResponse

object GetUserAPI {
    data class Request(
        val userName: String
    ) : APIRequest<Response> {
        override var definition: APIDefinition = GithubAPIDefinition.GET_USER

        override val path = "${definition.path}/$userName"

        override val parameter: Map<String, String>
            get() = mapOf()
    }

    data class Response(
        val rawUser: RawUser?
    ) : APIResponse {
        companion object {
            fun from(rawResponse: retrofit2.Response<RawUser>): Response {
                return Response(rawResponse.body())
            }
        }
    }
}
