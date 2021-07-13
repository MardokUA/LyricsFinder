package com.gmail.laktionov.lyricsfinder.domain.model

data class ContentItem(
    val type: ContentType,
    val id: Long,
    val ownerId: Long,
    val title: String,
    val fullTitle: String,
    val headerThumbnail: String,
    val songArtThumbnail: String
) {
    enum class ContentType {
        SONG,
        NONE
    }
}