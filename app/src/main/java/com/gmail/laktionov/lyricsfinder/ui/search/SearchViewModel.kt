package com.gmail.laktionov.lyricsfinder.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.laktionov.lyricsfinder.core.BaseResponse.DataResponse
import com.gmail.laktionov.lyricsfinder.core.BaseResponse.ErrorResponse
import com.gmail.laktionov.lyricsfinder.core.isNotEmpty
import com.gmail.laktionov.lyricsfinder.core.prepareInput
import com.gmail.laktionov.lyricsfinder.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val songLyricData: MutableLiveData<String> = MutableLiveData()
    private val loadingStateData: MutableLiveData<Boolean> = MutableLiveData()

    fun observeSongData() = songLyricData
    fun observeLoadingState() = loadingStateData

    fun searchLyric(artistName: String, songName: String) {
        proceedRequestAsync(artistName, songName)
    }

    private fun proceedRequestAsync(artistName: String, songName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentRequest = prepareRequest(artistName, songName)

            // check input and execute request
            if (currentRequest.isNotEmpty()) {
                loadingStateData.postValue(true)
                when (val result = repository.findLyric(currentRequest)) {
                    is DataResponse -> songLyricData.postValue(result.data.songText)
                    is ErrorResponse -> songLyricData.postValue(result.errorMessage)
                }
            } else {
                /* TODO(notify user about empty input) */
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