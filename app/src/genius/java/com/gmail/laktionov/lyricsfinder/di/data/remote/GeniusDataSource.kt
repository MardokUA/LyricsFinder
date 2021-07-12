package com.gmail.laktionov.lyricsfinder.di.data.remote

import com.gmail.laktionov.lyricsfinder.core.BaseResponse
import com.gmail.laktionov.lyricsfinder.di.data.remote.mapper.GeniusResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class GeniusDataSource(private val mapper: GeniusResponseMapper) : RemoteSource {

    override fun findLyricRemote(artistName: String, songName: String): BaseResponse<SongLyric> {
        TODO("Not yet implemented")
    }
}