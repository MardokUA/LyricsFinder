package com.gmail.laktionov.lyricsfinder

import android.app.Application
import android.content.Context
import com.gmail.laktionov.lyricsfinder.di.AppComponent
import com.gmail.laktionov.lyricsfinder.di.AppModule
import com.gmail.laktionov.lyricsfinder.di.DaggerAppComponent

class LyricFinder : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is LyricFinder -> appComponent
        else -> this.applicationContext.appComponent
    }