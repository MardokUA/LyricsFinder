package com.gmail.laktionov.lyricsfinder.di.data.remote.model.base

import com.google.gson.annotations.SerializedName

data class Meta(@SerializedName(value = "status") val status: Int)