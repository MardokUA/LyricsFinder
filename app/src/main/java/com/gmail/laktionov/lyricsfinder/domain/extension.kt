package com.gmail.laktionov.lyricsfinder.domain

import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.inputmethod.InputMethodManager


fun FragmentActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun FragmentActivity.addWithTransition(fragment: Fragment,
                                       @IdRes frameId: Int,
                                       transitionMask: Int = FragmentTransaction.TRANSIT_ENTER_MASK) {
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

fun Pair<String, String>.isNotEmpty(): Boolean {
    return first.isNotBlank() && second.isNotBlank()
}

fun String.prepareInput(): String {
    return apply {
        this.trimStart()
        this.trimEnd()
    }
}