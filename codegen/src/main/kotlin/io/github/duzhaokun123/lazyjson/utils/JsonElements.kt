package io.github.duzhaokun123.lazyjson.utils

import com.google.gson.*
import io.github.duzhaokun123.lazyjson.model.JsonNode
import io.github.duzhaokun123.lazyjson.model.JsonTree

import io.github.duzhaokun123.lazyjson.model.JsonType

fun JsonElement.getType(): JsonType {
    return when (this) {
        is JsonNull -> JsonType.NULL
        is JsonPrimitive -> when {
            this.isBoolean -> JsonType.BOOLEAN
            this.isNumber -> JsonType.NUMBER
            this.isString -> JsonType.STRING
            else -> throw IllegalArgumentException("Unknown JsonPrimitive type")
        }
        is JsonArray -> JsonType.ARRAY
        is JsonObject -> JsonType.OBJECT
        else -> throw IllegalArgumentException("Unknown JsonElement type")
    }
}