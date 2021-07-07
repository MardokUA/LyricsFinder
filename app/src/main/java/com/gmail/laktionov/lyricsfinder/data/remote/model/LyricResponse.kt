package com.gmail.laktionov.lyricsfinder.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represent main API response
 */
data class LyricResponse(@SerializedName("statusCode") val statusCode: Int = 0,
                         @SerializedName("lyrics") val songText: String = "",
                         @SerializedName("error") val errorMessage: String = "") {

    fun isNotEmpty() = songText.isNotEmpty()
    fun hasErrors() = statusCode != 0
}