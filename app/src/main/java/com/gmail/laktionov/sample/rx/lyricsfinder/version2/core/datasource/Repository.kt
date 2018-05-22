package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource

import kotlinx.coroutines.experimental.Deferred

interface Repository {

    fun prepareSearchEngine()

    fun findLyrics(artistName: String, songName: String): String

    fun findLyricsAsync(artistName: String, songName: String): Deferred<String>
}