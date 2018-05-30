package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model.SongLyric
import kotlinx.coroutines.experimental.Deferred

interface Repository {

    fun prepareSearchEngine()

    suspend fun findLyrics(request: Pair<String,String>): SongLyric

    fun findLyricsAsync(request: Pair<String, String>): Deferred<SongLyric>

}