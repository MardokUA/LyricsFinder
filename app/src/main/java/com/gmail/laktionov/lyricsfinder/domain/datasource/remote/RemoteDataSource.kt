package com.gmail.laktionov.lyricsfinder.domain.datasource.remote

import com.gmail.laktionov.lyricsfinder.domain.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.DataResponse
import com.gmail.laktionov.lyricsfinder.domain.ErrorResponse
import com.gmail.laktionov.lyricsfinder.domain.SongLyric
import com.gmail.laktionov.lyricsfinder.domain.datasource.RemoteSource
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteDataSource(private val serverApi: LyricApi) : RemoteSource {

    override fun findLyricRemote(artistName: String, songName: String): BaseResponse<SongLyric> {
        return serverApi.findLyric(artistName, songName)
                .executeRequest()
                .convertResponse { data -> mapToSongLyric(artistName, songName, data) }
    }


    /**
     * Base extension to Retrofit's synchronous [Call].
     *
     * @return [BaseResponse] with obtained data;
     */
    private fun <T : Any> Call<T>.executeRequest(): BaseResponse<T> {
        return try {
            val response: Response<T> = execute()
            if (response.isSuccessful && response.code() == 200) {
                response.body()?.let { DataResponse(it) } ?: ErrorResponse(EMPTY_BODY)
            } else {
                ErrorResponse(response.code(), response.message())
            }
        } catch (e: Exception) {
            return when (e) {
                is SocketTimeoutException,
                is UnknownHostException,
                is ConnectException -> ErrorResponse(CONNECTION_ERROR)
                else -> ErrorResponse(UNKNOWN_ERROR)
            }
        }
    }

    /**
     * Convert base response with mapper
     *
     * @param transform mapper to transform;
     * @return [BaseResponse] with new data value;
     */
    private inline fun <T : Any, R : Any> BaseResponse<T>.convertResponse(transform: (T) -> R): BaseResponse<R> =
            when (this) {
                is ErrorResponse -> ErrorResponse(this.errorType)
                is DataResponse -> DataResponse(transform.invoke(data))
            }

    companion object {
        const val CONNECTION_ERROR = 500
        const val UNKNOWN_ERROR = 520
        const val EMPTY_BODY = 4044
    }
}