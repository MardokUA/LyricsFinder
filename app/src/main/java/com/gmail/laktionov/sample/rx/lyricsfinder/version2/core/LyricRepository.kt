package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.LocalSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.RemoteSource
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository

class LyricRepository(private val localSource: LocalSource,
                      private val remoteSource: RemoteSource) : Repository {
}