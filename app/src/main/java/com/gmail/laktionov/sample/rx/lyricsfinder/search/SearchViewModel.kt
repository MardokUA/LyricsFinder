package com.gmail.laktionov.sample.rx.lyricsfinder.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.core.isNotEmpty
import com.gmail.laktionov.sample.rx.lyricsfinder.core.model.SongLyric
import com.gmail.laktionov.sample.rx.lyricsfinder.core.prepareInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository,
                      private val scope: CoroutineScope) : ViewModel() {

    private val songLyricData: MutableLiveData<String> = MutableLiveData()
    private val loadingStateData: MutableLiveData<Boolean> = MutableLiveData()

    fun observeSongData() = songLyricData
    fun observeLoadingState() = loadingStateData

    fun searchLyric(artistName: String, songName: String) {
        proceedRequestAsync(artistName, songName)
    }

    private fun proceedRequestAsync(artistName: String, songName: String) {
        scope.launch {
            val currentRequest = prepareRequest(artistName, songName)

            // check input and execute request
            if (currentRequest.isNotEmpty()) {
                loadingStateData.postValue(true)
                val result = repository.findLyric(currentRequest)
                proceedResponse(result)
            } else {
                /*TODO(notify user about empty input)  */
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
    private fun prepareRequest(artistName: String, songName: String) =
            Pair(artistName.prepareInput(), songName.prepareInput())

}