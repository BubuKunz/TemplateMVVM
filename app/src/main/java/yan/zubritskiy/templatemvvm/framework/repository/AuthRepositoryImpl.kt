package yan.zubritskiy.templatemvvm.framework.repository

import yan.zubritskiy.templatemvvm.business.local.Storage
import yan.zubritskiy.templatemvvm.business.network.AuthNetworkDataSource
import yan.zubritskiy.templatemvvm.business.repository.AuthRepository

class AuthRepositoryImpl(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val storage: Storage
) : AuthRepository {

    override suspend fun signIn(nickName: String, password: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun signUp(nickName: String, password: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun isSignedIn(): Boolean {
        // TODO("Not yet implemented")
        return false
    }
}