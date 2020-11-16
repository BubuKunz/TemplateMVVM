package yan.zubritskiy.templatemvvm.business.network

interface AuthNetworkDataSource {
    suspend fun signIn(nickName: String, password: String)

    suspend fun signUp(nickName: String, password: String)
}