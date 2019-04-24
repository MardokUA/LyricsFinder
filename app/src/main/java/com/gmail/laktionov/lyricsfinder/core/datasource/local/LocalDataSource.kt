package com.gmail.laktionov.lyricsfinder.core.datasource.local

import com.gmail.laktionov.lyricsfinder.core.datasource.LocalSource

class LocalDataSource(private val db: DataBase) : LocalSource {

    override fun getLyricLocal(artistName: String, songName: String) = db.getDao().getSong(artistName = artistName, songName = songName)

    override fun storeData(entity: SongEntity) = db.getDao().addSong(entity)

}