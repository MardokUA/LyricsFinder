package com.gmail.laktionov.lyricsfinder.domain.model

data class SongLyric(
    val artistName: String = "",
    val songName: String = "",
    val songText: String = ""
) {
    fun isEmpty() = songText.isEmpty()
}