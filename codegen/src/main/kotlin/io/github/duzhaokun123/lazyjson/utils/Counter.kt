package io.github.duzhaokun123.lazyjson.utils

class Counter {
    var count = 0
       private set
    fun inc() {
        count++
    }

    override fun toString(): String {
        return count.toString()
    }
}