package com.gmail.laktionov.lyricsfinder.domain.datasource.local

import com.gmail.laktionov.lyricsfinder.domain.SongLyric
import com.gmail.laktionov.lyricsfinder.domain.datasource.LocalSource

class LocalDataSource(private val db: DataBase) : LocalSource {

    override fun getLyric(artistName: String, songName: String) =
            db.getDao().getSong(artistName = artistName, songName = songName)
                    .mapFromEntity()

    override fun storeData(songLyric: SongLyric) {
        return db.getDao().addSong(mapToEntity(songLyric))
    }

}