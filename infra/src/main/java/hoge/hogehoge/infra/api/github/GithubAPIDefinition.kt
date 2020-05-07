package hoge.hogehoge.infra.api.github

import hoge.hogehoge.infra.api.template.APIDefinition

enum class GithubAPIDefinition : APIDefinition {
    GET_USER,
    GET_CONTRIBUTORS;

    override val path: String
        get() = when (this) {
            GET_USER -> "/users"
            GET_CONTRIBUTORS -> "/repos"
        }
}
