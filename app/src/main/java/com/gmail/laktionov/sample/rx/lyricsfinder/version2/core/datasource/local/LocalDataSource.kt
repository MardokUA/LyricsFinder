package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.LocalSource

class LocalDataSource(private val db: DataBase) : LocalSource {

    override fun getLocalLyric(artistName: String, songName: String) = db.getDao().getSong(artistName = artistName, songName = songName)

    override fun storeData(entity: SongEntity) = db.getDao().addSong(entity)

}