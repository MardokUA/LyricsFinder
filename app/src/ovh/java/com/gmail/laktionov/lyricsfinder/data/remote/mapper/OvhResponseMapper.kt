package com.gmail.laktionov.lyricsfinder.data.remote.mapper

import com.gmail.laktionov.lyricsfinder.data.remote.model.OvhResponse
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric

class OvhResponseMapper {

    fun mapToSongLyric(artistName: String, songName: String, response: OvhResponse) =
        SongLyric(
            artistName = artistName,
            songName = songName,
            songText = response.songText
        )
}