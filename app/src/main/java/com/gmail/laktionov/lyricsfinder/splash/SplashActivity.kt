package com.gmail.laktionov.lyricsfinder.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.search.SearchActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayStart()
    }

    private fun delayStart() {

        val animator = ObjectAnimator
                .ofFloat(app_label, View.ALPHA, 0f, 1f)
                .setDuration(1_500L)

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                val searchIntent = Intent(this@SplashActivity, SearchActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                startActivity(searchIntent)
                finish()
            }
        })
        animator.start()
    }
}
