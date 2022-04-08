package io.github.duzhaokun123.lazyjson.model

class JsonClassTree(val className: String) {
    val fields = mutableListOf<Field>()
    val childClass = mutableListOf<JsonClassTree>()

    fun addField(field: Field) {
        fields.add(field)
    }
    fun addChildClass(childClass: JsonClassTree) {
        this.childClass.add(childClass)
    }

    data class Field(val name: String, val type: String, val jsonName: String, val className: String)

    override fun toString(): String {
        return "$className(fields=${fields.joinToString()}, childClass=${childClass.joinToString()})"
    }
}