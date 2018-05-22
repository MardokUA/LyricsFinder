package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.LocalSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.RemoteSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.SongEntity
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.LyricRequest
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.LyricResponse
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.CoroutineContext

class LyricRepository(private val localSource: LocalSource,
                      private val remoteSource: RemoteSource,
                      private val uiContext: CoroutineContext = UI) : Repository {

    override fun prepareSearchEngine() {

    }

    override fun findLyrics(artistName: String, songName: String): String {
        return runBlocking(uiContext) {
            val localRequest = async { localSource.getLocalLyric(artistName, songName) }
            val remoteRequest = async { remoteSource.findLyricRemote(createRequest(artistName, songName)) }

            val localResponse = localRequest.await()
            if (localResponse.isNotEmpty()) {
                return@runBlocking mapFromEntity(localResponse)
            } else {
                val remoteResponse = remoteRequest.await()
                if (remoteResponse.isNotEmpty()) persistData(artistName, songName, remoteResponse)
                return@runBlocking mapFromResponse(remoteResponse)
            }
        }
    }

    override fun findLyricsAsync(artistName: String, songName: String): Deferred<String> {
        return async {
            val localResponse = localSource.getLocalLyric(artistName, songName)
            val remoteResponse = remoteSource.findLyricRemote(createRequest(artistName, songName))

            if (localResponse.isNotEmpty()) {
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

    private fun mapFromResponse(response: LyricResponse): String {
        return if (response.errorMessage.isNotEmpty()) response.songText
        else ERROR_TEXT
    }

    private fun mapToEntity(artistName: String, songName: String, response: LyricResponse) = SongEntity(artistName = artistName, songName = songName, songLyric = response.songText)
    private fun mapFromEntity(response: SongEntity) = response.songName
    private fun createRequest(artistName: String, songName: String): LyricRequest = LyricRequest(artistName = artistName, songName = songName)

    companion object {
        const val ERROR_TEXT = "Nothing found ;("
    }
}