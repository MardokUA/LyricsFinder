package com.gmail.laktionov.lyricsfinder.domain.datasource.remote

import com.gmail.laktionov.lyricsfinder.domain.SongLyric

fun mapToSongLyric(artistName: String,
                   songName: String,
                   response: LyricResponse) =
        SongLyric(
                artistName = artistName,
                songName = songName,
                songText = response.songText)