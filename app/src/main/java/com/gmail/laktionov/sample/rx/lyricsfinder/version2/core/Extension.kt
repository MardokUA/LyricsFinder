package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


fun Pair<String, String>.isNotEmpty(): Boolean {
    return first.isNotBlank() && second.isNotBlank()
}

fun String.prepareInput(): String {
    return apply {
        this.trimStart()
        this.trimEnd()
    }
}

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>) {
            if (response.isSuccessful) {
                continuation.resume(response.body()!!)
            } else continuation.resumeWithException(HttpException(response))
        }

        override fun onFailure(call: Call<T>?, t: Throwable) = continuation.resumeWithException(t)
    })
}