@file:Suppress("EXPERIMENTAL_API_USAGE", "CommitPrefEdits")

package yan.zubritskiy.templatemvvm.framework.local

import android.app.Activity
import android.app.backup.BackupAgentHelper
import android.app.backup.SharedPreferencesBackupHelper
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.pfm.data.common.storage.Storage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yan.zubritskiy.templatemvvm.framework.utils.DeviceUuidFactory
import yan.zubritskiy.templatemvvm.framework.utils.JsonConverter
import java.util.*

private const val PREFERENCES_NAME = "APP_SHARED_PREFERENCES"
private const val RESTORABLE_PREFERENCES_NAME = "APP_RESTORABLE_PREFERENCES"

private const val PUSH_TOKEN_KEY = "FCM_TOKEN_KEY"
private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
private const val UUID_KEY = "UNIQUE_IDENTIFIER_KEY"
private const val MAIN_TRANSACTIONS_LIST_CURSOR = "MAIN_TRANSACTIONS_LIST_CURSOR"

private const val TRANSACTIONS_COUNT_KEY = "TRANSACTIONS_COUNT_KEY"

// A key to uniquely identify the set of backup data
private const val PREFS_BACKUP_KEY = "app_restore_backup_prefs"

@ExperimentalCoroutinesApi
class AppStorage(
    private val context: Context
) : Storage {

    init {
        if (context is Activity) {
            Log.w(AppStorage::class.java.simpleName, "context is activity context!")
        }
    }

    private val sharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    private val restorableSharedPreferences by lazy {
        context.getSharedPreferences(
            RESTORABLE_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }
    private val deviceUuidFactory by lazy {
        DeviceUuidFactory(
            context
        )
    }
    // TODO: 2019-12-19 need to check work of BackupManager and decide code's destiny
//    private val backupManager: BackupManager by lazy { BackupManager(context) }

    private val editor by lazy {
        sharedPreferences.edit()
    }

    override val accessTokenFlow = sharedPreferences
        .getStringPreference(ACCESS_TOKEN_KEY)
        .getAsFlow()

    override var pushNotificationsToken: String?
        get() = sharedPreferences
            .getStringPreference(PUSH_TOKEN_KEY)
            .get()
        set(value) {
            editor.putString(PUSH_TOKEN_KEY, value).apply()
        }
    override var accessToken: String?
        get() = sharedPreferences
            .getStringPreference(ACCESS_TOKEN_KEY)
            .get()
        set(accessToken) {
            synchronized(AppStorage::class.java) {
                editor.putString(ACCESS_TOKEN_KEY, accessToken).apply()
            }
        }

    override var refreshToken: String?
        get() = sharedPreferences
            .getStringPreference(REFRESH_TOKEN_KEY)
            .get()
        set(value) {
            synchronized(AppStorage::class.java) {
                editor.putString(REFRESH_TOKEN_KEY, value).apply()
            }
        }
    override val refreshTokenFlow: Flow<String?> = sharedPreferences
        .getStringPreference(REFRESH_TOKEN_KEY)
        .getAsFlow()

    override val uuid: String
        get() {
            val uuid = restorableSharedPreferences.getStringPreference(UUID_KEY).get()
            return if (uuid.isEmpty()) {
//                    val uuid = deviceUuidFactory.getDeviceUuid().toString()
                // TODO: 2019-12-26 should be replaced on deviceId after MVP ends
                val generatedUUID = UUID.randomUUID().toString()
                restorableSharedPreferences.edit().apply {
                    putString(UUID_KEY, generatedUUID)
                    apply()
                }
                // TODO: 2019-12-19 need to check work of BackupManager and decide code's destiny
//                    backupManager.dataChanged()
                generatedUUID
            } else {
                uuid
            }
        }

    override var mainTransactionsListCursor: String?
        get() = sharedPreferences
            .getStringPreference(MAIN_TRANSACTIONS_LIST_CURSOR)
            .get()
        set(value) {
            synchronized(AppStorage::class.java) {
                editor.putString(MAIN_TRANSACTIONS_LIST_CURSOR, value).apply()
            }
        }

    override var transactionsCount: Long
        get() = sharedPreferences.getLongPreference(TRANSACTIONS_COUNT_KEY, 0L).get()
        set(value) = editor.putLong(TRANSACTIONS_COUNT_KEY, value).apply()

    override val transactionsCountFlow: Flow<Long> =
        sharedPreferences.getLongPreference(TRANSACTIONS_COUNT_KEY, 0L).getAsFlow().map { it ?: 0L }

    override suspend fun clearData() {
        val fcmToken = pushNotificationsToken
        editor.clear().commit()
        pushNotificationsToken = fcmToken
        transactionsCount = 0L
    }
}

// need to check work of BackupManager and decide code's destiny
class PfmPrefsBackupAgent : BackupAgentHelper() {
    override fun onCreate() {
        // Allocate a helper and add it to the backup agent
        SharedPreferencesBackupHelper(this, RESTORABLE_PREFERENCES_NAME).also {
            addHelper(PREFS_BACKUP_KEY, it)
        }
    }
}

private fun SharedPreferences.getStringPreference(key: String, defaultValue: String = "") =
    Preference.StringPreference(this, key, defaultValue)

private fun SharedPreferences.getIntPreference(key: String, defaultValue: Int) =
    Preference.IntPreference(this, key, defaultValue)

private fun SharedPreferences.getBooleanPreference(key: String, defaultValue: Boolean = false) =
    Preference.BooleanPreference(this, key, defaultValue)

private fun SharedPreferences.getFloatPreference(key: String, defaultValue: Float) =
    Preference.FloatPreference(this, key, defaultValue)

private fun SharedPreferences.getLongPreference(key: String, defaultValue: Long) =
    Preference.LongPreference(this, key, defaultValue)

private fun <T : Enum<T>> SharedPreferences.getEnumPreference(
    key: String,
    enumClass: Class<T>,
    defaultValue: T
) =
    Preference.EnumPreference(this, key, enumClass, defaultValue).getAsFlow()

private fun SharedPreferences.getStringSetPreference(
    key: String,
    defaultValue: Set<String> = hashSetOf()
) =
    Preference.StringSetSharePreference(this, key, defaultValue).getAsFlow()

private fun <T> SharedPreferences.getObjectPreference(
    key: String,
    objClass: Class<T>,
    defaultValue: T?,
    jsonConverter: JsonConverter
) =
    Preference.ObjectPreference(
        this,
        key,
        { json -> jsonConverter.deserialize(json, objClass) },
        defaultValue
    )