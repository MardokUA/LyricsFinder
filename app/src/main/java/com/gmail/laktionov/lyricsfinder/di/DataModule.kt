package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.data.LyricRepository
import com.gmail.laktionov.lyricsfinder.data.local.DataBase
import com.gmail.laktionov.lyricsfinder.data.local.LocalDataSource
import com.gmail.laktionov.lyricsfinder.data.local.mapper.EntityMapper
import com.gmail.laktionov.lyricsfinder.domain.LocalSource
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.song.SongRepository
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    @Provides
    fun providesRepository(
        localSource: LocalSource,
        remoteSource: RemoteSource
    ): SongRepository = LyricRepository(
        localSource = localSource,
        remoteSource = remoteSource
    )

    @Provides
    fun providesLocalSource(db: DataBase, mapper: EntityMapper): LocalSource =
        LocalDataSource(db = db, mapper = mapper)

    @Provides
    fun provideEntityMapper(): EntityMapper = EntityMapper()
}