package com.gmail.laktionov.sample.rx.lyricsfinder.version2

import android.arch.persistence.room.Room
import android.content.Context
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.LyricRepository
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.LocalSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.RemoteSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.DataBase
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.LocalDataSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.RemoteDataSource

/**
 * Created by: Roman Laktionov
 * Date: 24.05.18
 */

object DIManager {

    fun createRepository(context: Context): LyricRepository {
        createStorages(context).let { result -> return LyricRepository(localSource = result.first, remoteSource = result.second) }
    }

    private fun createStorages(context: Context): Pair<LocalSource, RemoteSource> {
        return Pair(LocalDataSource(
                Room.databaseBuilder(context, DataBase::class.java, "lyric.db").build()),
                RemoteDataSource())
    }
}