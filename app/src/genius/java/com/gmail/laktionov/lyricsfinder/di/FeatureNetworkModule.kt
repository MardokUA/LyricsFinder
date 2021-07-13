package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.BuildConfig
import com.gmail.laktionov.lyricsfinder.di.CompositeNetworkModule.LoggingInterceptor
import com.gmail.laktionov.lyricsfinder.di.base.GeniusHeaderInterceptor
import com.gmail.laktionov.lyricsfinder.di.data.remote.GeniusDataSource
import com.gmail.laktionov.lyricsfinder.di.data.remote.GeniusLyricsApi
import com.gmail.laktionov.lyricsfinder.di.data.remote.mapper.GeniusResponseMapper
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
object FeatureNetworkModule {

    @Provides
    fun providesRemoteSource(api: GeniusLyricsApi, mapper: GeniusResponseMapper): RemoteSource =
        GeniusDataSource(api = api, mapper = mapper)

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
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @Named("headerInterceptor") headerInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

    @Provides
    @Named("headerInterceptor")
    fun provideHeaderInterceptor(): Interceptor = GeniusHeaderInterceptor()

    @Provides
    fun provideResponseMapper(): GeniusResponseMapper = GeniusResponseMapper()
}