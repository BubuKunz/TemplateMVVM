package yan.zubritskiy.templatemvvm.framework.network.retrofit.model

import com.squareup.moshi.Json

data class RefreshTokenRequest(
    @field:Json(name = "refreshToken")
    val refreshToken: String
)