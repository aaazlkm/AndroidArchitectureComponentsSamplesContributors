package hoge.hogehoge.infra.api.template

abstract class APIException(val code: Int, message: String = "") : Exception("code: $code message: $message")
