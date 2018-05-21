package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource

interface Repository {

    fun prepareSearchEngine()

    fun findLyrics(artistName: String, songName: String): String
}