package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.SongEntity

interface LocalSource {

    fun getLocalLyric(artistName: String, songName: String): SongEntity

    fun storeData(entity: SongEntity)
}