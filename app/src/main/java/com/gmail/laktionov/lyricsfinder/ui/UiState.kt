package com.gmail.laktionov.lyricsfinder.ui

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Data<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}