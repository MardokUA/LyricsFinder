package com.gmail.laktionov.lyricsfinder.data.remote

import com.gmail.laktionov.lyricsfinder.data.remote.model.OvhResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Represent main API to remote connection
 */
interface LyricOvhApi {

    @GET("{artist}/{title}")
    suspend fun findLyric(
        @Path("artist") artistName: String,
        @Path("title") songName: String
    ): OvhResponse
}
