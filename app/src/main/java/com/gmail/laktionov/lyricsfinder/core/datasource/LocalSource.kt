package com.gmail.laktionov.lyricsfinder.core.datasource

import com.gmail.laktionov.lyricsfinder.core.datasource.local.SongEntity

interface LocalSource {

    fun getLyricLocal(artistName: String, songName: String): SongEntity?

    fun storeData(entity: SongEntity)
}