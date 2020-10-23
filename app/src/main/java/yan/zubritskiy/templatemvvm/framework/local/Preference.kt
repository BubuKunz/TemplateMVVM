package yan.zubritskiy.templatemvvm.framework.local

import android.content.SharedPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.lang.Enum.valueOf

@ExperimentalCoroutinesApi
sealed class Preference<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String
) {

    abstract fun get(): T?

    private val preferenceFlow = callbackFlow {
        offer(key)
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key -> offer(key) }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    fun getAsFlow() =
        preferenceFlow
            .filter { it == key }
            .map { get() }
            .distinctUntilChanged()

    class StringPreference(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: String = ""
    ) :
        Preference<String>(sharedPreferences, key) {
        override fun get() = sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    class IntPreference(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: Int
    ) :
        Preference<Int>(sharedPreferences, key) {
        override fun get() = sharedPreferences.getInt(key, defaultValue)
    }

    class BooleanPreference(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: Boolean = false
    ) :
        Preference<Boolean>(sharedPreferences, key) {
        override fun get() = sharedPreferences.getBoolean(key, defaultValue)
    }

    class FloatPreference(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: Float
    ) :
        Preference<Float>(sharedPreferences, key) {
        override fun get() = sharedPreferences.getFloat(key, defaultValue)
    }

    class LongPreference(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: Long
    ) :
        Preference<Long>(sharedPreferences, key) {
        override fun get() = sharedPreferences.getLong(key, defaultValue)
    }

    class EnumPreference<ENUM_TYPE : Enum<ENUM_TYPE>>(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val enumClass: Class<ENUM_TYPE>,
        private val defaultValue: ENUM_TYPE
    ) :
        Preference<ENUM_TYPE>(sharedPreferences, key) {
        override fun get() = sharedPreferences.getString(key, null)
            ?.let { serializedEnum -> valueOf(enumClass, serializedEnum) }
            ?: defaultValue
    }

    class StringSetSharePreference(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: Set<String> = hashSetOf()
    ) :
        Preference<Set<String>>(sharedPreferences, key) {
        override fun get(): Set<String> =
            sharedPreferences.getStringSet(key, defaultValue) ?: defaultValue
    }

    class ObjectPreference<OBJECT_TYPE>(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val deserializeFunction: (String) -> OBJECT_TYPE?,
        private val defaultValue: OBJECT_TYPE?
    ) :
        Preference<OBJECT_TYPE>(sharedPreferences, key) {
        override fun get() =
            sharedPreferences.getString(key, null)
                ?.let { serializedObject -> deserializeFunction(serializedObject) }
                ?: defaultValue
    }
}