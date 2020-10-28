package yan.zubritskiy.templatemvvm.framework.network

import okhttp3.Interceptor
import okhttp3.Response
import yan.zubritskiy.templatemvvm.framework.TokenManager

class AuthTokenInterceptor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return tokenManager?.accessToken?.let {
            val newRequest = chain.request().newBuilder()
                .addAuthorizationBasicHeader(it)
                .build()
            chain.proceed(newRequest)
        } ?: run {
            chain.proceed(chain.request())
        }
    }
}