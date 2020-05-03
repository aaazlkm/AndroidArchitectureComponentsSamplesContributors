package hoge.hogehoge.infra.api.github

import hoge.hogehoge.infra.api.template.APIDefinition

enum class GithubAPIDefinition : APIDefinition {
    GET_CONTRIBUTORS;

    override val path: String
        get() = "/repos"
}
