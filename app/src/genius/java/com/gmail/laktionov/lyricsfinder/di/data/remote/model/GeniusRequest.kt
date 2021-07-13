package com.gmail.laktionov.lyricsfinder.di.data.remote.model

data class GeniusRequest(
    val artist: String,
    val song: String
) {
    fun isValid(): Boolean = artist.isNotBlank() && song.isNotBlank()
}