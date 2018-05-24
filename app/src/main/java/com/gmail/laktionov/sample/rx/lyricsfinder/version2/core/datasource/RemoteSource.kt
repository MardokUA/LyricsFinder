package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.LyricRequest
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.LyricResponse

interface RemoteSource {

    fun findLyricRemote(request: LyricRequest): LyricResponse

    suspend fun findLyricRemoteAsync(request: LyricRequest): LyricResponse
}