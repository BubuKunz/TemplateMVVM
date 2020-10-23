package yan.zubritskiy.templatemvvm.framework.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class JsonConverter(val moshi: Moshi) {

    inline fun <reified T> serialize(obj: T?): String =
        moshi.adapter(T::class.java).toJson(obj)

    inline fun <reified T> serializeList(list: List<T>?): String {
        val listType: Type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        return moshi.adapter<List<T>>(listType).toJson(list)
    }

    fun <T> deserialize(json: String, objClass: Class<T>): T? =
        moshi.adapter(objClass).fromJson(json)

    inline fun <reified T> deserializeList(json: String): List<T>? {
        val listType: Type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        return moshi.adapter<List<T>>(listType).fromJson(json)
    }
}