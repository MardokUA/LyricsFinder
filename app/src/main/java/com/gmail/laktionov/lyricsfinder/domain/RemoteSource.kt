package com.gmail.laktionov.lyricsfinder.domain

import com.gmail.laktionov.lyricsfinder.domain.model.ContentItem
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

interface RemoteSource {

    suspend fun findContent(name: String): List<ContentItem>

    suspend fun findLyricRemote(artistName: String, songName: String): SongLyric
}