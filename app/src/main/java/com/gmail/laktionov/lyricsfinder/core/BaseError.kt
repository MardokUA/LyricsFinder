package com.gmail.laktionov.lyricsfinder.core

sealed class Failure(cause: Throwable? = null) : Throwable(cause) {

    sealed class General(cause: Throwable?) : Failure(cause) {
        class NetworkException(cause: Throwable) : General(cause)
        class ServerException(cause: Throwable) : General(cause)
        class UnknownException(cause: Throwable?) : General(cause)
    }
}
