package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.BuildConfig
import com.gmail.laktionov.lyricsfinder.data.remote.ovh.LyricOvhApi
import com.gmail.laktionov.lyricsfinder.data.remote.ovh.OvhDataSource
import com.gmail.laktionov.lyricsfinder.data.remote.ovh.mapper.OvhResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

    @Provides
    fun providesRemoteSource(api: LyricOvhApi, mapper: OvhResponseMapper): RemoteSource =
        OvhDataSource(serverApi = api, mapper = mapper)

    @Provides
    fun provideApi(
        factory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): LyricOvhApi = Retrofit.Builder()
        .addConverterFactory(factory)
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(LyricOvhApi::class.java)

    @Provides
    fun provideResponseMapper(): OvhResponseMapper = OvhResponseMapper()
}