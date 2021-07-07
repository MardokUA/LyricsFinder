package com.gmail.laktionov.lyricsfinder.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.ui.addWithTransition
import com.gmail.laktionov.lyricsfinder.ui.search.SearchFragment
import com.gmail.laktionov.lyricsfinder.ui.toVisible
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayStart()
    }

    private fun delayStart() {
        val animator = ObjectAnimator
                .ofFloat(app_label, View.ALPHA, 0f, 1f, 1f, 1f, 0f)
                .setDuration(2_500L)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                app_label.toVisible(false)
                showSearchFragment()
            }

        })
        animator.start()
    }

    private fun showSearchFragment() {
        addWithTransition(
                SearchFragment.createInstance(),
                R.id.app_fragment_container,
                FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }
}
