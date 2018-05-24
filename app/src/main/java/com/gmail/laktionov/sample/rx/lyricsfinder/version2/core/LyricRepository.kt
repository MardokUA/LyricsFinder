package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.LocalSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.RemoteSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.SongEntity
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.LyricRequest
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.LyricResponse
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model.SongLyric
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class LyricRepository(private val localSource: LocalSource,
                      private val remoteSource: RemoteSource,
                      private val uiContext: CoroutineContext = UI) : Repository {
    constructor(localSource: LocalSource,
                remoteSource: RemoteSource) : this(localSource, remoteSource, UI)

    override fun prepareSearchEngine() {

    }

    override suspend fun findLyrics(artistName: String, songName: String): SongLyric {
        val localRequest = async { localSource.getLyricLocal(artistName, songName) }
        val remoteRequest = async { remoteSource.findLyricRemote(createRequest(artistName, songName)) }

        //STEP #2: check local data
        localRequest.await().let { it?.let { return mapFromEntity(it) } }

        //STEP #1: get remote data
        remoteRequest.await().let {
            if (it.isNotEmpty()) persistData(artistName, songName, it)
            return mapFromResponse(it)
        }
    }


    override fun findLyricsAsync(artistName: String, songName: String): Deferred<SongLyric> {
        return async {
            val localResponse = localSource.getLyricLocal(artistName, songName)
            val remoteResponse = remoteSource.findLyricRemote(createRequest(artistName, songName))

            if (localResponse != null) {
                return@async mapFromEntity(localResponse)
            } else {
                if (remoteResponse.isNotEmpty()) persistData(artistName, songName, remoteResponse)
                return@async mapFromResponse(remoteResponse)
            }
        }
    }

    private fun persistData(artistName: String, songName: String, remoteResponse: LyricResponse) {
        launch { localSource.storeData(mapToEntity(artistName, songName, remoteResponse)) }
    }

    private fun mapFromResponse(response: LyricResponse): SongLyric {
        return when {
            response.hasErrors() -> SongLyric(errorMessage = "Nothing found %(")
            else -> SongLyric(singLyric = response.songText)
        }
    }

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