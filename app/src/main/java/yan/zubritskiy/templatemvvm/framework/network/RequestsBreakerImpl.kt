package yan.zubritskiy.templatemvvm.framework.network

import okhttp3.OkHttpClient

class RequestsBreakerImpl : RequestsBreaker {
    private lateinit var okHttpClient: OkHttpClient
    override fun setClient(client: OkHttpClient) {
        if (!::okHttpClient.isInitialized) {
            okHttpClient = client
        }
    }

    @Throws(IllegalStateException::class)
    override fun cancelAllRequests() {
        if (!::okHttpClient.isInitialized) {
            throw IllegalStateException("client doesn't initialized. Make sure you've invoked setClient before")
        }
        okHttpClient.dispatcher.cancelAll()
        okHttpClient.dispatcher
    }
}