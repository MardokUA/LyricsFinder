package com.gmail.laktionov.lyricsfinder.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represent main API request
 */
data class OvhRequest(@SerializedName("artist") val artistName: String,
                      @SerializedName("title") val songName: String)