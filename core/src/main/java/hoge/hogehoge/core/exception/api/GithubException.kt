package hoge.hogehoge.core.exception.api

class GithubException : APIException {
    constructor(code: Int) : super(code)
    constructor(code: Int, message: String) : super(code, message)
}
