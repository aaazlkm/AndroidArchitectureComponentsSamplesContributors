package hoge.hogehoge.infra.api.template

interface APIRequest<RESPONSE> where RESPONSE : APIResponse {
    var definition: APIDefinition

    val path: String
        get() = definition.path

    val parameter: Map<String, String>
}
