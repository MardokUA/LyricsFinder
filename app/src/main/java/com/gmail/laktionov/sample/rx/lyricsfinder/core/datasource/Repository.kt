package com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource

import android.support.annotation.WorkerThread
import com.gmail.laktionov.sample.rx.lyricsfinder.core.model.SongLyric

interface Repository {

    @WorkerThread
    fun findLyric(request: Pair<String, String>): SongLyric

}