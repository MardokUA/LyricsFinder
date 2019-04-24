package com.gmail.laktionov.lyricsfinder.core.datasource

import com.gmail.laktionov.lyricsfinder.core.datasource.remote.LyricRequest
import com.gmail.laktionov.lyricsfinder.core.datasource.remote.LyricResponse

interface RemoteSource {

    fun findLyricRemote(request: LyricRequest): LyricResponse
}