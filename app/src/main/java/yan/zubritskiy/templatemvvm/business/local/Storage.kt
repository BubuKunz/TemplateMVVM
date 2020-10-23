package com.pfm.data.common.storage

import kotlinx.coroutines.flow.Flow

interface Storage {

    var pushNotificationsToken: String?
    var accessToken: String?
    val accessTokenFlow: Flow<String?>

    var refreshToken: String?
    val refreshTokenFlow: Flow<String?>
    val uuid: String
    var mainTransactionsListCursor: String?

    var transactionsCount: Long
    val transactionsCountFlow: Flow<Long>

    suspend fun clearData()
}