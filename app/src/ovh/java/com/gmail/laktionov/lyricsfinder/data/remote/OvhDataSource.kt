package com.gmail.laktionov.lyricsfinder.data.remote

import com.gmail.laktionov.lyricsfinder.data.remote.mapper.OvhResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.model.ContentItem
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class OvhDataSource(
    private val serverApi: LyricOvhApi,
    private val mapper: OvhResponseMapper
) : RemoteSource {

    override suspend fun findContent(name: String): List<ContentItem> {
        TODO("Not yet implemented")
    }

    override suspend fun findLyricRemote(
        artistName: String,
        songName: String
    ): SongLyric {
        return serverApi.findLyric(artistName, songName).run {
            mapper.mapToSongLyric(artistName, songName, this)
        }
    }
}