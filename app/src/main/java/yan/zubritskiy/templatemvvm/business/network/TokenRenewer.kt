package yan.zubritskiy.templatemvvm.business.network

interface TokenRenewer {

    /**
     * @param accessToken - old token which caused 401.
     * Returns either new access token or null. Will return null in case when
     * no need to repeat request (token refreshing failed, etc.)
     */
    fun executeRenew(accessToken: String?): String?
}