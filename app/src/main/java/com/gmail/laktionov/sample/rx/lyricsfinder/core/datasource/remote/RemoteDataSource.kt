package com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.remote

import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.RemoteSource
import java.io.IOException

class RemoteDataSource(private val serverApi: LyricApi) : RemoteSource {

    override fun findLyricRemote(request: LyricRequest): LyricResponse {
        return try {
            val result = serverApi.findLyric(request.artistName, request.songName).execute()
            result?.body() ?: LyricResponse(statusCode = EMPTY_BODY)
        } catch (exp: IOException) {
            LyricResponse(statusCode = ERROR_CODE)
        }
    }

    companion object {
        const val ERROR_CODE = 404
        const val EMPTY_BODY = 4044
    }
}