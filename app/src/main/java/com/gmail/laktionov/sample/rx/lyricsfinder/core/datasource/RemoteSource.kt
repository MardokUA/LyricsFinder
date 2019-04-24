package com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource

import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.remote.LyricRequest
import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.remote.LyricResponse

interface RemoteSource {

    fun findLyricRemote(request: LyricRequest): LyricResponse
}