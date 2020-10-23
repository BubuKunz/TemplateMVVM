package yan.zubritskiy.templatemvvm.business.network

import yan.zubritskiy.templatemvvm.business.model.User

interface UserNetworkDataSource {
    suspend fun getUser(): User
}