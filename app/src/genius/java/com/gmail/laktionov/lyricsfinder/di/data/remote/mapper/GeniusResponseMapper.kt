package com.gmail.laktionov.lyricsfinder.di.data.remote.mapper

import com.gmail.laktionov.lyricsfinder.di.data.remote.model.all.SearchItem
import com.gmail.laktionov.lyricsfinder.domain.model.ContentItem
import com.gmail.laktionov.lyricsfinder.domain.model.ContentItem.ContentType
import java.util.*

class GeniusResponseMapper {

    fun mapToContentItem(hitType: String, item: SearchItem): ContentItem {
        return ContentItem(
            type = hitType.toContentType(),
            id = item.id,
            ownerId = item.ownerId,
            title = item.title,
            fullTitle = item.fullTitle,
            headerThumbnail = item.headerThumbnail ?: "",
            songArtThumbnail = item.songArtThumbnail ?: ""
        )
    }

    private fun String.toContentType(): ContentType {
        return when (this) {
            "song" -> ContentType.valueOf(uppercase(Locale.getDefault()))
            else -> ContentType.NONE
        }
    }
}