package yan.zubritskiy.templatemvvm.framework.network

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import yan.zubritskiy.templatemvvm.business.network.NetworkError
import yan.zubritskiy.templatemvvm.framework.network.HttpStatusCode.CODE_SERVER_INTERNAL_ERROR
import yan.zubritskiy.templatemvvm.framework.network.HttpStatusCode.CODE_SERVER_MAINTENANCE
import yan.zubritskiy.templatemvvm.framework.network.HttpStatusCode.CODE_SERVER_UNAVAILABLE
import yan.zubritskiy.templatemvvm.framework.network.HttpStatusCode.CODE_UNAUTHORIZED
import yan.zubritskiy.templatemvvm.framework.network.HttpStatusCode.UPDATE_REQUIRED
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorMapper {

    fun <T> toErrorCause(
        response: Response<T>? = null,
        throwable: Throwable? = null
    ): NetworkError {
        return when {
            response?.isCodeUnauthorized == true -> NetworkError.Unauthorized
            response?.isCodeVersionUpdate == true -> NetworkError.VersionUpdate
            response?.isCodeServerInternalError == true -> NetworkError.ServerInternalError
            response?.isCodeServerUnavailable == true -> NetworkError.ServerTemporaryUnavailable
            response?.isCodeServerMaintenance == true -> NetworkError.ServerMaintenance
            throwable?.isConnectionException == true -> NetworkError.Connection
            throwable?.isConnectionTimeoutException == true -> NetworkError.ConnectionTimeout
            response?.errorBody() != null -> NetworkError.OperationCode(
                response.errorBody()!!.operationCode, response.code()
            )
            else -> NetworkError.Unknown(
                response?.toErrorString()
                    ?: throwable?.toErrorString()
                    ?: ""
            )
        }
    }

    private fun Throwable.toErrorString() =
        when (this) {
            is HttpException -> "${code()}: $message"
            else -> localizedMessage ?: ""
        }

    private fun Response<*>.toErrorString() = "${code()} : ${message()}"

    private val Throwable.isConnectionException: Boolean
        get() = this is ConnectException ||
                this is UnknownHostException ||
                this is NoRouteToHostException ||
                this.cause?.isConnectionException ?: false

    private val Throwable.isConnectionTimeoutException: Boolean
        get() = this is SocketTimeoutException || this is InterruptedIOException

    private val Response<*>.isCodeUnauthorized
        get() = code() == CODE_UNAUTHORIZED

    private val Response<*>.isCodeVersionUpdate
        get() = code() == UPDATE_REQUIRED

    private val Response<*>.isCodeServerInternalError
        get() = code() == CODE_SERVER_INTERNAL_ERROR

    private val Response<*>.isCodeServerUnavailable
        get() = code() == CODE_SERVER_UNAVAILABLE

    private val Response<*>.isCodeServerMaintenance
        get() = code() == CODE_SERVER_MAINTENANCE
}

val ResponseBody.operationCode: String
    get() = try {
        JSONObject(string()).getString("errorCode")
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }

object HttpStatusCode {
    const val CODE_UNAUTHORIZED = 401
    const val CODE_SERVER_INTERNAL_ERROR = 500
    const val CODE_SERVER_UNAVAILABLE = 502
    const val CODE_SERVER_MAINTENANCE = 503
    const val UPDATE_REQUIRED = 426
}