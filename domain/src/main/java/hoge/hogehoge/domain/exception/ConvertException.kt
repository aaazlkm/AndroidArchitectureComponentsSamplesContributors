package hoge.hogehoge.domain.exception

/**
 * APIレスポンスデータを変換した時のエラー
 *
 * @param message String
 */
class ConvertException(message: String) : Exception(message)
