package com.gmail.laktionov.lyricsfinder.data

import com.gmail.laktionov.lyricsfinder.core.BaseResponse
import com.gmail.laktionov.lyricsfinder.core.Failure
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository {

    protected suspend fun <T : Any> call(block: suspend () -> T): BaseResponse<T> =
        try {
            BaseResponse.DataResponse(block())
        } catch (e: Throwable) {
             BaseResponse.ErrorResponse(
                when (e) {
                    is SocketTimeoutException,
                    is UnknownHostException,
                    is ConnectException -> Failure.General.NetworkException(e)
                    is HttpException -> Failure.General.ServerException(e)
                    else -> Failure.General.UnknownException(e)
                }
            )
        }
}