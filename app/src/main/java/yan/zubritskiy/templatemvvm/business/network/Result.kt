package yan.zubritskiy.templatemvvm.business.network

private const val ERROR_UNAUTHORIZED = "Unauthorized"
private const val ERROR_VERSION_UPDATE = "Version Update"
private const val ERROR_CONNECTION = "Connection Error"
private const val ERROR_CONNECTION_TIMEOUT = "Connection Timeout"
private const val ERROR_SERVER_INTERNAL = "Something went wrong"
private const val ERROR_SERVER_TEMPORARY_UNAVAILABLE = "Server is temporarily unavailable."
private const val ERROR_SERVER_MAINTENANCE = "Technical works are in progress."

sealed class Result<out R> {

    data class Success<out R>(val data: R?) : Result<R>()
    data class Error(val error: BaseError) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}

inline fun <reified R, F> Result<R>.map(mapFunc: (type: R?) -> F?): Result<F> {
    return when (this) {
        is Result.Success<R> -> Result.Success(
            mapFunc(this.data)
        )
        is Result.Error -> this
    }
}

inline fun <reified R, F> Result<R>.mapNotNull(mapFunc: (type: R) -> F?): Result<F> {
    return when (this) {
        is Result.Success<R> -> Result.Success(
            data?.let(mapFunc)
        )
        is Result.Error -> this
    }
}

inline fun <reified R> Result<R>.mapNothing(): Result<Nothing> {
    return when (this) {
        is Result.Success<R> -> Result.Success(null)
        is Result.Error -> this
    }
}

inline fun <reified R> Result<R>.onSuccess(perform: (R?) -> Unit): Result<R> {
    if (this is Result.Success<R>) {
        perform(data)
    }
    return this
}

inline fun <reified R> Result<R>.onSuccessNotNull(perform: (R) -> Unit): Result<R> {
    if (this is Result.Success<R>) {
        data?.let(perform)
    }
    return this
}

inline fun <reified R> Result<R>.onError(perform: (BaseError) -> Unit): Result<R> {
    if (this is Result.Error) {
        perform(error)
    }
    return this
}

abstract class BaseError(message: String = "") : Throwable(message)

sealed class NetworkError(errorMessage: String) : BaseError(errorMessage) {
    object Unauthorized : NetworkError(ERROR_UNAUTHORIZED)
    object VersionUpdate : NetworkError(ERROR_VERSION_UPDATE)
    object Connection : NetworkError(ERROR_CONNECTION)
    object ConnectionTimeout : NetworkError(ERROR_CONNECTION_TIMEOUT)
    object ServerInternalError : NetworkError(ERROR_SERVER_INTERNAL)
    object ServerTemporaryUnavailable : NetworkError(ERROR_SERVER_TEMPORARY_UNAVAILABLE)
    object ServerMaintenance : NetworkError(ERROR_SERVER_MAINTENANCE)
    data class OperationCode(val opCode: String, val responseCode: Int) : NetworkError(opCode)
    data class Unknown(val errorMessage: String = "") : NetworkError(errorMessage)
}