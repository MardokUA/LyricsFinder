package com.gmail.laktionov.lyricsfinder.domain

import com.gmail.laktionov.lyricsfinder.core.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

interface RemoteSource {

    fun findLyricRemote(artistName: String, songName: String): BaseResponse<SongLyric>
}