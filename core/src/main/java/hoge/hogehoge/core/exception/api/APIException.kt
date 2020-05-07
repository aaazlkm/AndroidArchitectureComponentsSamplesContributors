package hoge.hogehoge.core.exception.api

open class APIException(val code: Int, message: String = "") : Exception("code: $code message: $message")
