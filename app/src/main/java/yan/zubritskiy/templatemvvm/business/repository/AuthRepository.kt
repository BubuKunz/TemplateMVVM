package yan.zubritskiy.templatemvvm.business.repository

interface AuthRepository {
    suspend fun signIn(nickName: String, password: String)

    suspend fun signUp(nickName: String, password: String)

    suspend fun isSignedIn(): Boolean
}