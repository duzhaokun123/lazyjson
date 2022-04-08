package io.github.duzhaokun123.lazyjson.utils

import java.util.*

operator fun CharSequence.times(i: Int): String {
    val sb = StringBuilder()
    0.until(i).forEach { _ -> sb.append(this) }
    return sb.toString()
}

fun String.toCamelCase(): String {
    val sb = StringBuilder()
    val words = split("_")
    words.forEachIndexed { index, word ->
        if (index == 0) {
            sb.append(word)
        } else {
            sb.append(word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        }
    }
    return sb.toString()
}

fun String.toBigCamelCase(): String {
    return toCamelCase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}