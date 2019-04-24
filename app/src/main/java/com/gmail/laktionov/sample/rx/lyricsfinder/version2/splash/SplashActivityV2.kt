package com.gmail.laktionov.sample.rx.lyricsfinder.version2.splash

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.search.SearchActivityV2

class SplashActivityV2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayStart()
    }

    private fun delayStart() {
        Thread {
            SystemClock.sleep(2_000L)
            runOnUiThread {
                startActivity(Intent(this@SplashActivityV2, SearchActivityV2::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }
    }
}
