package com.gmail.laktionov.lyricsfinder.domain


/* Responses */

sealed class BaseResponse<T : Any> {

    data class DataResponse<T : Any>(val data: T) : BaseResponse<T>()

    data class ErrorResponse<T : Any>(
        val errorType: Type,
        var errorMessage: String? = null
    ) : BaseResponse<T>()
}

enum class Type { CONNECTION_ERROR, UNKNOWN_ERROR, EMPTY_BODY, HTTP_ERROR }


/* Models */

data class SongLyric(
    val artistName: String = "",
    val songName: String = "",
    val songText: String = ""
) {
    fun isEmpty() = songText.isEmpty()
}