package yan.zubritskiy.templatemvvm.framework

import yan.zubritskiy.templatemvvm.business.local.Storage

class TokenManager(
    private val storage: Storage
) {

    var accessToken: String? = null
        @Synchronized set(value) {
            storage.accessToken = value
            field = value
        }
        get() {
            if (field.isNullOrEmpty()) {
                field = storage.accessToken
            }
            return field
        }

    var refreshToken: String? = null
        @Synchronized set(value) {
            storage.refreshToken = value
            field = value
        }
        get() {
            if (field.isNullOrEmpty()) {
                field = storage.refreshToken
            }
            return field
        }

    fun clearTokens() {
        accessToken = null
        refreshToken = null
    }
}