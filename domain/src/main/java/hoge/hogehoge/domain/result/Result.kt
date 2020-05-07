package hoge.hogehoge.domain.result

sealed class Result<T> {
    companion object {
        fun <T> success(value: T): Result<T> = Success(value)

        fun <T> failure(error: Throwable): Result<T> = Failure(error)

        fun <T> loading(): Result<T> = Loading()

        fun <T> onReady(): Result<T> = OnReady()
    }

    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val error: Throwable) : Result<T>()
    class Loading<T> : Result<T>()
    class OnReady<T> : Result<T>()
}

/**
 * Resultの型を変換する
 *
 * @param T
 * @param R
 * @param transform (value: T) -> R　変換処理
 * @return Result<R>
 */
inline fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.success(transform(this.value))
        is Result.Failure -> Result.failure(this.error)
        is Result.Loading -> Result.loading()
        is Result.OnReady -> Result.onReady()
    }
}
