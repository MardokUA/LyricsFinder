package com.gmail.laktionov.lyricsfinder.domain.datasource

import android.content.res.Resources
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.domain.BaseResponse
import com.gmail.laktionov.lyricsfinder.domain.BaseResponse.DataResponse
import com.gmail.laktionov.lyricsfinder.domain.BaseResponse.ErrorResponse
import com.gmail.laktionov.lyricsfinder.domain.SongLyric
import com.gmail.laktionov.lyricsfinder.domain.Type

class LyricRepository(private val localSource: LocalSource,
                      private val remoteSource: RemoteSource,
                      private val androidResources: Resources) : Repository {

    override fun findLyric(searchData: Pair<String, String>): BaseResponse<SongLyric> {
        val (artistName, songName) = searchData
        val localLyric = localSource.getLyric(artistName, songName)

        return if (localLyric.isEmpty()) {
            val remoteLyric = remoteSource.findLyricRemote(artistName, songName).localizeError()
            if (remoteLyric is DataResponse && !remoteLyric.data.isEmpty()) {
                persistData(remoteLyric.data)
            }
            remoteLyric
        } else {
            DataResponse(localLyric)
        }
    }

    private fun <T : Any> BaseResponse<T>.localizeError(): BaseResponse<T> = apply {
        if (this is ErrorResponse) {
            errorMessage = when (errorType) {
                Type.CONNECTION_ERROR -> androidResources.getString(R.string.error_connection)
                Type.UNKNOWN_ERROR -> androidResources.getString(R.string.error_default)
                Type.EMPTY_BODY -> androidResources.getString(R.string.error_not_found)
                Type.HTTP_ERROR -> androidResources.getString(R.string.error_server_default)
            }
        }
    }

    private fun persistData(lyric: SongLyric) {
        localSource.storeData(lyric)
    }
}