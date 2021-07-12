package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.di.data.remote.GeniusDataSource
import com.gmail.laktionov.lyricsfinder.di.data.remote.mapper.GeniusResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    //TODO: add api into constructor
    fun providesRemoteSource(mapper: GeniusResponseMapper): RemoteSource =
        GeniusDataSource(mapper = mapper)

//    @Provides
    // TODO: add api realization and uncoment this method
//    fun provideApi(
//        factory: GsonConverterFactory,
//        okHttpClient: OkHttpClient
//    ): MouritsLyricsApi

    @Provides
    fun provideResponseMapper(): GeniusResponseMapper = GeniusResponseMapper()
}