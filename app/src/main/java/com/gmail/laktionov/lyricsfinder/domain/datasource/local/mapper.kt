package com.gmail.laktionov.lyricsfinder.domain.datasource.local

import com.gmail.laktionov.lyricsfinder.domain.SongLyric


fun mapToEntity(songLyric: SongLyric) =
        SongEntity(
                artistName = songLyric.artistName,
                songName = songLyric.songName,
                songLyric = songLyric.songText)

fun SongEntity?.mapFromEntity() =
        SongLyric(
                artistName = this?.artistName ?: "",
                songName = this?.songName ?: "",
                songText = this?.songLyric ?: "")