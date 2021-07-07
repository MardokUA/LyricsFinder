package com.gmail.laktionov.lyricsfinder.core

fun Pair<String, String>.isNotEmpty(): Boolean {
    return first.isNotBlank() && second.isNotBlank()
}

fun String.prepareInput(): String {
    return apply {
        this.trimStart()
        this.trimEnd()
    }
}