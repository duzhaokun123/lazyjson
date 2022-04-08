package io.github.duzhaokun123.lazyjson.retrofit2.converter

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.github.duzhaokun123.lazyjson.annotation.LazyjsonClass
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class LazyjsonConverterFactory: Converter.Factory() {
    companion object {
        fun create(gson: Gson): LazyjsonConverterFactory {
            return LazyjsonConverterFactory().apply { this.gson = gson }
        }
    }

    lateinit var gson: Gson

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val clazz = TypeToken.get(type).rawType
        clazz.getAnnotation(LazyjsonClass::class.java) ?: return null
        return LazyjsonResponseBodyConverter(gson, clazz.getConstructor(JsonObject::class.java))
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val clazz = TypeToken.get(type).rawType
        clazz.getAnnotation(LazyjsonClass::class.java) ?: return null
        return LazyjsonRequestBodyConverter(gson)
    }
}