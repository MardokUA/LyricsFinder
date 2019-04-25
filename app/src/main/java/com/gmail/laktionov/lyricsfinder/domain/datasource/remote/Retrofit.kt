package com.gmail.laktionov.lyricsfinder.domain.datasource.remote

import com.google.gson.annotations.SerializedName
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


/**
 * Represent main API request
 */
data class LyricRequest(@SerializedName("artist") val artistName: String,
                        @SerializedName("title") val songName: String)


/**
 * Represent main API response
 */
data class LyricResponse(@SerializedName("statusCode") val statusCode: Int = 0,
                         @SerializedName("lyrics") val songText: String = "",
                         @SerializedName("error") val errorMessage: String = "") {

    fun isNotEmpty() = songText.isNotEmpty()
    fun hasErrors() = statusCode != 0
}
