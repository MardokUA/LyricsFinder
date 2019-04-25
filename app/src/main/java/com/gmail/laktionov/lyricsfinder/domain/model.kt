package com.gmail.laktionov.lyricsfinder.domain


sealed class BaseResponse<T : Any>

data class DataResponse<T : Any>(val data: T) : BaseResponse<T>()

data class ErrorResponse<T : Any>(
        val errorType: Int,
        val errorMessage: String? = null) : BaseResponse<T>()


data class SongLyric(val artistName: String = "",
                     val songName: String = "",
                     val songText: String = "") {
    fun isEmpty() = songText.isEmpty()
}