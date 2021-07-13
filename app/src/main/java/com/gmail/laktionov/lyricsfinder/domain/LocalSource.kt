package com.gmail.laktionov.lyricsfinder.domain

import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

interface LocalSource {

    fun getLyric(artistName: String, songName: String): SongLyric

    fun storeData(songLyric: SongLyric)
}