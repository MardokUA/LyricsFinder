package com.gmail.laktionov.lyricsfinder.data.remote

import com.gmail.laktionov.lyricsfinder.data.remote.model.LyricResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Represent main API to remote connection
 */
interface LyricApi {

    @GET("{artist}/{title}")
    fun findLyric(@Path("artist") artistName: String,
                  @Path("title") songName: String): Call<LyricResponse>
}
