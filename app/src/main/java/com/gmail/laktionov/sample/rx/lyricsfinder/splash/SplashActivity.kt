package com.gmail.laktionov.sample.rx.lyricsfinder.splash

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import com.gmail.laktionov.sample.rx.lyricsfinder.LyricFinder
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import com.gmail.laktionov.sample.rx.lyricsfinder.search.SearchActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayStart()
    }

    private fun delayStart() {
        Thread {
            SystemClock.sleep(1_000L)
            runOnUiThread {
                LyricFinder.logSmth("Starting activity... ${Thread.currentThread().name}")
                startActivity(Intent(this@SplashActivity, SearchActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }.run()
    }
}
