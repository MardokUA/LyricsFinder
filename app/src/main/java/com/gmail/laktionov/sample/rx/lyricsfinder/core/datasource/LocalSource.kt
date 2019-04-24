package com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource

import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.local.SongEntity

interface LocalSource {

    fun getLyricLocal(artistName: String, songName: String): SongEntity?

    fun storeData(entity: SongEntity)
}