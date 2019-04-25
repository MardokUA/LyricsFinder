package com.gmail.laktionov.lyricsfinder.domain.datasource

import com.gmail.laktionov.lyricsfinder.domain.SongLyric

interface LocalSource {

    fun getLyric(artistName: String, songName: String): SongLyric

    fun storeData(songLyric: SongLyric)

}