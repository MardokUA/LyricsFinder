package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model

data class SongLyric(val artistName: String = "",
                     val songName: String = "",
                     val singLyric: String = "",
                     val errorMessage: String = "")

data class SearchResult<out T>(val success: T? = null,
                               val error: Throwable? = null)