package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource

import android.support.annotation.WorkerThread
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.model.SongLyric
import kotlinx.coroutines.Deferred

interface Repository {

    @WorkerThread
    fun findLyric(request: Pair<String, String>): SongLyric

}