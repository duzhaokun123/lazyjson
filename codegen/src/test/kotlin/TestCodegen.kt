import Static.gson
import Static.json1
import Static.json2
import Static.json3
import com.github.salomonbrys.kotson.fromJson
import io.github.duzhaokun123.lazyjson.Codegen
import org.junit.jupiter.api.Test

class TestCodegen {
    @Test
    fun testCodegen() {
        val code1 = Codegen.generate(gson.fromJson(json1), "com.example", "Test")
        println(code1)
        val code2 = Codegen.generate(gson.fromJson(json2), "com.example", "Test")
        println(code2)
        val code3 = Codegen.generate(gson.fromJson(json3), "com.example", "Test")
        println(code3)
    }
}