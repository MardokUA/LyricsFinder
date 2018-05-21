package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote

import com.gmail.laktionov.sample.rx.lyricsfinder.BuildConfig
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.RemoteSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource : RemoteSource {

    private val serverApi: LyricApi by lazy { createApi() }

    private fun createApi(): LyricApi {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(LyricApi::class.java)
    }

    private fun provideOkHttpClient() = OkHttpClient.Builder().addInterceptor(provideInterceptor()).build()
    private fun provideInterceptor(): Interceptor = HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

    override fun findLyricRemote(request: LyricRequest): LyricResponse {
        TODO("implement")
    }
}