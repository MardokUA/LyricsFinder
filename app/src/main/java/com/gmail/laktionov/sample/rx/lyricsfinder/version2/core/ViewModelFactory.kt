package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.search.SearchViewModel

class ViewModelFactory private constructor(
        private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository)
                    else -> throwError(modelClass)
                }

            } as T

    private fun <T : ViewModel?> throwError(modelClass: Class<T>): Nothing {
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {

        private lateinit var INSTANCE: ViewModelFactory

        fun getInstance() = INSTANCE

        /**
         * Call ony once to create instance
         */
        fun initViewModelFactory(repository: Repository) {
            INSTANCE = ViewModelFactory(repository)
        }

    }
}