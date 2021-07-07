package com.gmail.laktionov.lyricsfinder.di

import com.gmail.laktionov.lyricsfinder.BuildConfig
import com.gmail.laktionov.lyricsfinder.data.remote.LyricApi
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
    fun provideApi(
        factory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): LyricApi = Retrofit.Builder()
        .addConverterFactory(factory)
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(LyricApi::class.java)

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
}