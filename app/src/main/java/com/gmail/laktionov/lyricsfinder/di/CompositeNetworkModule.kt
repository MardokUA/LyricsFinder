package com.gmail.laktionov.lyricsfinder.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Module(includes = [FeatureNetworkModule::class])
object CompositeNetworkModule {

    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .apply { this.serializeNulls() }
                .create()
        )

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor()
        .also { it.level = HttpLoggingInterceptor.Level.BODY }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LoggingInterceptor
}