package com.gmail.laktionov.lyricsfinder.di.base

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GeniusHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            //TODO: use api key from BuildConfig
            .addHeader(KEY_API, "8d2a5820dfmsh5aee79d0ddd6702p12c179jsnd75f4bf812df")
            .addHeader(KEY_HOST, "genius.p.rapidapi.com")
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val KEY_API = "x-rapidapi-key"
        private const val KEY_HOST = "x-rapidapi-host"
    }
}