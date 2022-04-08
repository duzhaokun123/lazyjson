package io.github.duzhaokun123.lazyjson.retrofit2.converter

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Constructor

class LazyjsonResponseBodyConverter<T>(private val gson: Gson, private val constructor: Constructor<T>) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        val jsonObject = gson.fromJson(value.charStream(), JsonObject::class.java)
        return constructor.newInstance(jsonObject)
    }
}