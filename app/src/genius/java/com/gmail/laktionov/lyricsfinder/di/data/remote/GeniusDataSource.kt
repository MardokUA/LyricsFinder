package com.gmail.laktionov.lyricsfinder.di.data.remote

import com.gmail.laktionov.lyricsfinder.di.data.remote.mapper.GeniusResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.model.ContentItem
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class GeniusDataSource(
    private val api: GeniusLyricsApi,
    private val mapper: GeniusResponseMapper
) : RemoteSource {

    override suspend fun findContent(name: String): List<ContentItem> {
        return api.searchAnywhere(name).run {
            response.hits.flatMap { hit ->
                hit.result.map { searchItem ->
                    mapper.mapToContentItem(hit.type, searchItem)
                }
            }
        }
    }

    override suspend fun findLyricRemote(artistName: String, songName: String): SongLyric {
        TODO("Not yet implemented")
    }
}