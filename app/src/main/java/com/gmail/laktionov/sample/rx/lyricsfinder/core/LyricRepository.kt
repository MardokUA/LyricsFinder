package com.gmail.laktionov.sample.rx.lyricsfinder.core

import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.LocalSource
import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.RemoteSource
import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.local.SongEntity
import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.remote.LyricRequest
import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.remote.LyricResponse
import com.gmail.laktionov.sample.rx.lyricsfinder.core.model.SongLyric

class LyricRepository(private val localSource: LocalSource,
                      private val remoteSource: RemoteSource) : Repository {

    override fun findLyric(request: Pair<String, String>): SongLyric {
        val (artistName, songName) = request

        val localResponse = localSource.getLyricLocal(artistName, songName)
        return if (localResponse != null) {
            mapFromEntity(localResponse)
        } else {
            val remoteResponse = remoteSource.findLyricRemote(createRequest(artistName, songName))
            if (remoteResponse.isNotEmpty()) {
                persistData(artistName, songName, remoteResponse)
            }
            mapFromResponse(remoteResponse)
        }
    }

    private fun persistData(artistName: String, songName: String, remoteResponse: LyricResponse) {
        localSource.storeData(mapToEntity(artistName, songName, remoteResponse))
    }

    private fun mapFromResponse(response: LyricResponse): SongLyric {
        return when {
            response.hasErrors() -> SongLyric(errorMessage = ERROR_TEXT)
            else -> SongLyric(singLyric = response.songText)
        }
    }

    /*  Mappers */

    private fun mapToEntity(artistName: String, songName: String, response: LyricResponse) = SongEntity(
            artistName = artistName,
            songName = songName,
            songLyric = response.songText)

    private fun mapFromEntity(entity: SongEntity) = SongLyric(
            artistName = entity.artistName,
            songName = entity.songName,
            singLyric = entity.songLyric)

    private fun createRequest(artistName: String, songName: String): LyricRequest = LyricRequest(
            artistName = artistName,
            songName = songName)

    companion object {
        const val ERROR_TEXT = "Nothing found ;("
    }
}