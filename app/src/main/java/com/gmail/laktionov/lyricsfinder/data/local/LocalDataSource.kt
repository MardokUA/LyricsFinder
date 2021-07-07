package com.gmail.laktionov.lyricsfinder.data.local

import com.gmail.laktionov.lyricsfinder.data.local.mapper.EntityMapper
import com.gmail.laktionov.lyricsfinder.domain.LocalSource
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class LocalDataSource(
    private val db: DataBase,
    private val mapper: EntityMapper
) : LocalSource {

    override fun getLyric(artistName: String, songName: String) =
        db.getDao().getSong(artistName = artistName, songName = songName)
            .run { mapper.mapFromEntity(this) }

    override fun storeData(songLyric: SongLyric) {
        return db.getDao().addSong(mapper.mapToEntity(songLyric))
    }
}