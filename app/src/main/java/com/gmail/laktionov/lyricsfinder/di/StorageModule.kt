package com.gmail.laktionov.lyricsfinder.di

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.gmail.laktionov.lyricsfinder.domain.datasource.local.DataBase
import dagger.Module
import dagger.Provides

@Module
object StorageModule {

    @Provides
    fun provideDataBase(context: Context): DataBase =
        Room.databaseBuilder(
            context,
            DataBase::class.java,
            "lyric.db"
        ).build()

    @Provides
    //TODO: add wrapper for resources
    fun providesResources(context: Context): Resources =
        context.resources
}