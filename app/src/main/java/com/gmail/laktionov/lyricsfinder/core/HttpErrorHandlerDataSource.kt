package com.gmail.laktionov.lyricsfinder.core

import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

//TODO: think about is it reasonable to use simple extension
abstract class HttpErrorHandlerDataSource {

    /**
     * Base extension to Retrofit's synchronous [Call].
     *
     * @return [BaseResponse] with obtained data;
     */
    protected fun <T : Any> Call<T>.executeRequest(): BaseResponse<T> {
        return try {
            val response: Response<T> = execute()
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { BaseResponse.DataResponse(it) }
                    ?: BaseResponse.ErrorResponse(
                        BaseResponse.Type.EMPTY_BODY
                    )
            } else {
                BaseResponse.ErrorResponse(BaseResponse.Type.HTTP_ERROR, response.message())
            }
        } catch (e: Exception) {
            return when (e) {
                is SocketTimeoutException,
                is UnknownHostException,
                is ConnectException -> BaseResponse.ErrorResponse(BaseResponse.Type.CONNECTION_ERROR)
                else -> BaseResponse.ErrorResponse(BaseResponse.Type.UNKNOWN_ERROR)
            }
        }
    }
}