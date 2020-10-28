package yan.zubritskiy.templatemvvm.framework.network

import okhttp3.Interceptor
import okhttp3.Response
import yan.zubritskiy.templatemvvm.BuildConfig

private const val HEADER_MIN_VERSION = "X-Android-Min-Version"

class AppVersionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        return if (response.minVersion > BuildConfig.VERSION_CODE) {
            response
                .newBuilder()
                .code(HttpStatusCode.UPDATE_REQUIRED)
                .build()
        } else
            response
    }

    private val Response.minVersion: Int
        get() {
            return try {
                Integer.parseInt(header(HEADER_MIN_VERSION) ?: "0")
            } catch (e: NumberFormatException) {
                0
            }
        }
}