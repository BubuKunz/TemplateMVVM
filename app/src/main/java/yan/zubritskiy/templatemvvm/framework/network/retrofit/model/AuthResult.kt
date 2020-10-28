package yan.zubritskiy.templatemvvm.framework.network.retrofit.model

import com.squareup.moshi.Json

data class AuthResult(
    @field:Json(name = "accessToken")
    val accessToken: String,
    @field:Json(name = "refreshToken")
    val refreshToken: String
)