package io.github.duzhaokun123.lazyjson.model

import com.github.salomonbrys.kotson.forEach
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import io.github.duzhaokun123.lazyjson.utils.Counter
import io.github.duzhaokun123.lazyjson.utils.getType
import io.github.duzhaokun123.lazyjson.utils.toBigCamelCase

class JsonTree {
    constructor(json: JsonElement) {
        addJsonElement("", json)
    }

    constructor()

    val nodes = mutableMapOf<JsonNode, Counter>()
    fun addNode(node: JsonNode) {
        if (node.type == JsonType.NULL) return
        nodes.getOrPut(node) { Counter() }.inc()
    }
    fun getNode(path: String): JsonNode? {
        return nodes.keys.find { it.path == path }
    }
    fun getCount(node: JsonNode): Int {
        return nodes[node]?.count ?: 0
    }

    override fun toString(): String {
        return nodes.map { "${it.key.path}: ${it.key.type}${if (isNullable(it.key)) "?" else ""} (${it.value})" }.joinToString("\n")
    }

    fun isNullable(node: JsonNode): Boolean {
        val count = nodes[node]?.count ?: return false
        val parent = node.parent ?: return false
        if (parent.type == JsonType.ARRAY) return isNullable(parent)
        return count != getCount(parent)
    }

    fun addJsonElement(path: String, element: JsonElement) {
        when(element) {
            is JsonNull -> {}
            is JsonObject -> {
                addNode(JsonNode(path, JsonType.OBJECT))
                element.forEach { s, jsonElement -> addJsonElement("$path.$s", jsonElement) }
            }
            is JsonArray -> {
                addNode(JsonNode(path, JsonType.ARRAY))
                element.forEach { addJsonElement("$path[]", it) }
            }
            is JsonPrimitive -> {
                addNode(JsonNode(path, element.getType()))
            }
        }
    }

    fun getChildren(path: String): List<JsonNode> {
        return nodes.keys.filter { it.path.substringBeforeLast(".") == path }
    }

    fun toJsonClassTree(className: String, path: String = ""): JsonClassTree {
        val tree = JsonClassTree(className)
        getChildren(path).forEach {
            if (it.type == JsonType.ARRAY) return@forEach
            if (it.name == "") return@forEach
            val field = it.toField(isNullable(it), it.path.endsWith("[]"))
            tree.addField(field)
            if (it.type == JsonType.OBJECT)
                tree.addChildClass(toJsonClassTree(field.jsonName.substringBeforeLast("[").toBigCamelCase(), it.path))
        }
        return tree
    }
}