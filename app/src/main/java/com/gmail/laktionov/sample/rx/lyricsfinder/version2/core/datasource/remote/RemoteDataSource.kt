package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote

import com.gmail.laktionov.sample.rx.lyricsfinder.BuildConfig
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.await
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.RemoteSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RemoteDataSource : RemoteSource {

    private val serverApi: LyricApi by lazy { createApi() }

    override fun findLyricRemote(request: LyricRequest): LyricResponse {
        return try {
            val result = serverApi.findLyric(request.artistName, request.songName).execute()
            result?.body() ?: LyricResponse(statusCode = EMPTY_BODY)
        } catch (exp: IOException) {
            LyricResponse(statusCode = ERROR_CODE)
        }
    }

    override suspend fun findLyricRemoteAsync(request: LyricRequest): LyricResponse {
        return serverApi.findLyric(request.artistName, request.songName).await()
    }

    private fun createApi(): LyricApi {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .client(provideOkHttpClient())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(LyricApi::class.java)
    }

    private fun provideOkHttpClient() = OkHttpClient.Builder().addInterceptor(provideInterceptor()).build()
    private fun provideInterceptor(): Interceptor = HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

    private fun createGson(): Gson {
        val builder = GsonBuilder().apply { this.serializeNulls() }
        return builder.create()
    }

    companion object {
        const val ERROR_CODE = 404
        const val EMPTY_BODY = 4044
    }
}