package io.github.duzhaokun123.lazyjson.model

import io.github.duzhaokun123.lazyjson.utils.toBigCamelCase
import io.github.duzhaokun123.lazyjson.utils.toCamelCase

data class JsonNode(var path: String, var type: JsonType) {
    val parent: JsonNode?
        get() {
            val isArray = path.endsWith("]")
            val parentPath = runCatching {
                if (isArray) path.substring(0, path.lastIndexOf("[")) else path.substring(0, path.lastIndexOf("."))
            }
            return parentPath.getOrNull()?.let { JsonNode(it, if (isArray) JsonType.ARRAY else JsonType.OBJECT) }
        }

    val name get() = path.substringAfterLast(".")


    fun toField(isNullable: Boolean, isArray: Boolean): JsonClassTree.Field {
        val jsonName = name.substringBeforeLast("[")
        var clazz = when (type) {
            JsonType.OBJECT -> jsonName.toBigCamelCase()
            JsonType.STRING -> "String"
            JsonType.NUMBER -> "Double"
            JsonType.BOOLEAN -> "Boolean"
            else -> throw IllegalArgumentException("Unsupported type: $type")
        }
        val className = if (type == JsonType.OBJECT) jsonName.toBigCamelCase() else clazz
        if (isArray) clazz = "List<$clazz>"
        if (isNullable) clazz = "$clazz?"
        val name = jsonName.toCamelCase()
        return JsonClassTree.Field(name, clazz, jsonName, className)
    }


}