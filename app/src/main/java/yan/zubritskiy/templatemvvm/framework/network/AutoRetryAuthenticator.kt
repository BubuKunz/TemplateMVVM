package yan.zubritskiy.templatemvvm.framework.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import yan.zubritskiy.templatemvvm.business.network.TokenRenewer

class AutoRetryAuthenticator(
    private val tokenRenewer: TokenRenewer
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        return if (response.code == HttpStatusCode.CODE_UNAUTHORIZED) {
            val oldToken = response.request.header(AUTHORIZATION)
                ?.replace(BEARER.trim(), "")
                ?.trim()
            tokenRenewer.executeRenew(oldToken)?.let { newToken ->
                newRequest(response, newToken)
            }
        } else null
    }

    private fun newRequest(response: Response, newToken: String?): Request? = newToken?.let {
        with(response.request.newBuilder()) {
            header(AUTHORIZATION, getFormattedToken(it))
            build()
        }
    }

    private fun getFormattedToken(token: String) = "$BEARER$token"
}