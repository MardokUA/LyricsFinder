package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body

/**
 * Represent main API to remote connection
 */
interface LyricApi {

    fun findLyric(@Body request: LyricRequest): Call<LyricResponse>
}

/**
 * Represent main API request
 */
data class LyricRequest(@SerializedName("artist") val artistName: String,
                        @SerializedName("title") val songName: String)

/**
 * Represent main API response
 */
data class LyricResponse(@SerializedName("statusCode") val statusCode: Int,
                         @SerializedName("lyrics") val songText: String = "",
                         @SerializedName("error") val errorMessage: String = "") {

    fun isNotEmpty() = errorMessage.isNotEmpty()
}