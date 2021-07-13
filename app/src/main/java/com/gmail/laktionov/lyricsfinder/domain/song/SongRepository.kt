package com.gmail.laktionov.lyricsfinder.domain.song

import com.gmail.laktionov.lyricsfinder.core.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

interface SongRepository {

    suspend fun findLyric(searchData: Pair<String, String>): BaseResponse<SongLyric>
}