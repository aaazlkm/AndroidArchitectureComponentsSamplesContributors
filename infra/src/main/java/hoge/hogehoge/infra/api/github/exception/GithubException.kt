package hoge.hogehoge.infra.api.github.exception

import hoge.hogehoge.infra.api.template.APIException

class GithubException : APIException {
    constructor(code: Int) : super(code)
    constructor(code: Int, message: String) : super(code, message)
}
