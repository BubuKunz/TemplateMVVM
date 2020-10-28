package yan.zubritskiy.templatemvvm.framework.network

import okhttp3.Interceptor

class UserAgentInterceptor(private val userAgentHeader: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain) =
        chain.proceed(
            chain
                .request()
                .newBuilder()
                .addUserAgentHeader(userAgentHeader)
                .build()
        )
}