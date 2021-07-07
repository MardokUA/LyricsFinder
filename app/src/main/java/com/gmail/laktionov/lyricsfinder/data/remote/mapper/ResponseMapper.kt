package com.gmail.laktionov.lyricsfinder.data.remote.mapper

import com.gmail.laktionov.lyricsfinder.data.remote.model.LyricResponse
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class ResponseMapper {

    fun mapToSongLyric(artistName: String, songName: String, response: LyricResponse) =
        SongLyric(
            artistName = artistName,
            songName = songName,
            songText = response.songText
        )
}