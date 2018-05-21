package com.gmail.laktionov.sample.rx.lyricsfinder.version2.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model.SongLyric
import kotlin.properties.Delegates

class SearchViewModel(private val repository: Repository,
                      private val songLyricData: MutableLiveData<String> = MutableLiveData(),
                      private val loadingStateData: MutableLiveData<Boolean> = MutableLiveData()) : ViewModel() {

    private var findRequest: Pair<String, String> by Delegates.observable(Pair(INIT_DATA, INIT_DATA),
            { _, oldValue, newValue -> startSearchEngine(oldValue, newValue) })

    fun observeSongData() = songLyricData
    fun observeLoadingState() = loadingStateData

    fun searchLyric(artistName: String, songName: String) {

    }

    private fun startSearchEngine(previousRequest: Pair<String, String>, currentRequest: Pair<String, String>) {
        if (previousRequest != currentRequest && isQueryValid(currentRequest)) {
            val (artistName, songName) = currentRequest
            repository.findLyrics(artistName, songName)
        }
    }

    private fun isQueryValid(currentRequest: Pair<String, String>) = currentRequest.first.isNotEmpty() && currentRequest.second.isNotEmpty()

    companion object {
        const val INIT_DATA = ""
    }
}