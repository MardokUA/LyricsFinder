package com.gmail.laktionov.lyricsfinder.di.data.remote.model

import com.gmail.laktionov.lyricsfinder.di.data.remote.model.all.SearchItem
import com.google.gson.annotations.SerializedName

/*
"index":"song"
"type":"song"
"result":List<SearchItem>
 */
data class Hit(
    @SerializedName("index") val index: String,
    @SerializedName("type") val type: String,
    @SerializedName("result") val result: List<SearchItem>
)