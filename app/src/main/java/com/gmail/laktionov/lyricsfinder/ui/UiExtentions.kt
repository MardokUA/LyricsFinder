package com.gmail.laktionov.lyricsfinder.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


fun FragmentActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun FragmentActivity.addWithTransition(
    fragment: Fragment,
    @IdRes frameId: Int,
    transitionMask: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN
) {
    supportFragmentManager.transact {
        setTransition(transitionMask)
        add(frameId, fragment)
    }
}

inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction()
        .apply { action() }
        .commitAllowingStateLoss()
}

fun View.toVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}