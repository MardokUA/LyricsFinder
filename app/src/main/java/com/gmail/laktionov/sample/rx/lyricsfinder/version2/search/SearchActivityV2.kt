package com.gmail.laktionov.sample.rx.lyricsfinder.version2.search

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivityV2 : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelFactory.getInstance().create(SearchViewModel::class.java)

        viewModel.observeSongData().observe(this,
                Observer { data -> data?.let { proceedResponse(it) } })
        viewModel.observeLoadingState().observe(this,
                Observer { state -> state?.let { updateLoadingState(it) } })

        btn_search_lyric.setOnClickListener {
            viewModel.searchLyric(
                    et_artist_title.text.toString(),
                    et_song_title.text.toString())
        }
    }

    private fun updateLoadingState(isUpdate: Boolean) {
        if (isUpdate) {
            tv_song_lyric.text = null
            hideKeyBoard()
        }
        pb_progress.visibility = if (isUpdate) View.VISIBLE else View.GONE
        btn_search_lyric.isEnabled = !isUpdate
    }

    private fun proceedResponse(data: String) {
        tv_song_lyric.text = data
    }

    private fun hideKeyBoard() {
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}