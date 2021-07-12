package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.BuildConfig
import com.gmail.laktionov.lyricsfinder.data.remote.LyricOvhApi
import com.gmail.laktionov.lyricsfinder.data.remote.OvhDataSource
import com.gmail.laktionov.lyricsfinder.data.remote.mapper.OvhResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .apply { this.serializeNulls() }
                .create()
        )

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor()
                .also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }
        ).build()

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