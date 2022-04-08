import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonElement
import io.github.duzhaokun123.lazyjson.Codegen

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Usage: <package> <class>")
        return
    }
    val json = Gson().fromJson<JsonElement>(System.`in`.reader())
    val code = Codegen.generate(json, args[0], args[1])
    print(code)
}