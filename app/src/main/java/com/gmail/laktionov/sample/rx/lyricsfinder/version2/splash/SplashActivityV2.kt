package com.gmail.laktionov.sample.rx.lyricsfinder.version2.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class SplashActivityV2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayStart()
    }

    private fun delayStart() {
        launch(UI) {
            delay(2)
            TODO("implement start SearchV2")
        }
    }
}