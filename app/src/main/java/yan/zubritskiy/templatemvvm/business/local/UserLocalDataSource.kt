package yan.zubritskiy.templatemvvm.business.local

import kotlinx.coroutines.flow.Flow
import yan.zubritskiy.templatemvvm.business.model.User

interface UserLocalDataSource {
    suspend fun getUser(): User
    fun getUserFlow(): Flow<User>
}