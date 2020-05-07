package hoge.hogehoge.core.extension

import hoge.hogehoge.core.exception.api.APIException
import io.reactivex.Single
import retrofit2.Response

/**
 * APIレスポンスのステータスコードを確認して[200..300)でなければエラーを返す
 *
 * @param T Response
 * @return Single<Response<T>>
 */
fun <T> Single<Response<T>>.checkStatusCodeAndThrowIfNeeded(): Single<Response<T>> {
    return this.doOnSuccess {
        if (!it.isSuccessful) throw APIException(it.code(), "${it.errorBody()}")
    }
}
