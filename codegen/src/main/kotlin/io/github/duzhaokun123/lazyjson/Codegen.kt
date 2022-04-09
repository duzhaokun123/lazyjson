package io.github.duzhaokun123.lazyjson

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.duzhaokun123.lazyjson.annotation.LazyjsonClass
import io.github.duzhaokun123.lazyjson.model.JsonClassTree
import io.github.duzhaokun123.lazyjson.model.JsonTree
import krobot.api.*
import java.io.ByteArrayOutputStream

object Codegen {
     fun generate(json: JsonElement, packageName: String, className: String): String {
        val jsonTree = JsonTree(json)
        val out = ByteArrayOutputStream()
        kotlinFile {
            `package`(packageName)
            import(JsonObject::class)
            import(LazyjsonClass::class)
            addJsonClassTree(jsonTree.toJsonClassTree(className))
        }.saveTo(out)
        return out.toString()
    }

    private fun AdvancedDeclarationsRobot.addJsonClassTree(jsonClassTree: JsonClassTree, depth: Int = 0) {
        if (depth != 0) private.`fun`("JsonObject.as${jsonClassTree.className}").returns(get("${jsonClassTree.className}(this)"))
        if (depth == 0) `@`("LazyjsonClass") else { this }
            .`class`(jsonClassTree.className).primaryConstructor(private.`val`.parameter("jsonObject") of type("JsonObject")).body {
                `fun`("getJsonObject").returns(get("jsonObject"))
                jsonClassTree.fields.forEach { field ->
                    val asTo = field.className
                    val isNullable = "?" in field.type
                    val isArray = "<" in field.type
                    val q = if (isNullable) "?" else ""
                    if (isArray)
                        `val`(field.name).of(field.type).by(get("lazy { jsonObject.get(\"${field.name}\")$q.asJsonArray$q.map { it.as${asTo} }"))
                    else
                        `val`(field.name).of(field.type).by(get("lazy { jsonObject.get(\"${field.name}\")$q.as$asTo }"))
                }
                jsonClassTree.childClass.forEach { childClass ->
                    addJsonClassTree(childClass, depth + 1)
                }
        }
    }
}