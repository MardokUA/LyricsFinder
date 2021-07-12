package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.BuildConfig
import com.gmail.laktionov.lyricsfinder.di.base.GeniusHeaderInterceptor
import com.gmail.laktionov.lyricsfinder.di.data.remote.GeniusDataSource
import com.gmail.laktionov.lyricsfinder.di.data.remote.GeniusLyricsApi
import com.gmail.laktionov.lyricsfinder.di.data.remote.mapper.GeniusResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
object NetworkModule {

    @Provides
    //TODO: add api into constructor
    fun providesRemoteSource(mapper: GeniusResponseMapper): RemoteSource =
        GeniusDataSource(mapper = mapper)

    @Provides
    fun provideApi(
        factory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): GeniusLyricsApi = Retrofit.Builder()
        .addConverterFactory(factory)
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(GeniusLyricsApi::class.java)

    @Provides
    fun provideResponseMapper(): GeniusResponseMapper = GeniusResponseMapper()

    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .apply { this.serializeNulls() }
                .create()
        )

    @Provides
    fun provideOkHttpClient(
        @Named("loggingInterceptor") loggingInterceptor: Interceptor,
        @Named("headerInterceptor") headerInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

    @Provides
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .also { it.level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Named("headerInterceptor")
    fun provideHeaderInterceptor() = GeniusHeaderInterceptor()
}