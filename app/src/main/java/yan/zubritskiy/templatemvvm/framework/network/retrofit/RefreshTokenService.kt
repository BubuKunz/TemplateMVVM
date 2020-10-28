package yan.zubritskiy.templatemvvm.framework.network.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import yan.zubritskiy.templatemvvm.framework.network.AUTHORIZATION
import yan.zubritskiy.templatemvvm.framework.network.retrofit.model.AuthResult
import yan.zubritskiy.templatemvvm.framework.network.retrofit.model.RefreshTokenRequest

const val REFRESH_PATH = "auth/tokens/refresh"

interface RefreshTokenService {

    @POST(REFRESH_PATH)
    fun refreshToken(
        @Header(AUTHORIZATION) header: String,
        @Body request: RefreshTokenRequest
    ): Call<AuthResult>
}