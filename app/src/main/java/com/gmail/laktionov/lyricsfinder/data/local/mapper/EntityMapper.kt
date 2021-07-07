package com.gmail.laktionov.lyricsfinder.data.local.mapper

import com.gmail.laktionov.lyricsfinder.data.local.model.SongEntity
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class EntityMapper {

    fun mapToEntity(songLyric: SongLyric) =
        SongEntity(
            artistName = songLyric.artistName,
            songName = songLyric.songName,
            songLyric = songLyric.songText
        )

    fun mapFromEntity(entity: SongEntity?) =
        SongLyric(
            artistName = entity?.artistName ?: "",
            songName = entity?.songName ?: "",
            songText = entity?.songLyric ?: ""
        )
}

