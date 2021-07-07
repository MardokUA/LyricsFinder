package com.gmail.laktionov.lyricsfinder.core


/* Responses */

sealed class BaseResponse<in T : Any> {

    enum class Type { CONNECTION_ERROR, UNKNOWN_ERROR, EMPTY_BODY, HTTP_ERROR }

    data class DataResponse<T : Any>(val data: T) : BaseResponse<T>()

    data class ErrorResponse<T : Any>(
        val errorType: Type,
        var errorMessage: String? = null
    ) : BaseResponse<T>()

    companion object {
        /**
         * Convert base response with mapper
         *
         * @param transform mapper to transform;
         * @return [BaseResponse] with new data value;
         */
        inline fun <T : Any, R : Any> BaseResponse<T>.convertResponse(transform: (T) -> R): BaseResponse<R> =
            when (this) {
                is ErrorResponse -> ErrorResponse(this.errorType)
                is DataResponse -> DataResponse(transform.invoke(data))
            }
    }
}

