import io.github.duzhaokun123.lazyjson.utils.times
import io.github.duzhaokun123.lazyjson.utils.toBigCamelCase
import io.github.duzhaokun123.lazyjson.utils.toCamelCase
import org.junit.jupiter.api.Test

class TestStrings {
    @Test
    fun testTimes() {
        assert("a" * 0 == "")
        assert("a" * 1 == "a")
        assert("a" * 2 == "aa")
        assert("abc" * 3 == "abcabcabc")
        assert("bb\n" * 3 == "bb\nbb\nbb\n")
    }

    @Test
    fun testToCamelCase() {
        assert("".toCamelCase() == "")
        assert("a".toCamelCase() == "a")
        assert("a_b".toCamelCase() == "aB")
        assert("a_b_c".toCamelCase() == "aBC")
        assert("abo_aaaBB".toCamelCase() == "aboAaaBB")
    }

    @Test
    fun testToBigCamelCase() {
        assert("".toBigCamelCase() == "")
        assert("a".toBigCamelCase() == "A")
        assert("a_b".toBigCamelCase() == "AB")
        assert("a_b_c".toBigCamelCase() == "ABC")
        assert("abo__aaaBB".toBigCamelCase() == "AboAaaBB")
    }
}