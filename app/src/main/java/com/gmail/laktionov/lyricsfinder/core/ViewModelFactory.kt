package com.gmail.laktionov.lyricsfinder.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.laktionov.lyricsfinder.domain.song.SongRepository
import com.gmail.laktionov.lyricsfinder.ui.search.SearchViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val repository: SongRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}