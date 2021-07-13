package com.gmail.laktionov.lyricsfinder.data

import com.gmail.laktionov.lyricsfinder.core.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.LocalSource
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric
import com.gmail.laktionov.lyricsfinder.domain.song.SongRepository

class LyricRepository(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource
) : BaseRepository(), SongRepository {

    override suspend fun findLyric(searchData: Pair<String, String>): BaseResponse<SongLyric> {
        val (artistName, songName) = searchData
        val localLyric = localSource.getLyric(artistName, songName)

        return if (localLyric.isEmpty()) {
            call {
                remoteSource.findLyricRemote(artistName, songName)
                    .apply { persistData(this) }
            }
        } else {
            BaseResponse.DataResponse(localLyric)
        }
    }

    private fun persistData(lyric: SongLyric) {
        localSource.storeData(lyric)
    }
}