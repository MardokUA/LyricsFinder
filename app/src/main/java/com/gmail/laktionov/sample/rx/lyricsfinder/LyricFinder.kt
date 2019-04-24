package com.gmail.laktionov.sample.rx.lyricsfinder

import android.app.Application
import android.util.Log
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.DIManager
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.LyricRepository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.ViewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by: Roman Laktionov
 * Date: 24.04.19
 */

class LyricFinder : Application() {

    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.initViewModelFactory(
                repository = provideRepository(),
                scope = createScope())
    }

    private fun provideRepository(): LyricRepository =
            DIManager.createRepository(this)

    private fun createScope(): CoroutineScope =
            CoroutineScope(Job() + Dispatchers.IO + provideExceptionHandler())

    private fun provideExceptionHandler(): CoroutineExceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Log.e("LyricFinder", "${throwable.message}")
            }
}