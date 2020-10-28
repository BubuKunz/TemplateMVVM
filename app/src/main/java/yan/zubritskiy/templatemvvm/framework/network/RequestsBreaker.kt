package yan.zubritskiy.templatemvvm.framework.network

import okhttp3.OkHttpClient

interface RequestsBreaker {

    fun setClient(client: OkHttpClient)
    fun cancelAllRequests()
}