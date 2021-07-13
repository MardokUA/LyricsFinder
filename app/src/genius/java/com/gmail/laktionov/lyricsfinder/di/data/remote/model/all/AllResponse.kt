package com.gmail.laktionov.lyricsfinder.di.data.remote.model.all

import com.gmail.laktionov.lyricsfinder.di.data.remote.model.Hit
import com.gmail.laktionov.lyricsfinder.di.data.remote.model.base.Meta
import com.google.gson.annotations.SerializedName

data class AllResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("response") val response: ResponseWrapper
) {
    data class ResponseWrapper(@SerializedName("hits") val hits: List<Hit>)
}