import Static.gson
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import io.github.duzhaokun123.lazyjson.model.JsonTree
import org.junit.jupiter.api.Test

class TestJsonModel {
    @Test
    fun testJsonModel() {
        val json = Static.json3
        val jsonTree = JsonTree().apply {
            addJsonElement("", gson.fromJson(json))
        }
        println(jsonTree)
        println(jsonTree.toJsonClassTree("Test"))
    }
}