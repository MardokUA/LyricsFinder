package com.gmail.laktionov.lyricsfinder.di

import android.content.Context
import com.gmail.laktionov.lyricsfinder.ui.search.SearchFragment
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(fragment: SearchFragment)
}


@Module(
    includes = [
        CompositeNetworkModule::class,
        DataModule::class,
        StorageModule::class]
)
class AppModule(private val context: Context) {

    @Provides
    fun providesContext(): Context = context
}