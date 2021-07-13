package com.gmail.laktionov.lyricsfinder.core

sealed class BaseResponse<in T : Any> {
    data class DataResponse<T : Any>(val data: T) : BaseResponse<T>()
    data class ErrorResponse<T : Any>(val cause: Throwable?) : BaseResponse<T>()
}

