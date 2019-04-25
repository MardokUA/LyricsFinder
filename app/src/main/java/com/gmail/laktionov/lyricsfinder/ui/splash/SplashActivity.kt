package com.gmail.laktionov.lyricsfinder.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.domain.addWithTransition
import com.gmail.laktionov.lyricsfinder.domain.toVisible
import com.gmail.laktionov.lyricsfinder.ui.search.SearchFragment
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

    @Deprecated("activity was switched to fragment", level = DeprecationLevel.ERROR)
    private fun startSearch() {
//        val searchIntent = Intent(this@SplashActivity, SearchActivity::class.java).also {
//            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        }
//        startActivity(searchIntent)
//        finish()
    }
}
