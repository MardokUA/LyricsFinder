package com.gmail.laktionov.lyricsfinder.di

import android.content.res.Resources
import com.gmail.laktionov.lyricsfinder.data.LyricRepository
import com.gmail.laktionov.lyricsfinder.data.local.DataBase
import com.gmail.laktionov.lyricsfinder.data.local.LocalDataSource
import com.gmail.laktionov.lyricsfinder.data.local.mapper.EntityMapper
import com.gmail.laktionov.lyricsfinder.data.remote.LyricApi
import com.gmail.laktionov.lyricsfinder.data.remote.RemoteDataSource
import com.gmail.laktionov.lyricsfinder.data.remote.mapper.ResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.LocalSource
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.Repository
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
    fun providesLocalSource(db: DataBase, mapper: EntityMapper): LocalSource =
        LocalDataSource(db = db, mapper = mapper)

    @Provides
    fun providesRemoteSource(api: LyricApi, mapper: ResponseMapper): RemoteSource =
        RemoteDataSource(serverApi = api, mapper = mapper)

    @Provides
    fun provideEntityMapper(): EntityMapper = EntityMapper()

    @Provides
    fun provideResponseMapper(): ResponseMapper = ResponseMapper()
}