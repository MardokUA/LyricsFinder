package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.BuildConfig
import com.gmail.laktionov.lyricsfinder.data.remote.LyricOvhApi
import com.gmail.laktionov.lyricsfinder.data.remote.OvhDataSource
import com.gmail.laktionov.lyricsfinder.data.remote.mapper.OvhResponseMapper
import com.gmail.laktionov.lyricsfinder.di.CompositeNetworkModule.LoggingInterceptor
import com.gmail.laktionov.lyricsfinder.domain.RemoteSource
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object FeatureNetworkModule {

    @Provides
    fun providesRemoteSource(api: LyricOvhApi, mapper: OvhResponseMapper): RemoteSource =
        OvhDataSource(serverApi = api, mapper = mapper)

    @Provides
    fun provideOkHttpClient(@LoggingInterceptor loggingInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

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