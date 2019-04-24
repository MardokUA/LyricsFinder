package com.gmail.laktionov.lyricsfinder.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.search.SearchActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayStart()
    }

    private fun delayStart() {
        Thread {
            Thread.sleep(2_000L)
            runOnUiThread {
                startActivity(Intent(this@SplashActivity, SearchActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }.run()
    }
}
