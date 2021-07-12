package com.gmail.laktionov.lyricsfinder.data.remote

import com.gmail.laktionov.lyricsfinder.core.BaseResponse
import com.gmail.laktionov.lyricsfinder.core.BaseResponse.Companion.convertResponse
import com.gmail.laktionov.lyricsfinder.core.HttpErrorHandlerDataSource
import com.gmail.laktionov.lyricsfinder.data.remote.mapper.OvhResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class OvhDataSource(
    private val serverApi: LyricOvhApi,
    private val mapper: OvhResponseMapper
) : HttpErrorHandlerDataSource(), RemoteSource {

    override fun findLyricRemote(artistName: String, songName: String): BaseResponse<SongLyric> {
        return serverApi.findLyric(artistName, songName)
            .executeRequest()
            .convertResponse { data -> mapper.mapToSongLyric(artistName, songName, data) }
    }
}