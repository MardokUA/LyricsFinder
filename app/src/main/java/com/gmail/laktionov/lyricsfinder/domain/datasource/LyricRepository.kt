package com.gmail.laktionov.lyricsfinder.domain.datasource

import com.gmail.laktionov.lyricsfinder.domain.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.DataResponse
import com.gmail.laktionov.lyricsfinder.domain.SongLyric

class LyricRepository(private val localSource: LocalSource,
                      private val remoteSource: RemoteSource) : Repository {

    override fun findLyric(searchData: Pair<String, String>): BaseResponse<SongLyric> {
        val (artistName, songName) = searchData
        val localLyric = localSource.getLyric(artistName, songName)

        return if (localLyric.isEmpty()) {
            val remoteLyric = remoteSource.findLyricRemote(artistName, songName)
            if (remoteLyric is DataResponse && !remoteLyric.data.isEmpty()) {
                persistData(remoteLyric.data)
            }
            remoteLyric
        } else {
            DataResponse(localLyric)
        }
    }

    private fun persistData(lyric: SongLyric) {
        localSource.storeData(lyric)
    }
}