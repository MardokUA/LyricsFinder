package com.gmail.laktionov.lyricsfinder.domain.datasource

import androidx.annotation.WorkerThread
import com.gmail.laktionov.lyricsfinder.domain.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.SongLyric

interface Repository {

    @WorkerThread
    fun findLyric(searchData: Pair<String, String>): BaseResponse<SongLyric>
}