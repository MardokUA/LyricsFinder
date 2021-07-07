package com.gmail.laktionov.lyricsfinder.domain

import androidx.annotation.WorkerThread
import com.gmail.laktionov.lyricsfinder.core.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

interface Repository {

    @WorkerThread
    fun findLyric(searchData: Pair<String, String>): BaseResponse<SongLyric>
}