package com.gmail.laktionov.lyricsfinder.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.laktionov.lyricsfinder.core.BaseViewModel
import com.gmail.laktionov.lyricsfinder.core.isNotEmpty
import com.gmail.laktionov.lyricsfinder.core.prepareInput
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric
import com.gmail.laktionov.lyricsfinder.domain.song.SongRepository
import com.gmail.laktionov.lyricsfinder.ui.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SongRepository) : BaseViewModel() {

    private val songLyricState: MutableLiveData<UIState<SongLyric>> = MutableLiveData()

    fun observeLyricState() = songLyricState

    fun searchLyric(artistName: String, songName: String) {
        proceedRequestAsync(artistName, songName)
    }

    private fun proceedRequestAsync(artistName: String, songName: String) {
        songLyricState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val currentRequest = prepareRequest(artistName, songName)
            if (currentRequest.isNotEmpty()) {
                val state = repository.findLyric(currentRequest).toUiState()
                songLyricState.postValue(state)
            } else {
                songLyricState.postValue(UIState.Data(SongLyric()))
            }
        }
    }

    /**
     * Trims user input to prevent empty requests
     */
    private fun prepareRequest(artistName: String, songName: String) =
        Pair(artistName.prepareInput(), songName.prepareInput())

}