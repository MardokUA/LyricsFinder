package com.gmail.laktionov.lyricsfinder

import android.arch.persistence.room.Room
import android.content.Context
import com.gmail.laktionov.lyricsfinder.domain.datasource.LocalSource
import com.gmail.laktionov.lyricsfinder.domain.datasource.LyricRepository
import com.gmail.laktionov.lyricsfinder.domain.datasource.RemoteSource
import com.gmail.laktionov.lyricsfinder.domain.datasource.local.DataBase
import com.gmail.laktionov.lyricsfinder.domain.datasource.local.LocalDataSource
import com.gmail.laktionov.lyricsfinder.domain.datasource.remote.LyricApi
import com.gmail.laktionov.lyricsfinder.domain.datasource.remote.RemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by: Roman Laktionov
 * Date: 24.05.18
 * Updated: 24.04.19
 */

object DIManager {

    fun createRepository(context: Context): LyricRepository {
        createStorage(context).let { result ->
            return LyricRepository(
                localSource = result.first,
                remoteSource = result.second,
                androidResources = context.resources
            )
        }
    }

    private fun createStorage(context: Context): Pair<LocalSource, RemoteSource> {
        return Pair(
            LocalDataSource(
                Room.databaseBuilder(
                    context,
                    DataBase::class.java,
                    "lyric.db"
                )
                    .build()
            ),
            RemoteDataSource(provideServerApi())
        )
    }

    private fun provideServerApi(): LyricApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .client(provideOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(LyricApi::class.java)

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(provideInterceptor()).build()

    private fun provideInterceptor(): Interceptor =
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

    private fun createGson(): Gson {
        val builder = GsonBuilder().apply { this.serializeNulls() }
        return builder.create()
    }
}