package com.gmail.laktionov.lyricsfinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            setUpNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        binding.homeBottomNavigation.setupWithNavController(
            findNavController(R.id.home_nav_host)
        )
    }
}