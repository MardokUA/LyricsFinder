package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model.SongLyric
import kotlinx.coroutines.experimental.Deferred

interface Repository {

    fun prepareSearchEngine()

    suspend fun findLyrics(artistName: String, songName: String): SongLyric

    fun findLyricsAsync(artistName: String, songName: String): Deferred<SongLyric>

}