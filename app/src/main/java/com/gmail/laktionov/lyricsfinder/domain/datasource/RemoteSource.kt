package com.gmail.laktionov.lyricsfinder.domain.datasource

import com.gmail.laktionov.lyricsfinder.domain.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.SongLyric

interface RemoteSource {

    fun findLyricRemote(artistName: String, songName: String): BaseResponse<SongLyric>
}