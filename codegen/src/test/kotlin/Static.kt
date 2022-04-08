import com.google.gson.Gson

object Static {
    val gson = Gson()

    val json1 = """
        {
            "name": "John Doe",
            "age": 43,
            "phones": [
                "+44 1234567",
                "+44 2345678"
            ],
            "home": {
                "street": "Downing Street 10",
                "city": "London",
                "postcode": "SW1A 2AA"
            }
        }
    """
    val json2 = """
        {
            "users": [
                {"name":"John", "age":30, "car":null},
                {"name":"Mary", "age":28, "car":"Ford"}
            ]
        }
    """
    val json3 = """
        {
            "a": [
                {"first": "Tom", "last": [{"a": "Cat"}]},
                {"first": "Sam", "last": null}
            ]
        }
    """.trimIndent()
}