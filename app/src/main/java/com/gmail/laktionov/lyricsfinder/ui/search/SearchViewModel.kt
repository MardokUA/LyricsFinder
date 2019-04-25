package com.gmail.laktionov.lyricsfinder.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gmail.laktionov.lyricsfinder.domain.DataResponse
import com.gmail.laktionov.lyricsfinder.domain.ErrorResponse
import com.gmail.laktionov.lyricsfinder.domain.datasource.Repository
import com.gmail.laktionov.lyricsfinder.domain.isNotEmpty
import com.gmail.laktionov.lyricsfinder.domain.prepareInput
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
                when (val result = repository.findLyric(currentRequest)) {
                    is DataResponse -> songLyricData.postValue(result.data.songText)
                    is ErrorResponse -> songLyricData.postValue(result.errorMessage)
                }
            } else {
                /*TODO(notify user about empty input)  */
            }
            loadingStateData.postValue(false)
        }
    }

    /**
     * Trims user input to prevent empty requests
     */
    private fun prepareRequest(artistName: String, songName: String) =
            Pair(artistName.prepareInput(), songName.prepareInput())

}