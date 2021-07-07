package com.gmail.laktionov.lyricsfinder.di

import android.content.res.Resources
import com.gmail.laktionov.lyricsfinder.domain.datasource.LocalSource
import com.gmail.laktionov.lyricsfinder.domain.datasource.LyricRepository
import com.gmail.laktionov.lyricsfinder.domain.datasource.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.datasource.Repository
import com.gmail.laktionov.lyricsfinder.domain.datasource.local.DataBase
import com.gmail.laktionov.lyricsfinder.domain.datasource.local.LocalDataSource
import com.gmail.laktionov.lyricsfinder.domain.datasource.remote.LyricApi
import com.gmail.laktionov.lyricsfinder.domain.datasource.remote.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    @Provides
    fun providesRepository(
        resources: Resources,
        localSource: LocalSource,
        remoteSource: RemoteSource
    ): Repository = LyricRepository(
        localSource = localSource,
        remoteSource = remoteSource,
        androidResources = resources
    )

    @Provides
    fun providesLocalSource(db: DataBase): LocalSource =
        LocalDataSource(db)

    @Provides
    fun providesRemoteSource(api: LyricApi): RemoteSource =
        RemoteDataSource(serverApi = api)
}