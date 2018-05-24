package com.gmail.laktionov.sample.rx.lyricsfinder.version2.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.isNotEmpty
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model.SongLyric
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.prepareInput
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext
import kotlin.coroutines.experimental.CoroutineContext

class SearchViewModel(private val repository: Repository,
                      private val songLyricData: MutableLiveData<String> = MutableLiveData(),
                      private val loadingStateData: MutableLiveData<Boolean> = MutableLiveData(),
                      private val uiContext: CoroutineContext = UI,
                      private val bgContext: CoroutineContext = newSingleThreadContext(THREAD_NAME),
                      private val executionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }) : ViewModel() {

    private var previousRequest: Pair<String, String> = Pair(INIT_DATA, INIT_DATA)

    fun observeSongData() = songLyricData
    fun observeLoadingState() = loadingStateData

    fun searchLyric(artistName: String, songName: String) {
        launch(bgContext + executionHandler) {
            val currentRequest = prepareRequest(artistName, songName)
            if (currentRequest.isNotEmpty() && currentRequest != previousRequest) {
                previousRequest = currentRequest
                startSearchEngine(currentRequest)
            }
        }
    }

    private suspend fun startSearchEngine(currentRequest: Pair<String, String>) {
        val (artistName, songName) = currentRequest
        val result = repository.findLyrics(artistName, songName)
        proceedResponse(result)
    }

    private fun proceedResponse(result: SongLyric) {
        when {
            result.errorMessage.isNotEmpty() -> songLyricData.postValue(result.errorMessage)
            else -> songLyricData.postValue(result.singLyric)
        }
    }

    /**
     * Trims user input to prevent empty requests
     */
    private fun prepareRequest(artistName: String, songName: String) = Pair(artistName.prepareInput(), songName.prepareInput())

    companion object {
        const val INIT_DATA = ""
        const val THREAD_NAME = "lyric_thread"
    }
}