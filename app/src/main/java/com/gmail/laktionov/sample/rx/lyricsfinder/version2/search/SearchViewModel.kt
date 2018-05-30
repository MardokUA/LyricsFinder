package com.gmail.laktionov.sample.rx.lyricsfinder.version2.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.isNotEmpty
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model.SongLyric
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.prepareInput
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

class SearchViewModel(private val repository: Repository,
                      private val songLyricData: MutableLiveData<String> = MutableLiveData(),
                      private val loadingStateData: MutableLiveData<Boolean> = MutableLiveData(),
                      private val uiContext: CoroutineContext = UI,
                      private val bgContext: CoroutineContext = CommonPool) : ViewModel() {

    //Exception handler mechanism
    private val executionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    //If you need own thread
    private val newContext: CoroutineContext = newSingleThreadContext(THREAD_NAME)
    //Empty job
    private var job: Job = Job()

    fun observeSongData() = songLyricData
    fun observeLoadingState() = loadingStateData

    fun searchLyric(artistName: String, songName: String) {
        job = proceedRequest(artistName, songName)
    }

    //bgContext + executionHandler -> add to context
    private fun proceedRequest(artistName: String, songName: String): Job {
        return launch {
            //STEP #1: show progress
            loadingStateData.postValue(true)
            //STEP #2: prepare request
            val currentRequest = prepareRequest(artistName, songName)
            //STEP #3: start search if valid
            if (currentRequest.isNotEmpty()) {
                val result = repository.findLyrics(currentRequest)
                proceedResponse(result)
            }
            //STEP #4: hide progress
            loadingStateData.postValue(false)
        }
    }

    private fun proceedRequestAsync(artistName: String, songName: String): Job {
        return launch {
            loadingStateData.postValue(true)
            val currentRequest = prepareRequest(artistName, songName)
            if (currentRequest.isNotEmpty()) {
                val result = repository.findLyricsAsync(currentRequest).await()
                proceedResponse(result)
            }
            loadingStateData.postValue(false)
        }
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
        const val THREAD_NAME = "lyric_thread"
    }
}