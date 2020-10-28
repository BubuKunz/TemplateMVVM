package yan.zubritskiy.templatemvvm.framework.network

import androidx.annotation.WorkerThread
import okhttp3.ResponseBody
import retrofit2.Response
import yan.zubritskiy.templatemvvm.business.network.TokenRenewer
import yan.zubritskiy.templatemvvm.framework.TokenManager
import yan.zubritskiy.templatemvvm.framework.network.retrofit.RefreshTokenService
import yan.zubritskiy.templatemvvm.framework.network.retrofit.model.AuthResult
import yan.zubritskiy.templatemvvm.framework.network.retrofit.model.RefreshTokenRequest

private const val UNEXPECTED_REFRESH_TOKEN_ERROR = 599

class PfmTokenRenewer (
    private val refreshTokenService: RefreshTokenService,
    private val tokenManager: TokenManager,
    private val requestsBreaker: RequestsBreaker
) : TokenRenewer {

    @WorkerThread
    private fun refreshToken(): Response<AuthResult> {
        val refreshToken = tokenManager.refreshToken
        val accessToken = tokenManager.accessToken
        if (accessToken == null) {
            return Response.error<AuthResult>(
                UNEXPECTED_REFRESH_TOKEN_ERROR,
                ResponseBody.create(null, "Access token is null")
            )
        } else if (refreshToken == null) {
            return Response.error<AuthResult>(
                UNEXPECTED_REFRESH_TOKEN_ERROR,
                ResponseBody.create(null, "Refresh token is null")
            )
        }
        val refreshTokenRequest = RefreshTokenRequest(refreshToken)
        val call = refreshTokenService.refreshToken(accessToken, refreshTokenRequest)
        return try {
            val result = call.execute()
            if (result.isSuccessful) {
                tokenManager.accessToken = result.body()?.accessToken
                tokenManager.refreshToken = result.body()?.refreshToken
            }
            result
        } catch (e: Exception) {
            Response.error(UNEXPECTED_REFRESH_TOKEN_ERROR, ResponseBody.create(null, "Token refresh failed: ${e.message}"))
        }
    }

    override fun executeRenew(accessToken: String?): String? {
        if (tokenManager.accessToken.isNullOrBlank() || accessToken.isNullOrBlank()) {
            return null
        }
        return if (accessToken == tokenManager.accessToken) {
            synchronized(PfmTokenRenewer::class.java) {
                if (accessToken == tokenManager.accessToken) {
                    val refreshResult = refreshToken()
                    if (refreshResult.isSuccessful) {
                        refreshResult.body()?.accessToken
                    } else {
                        runCatching {
                            requestsBreaker.cancelAllRequests()
                        }
                        tokenManager.accessToken = null
                        tokenManager.refreshToken = null
                        null
                    }
                } else {
                    tokenManager.accessToken
                }
            }
        } else {
            tokenManager.accessToken
        }
    }
}