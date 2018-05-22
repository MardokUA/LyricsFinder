package com.gmail.laktionov.sample.rx.lyricsfinder.version2.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gmail.laktionov.sample.rx.lyricsfinder.R
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivityV2 : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelFactory.getInstance().create(SearchViewModel::class.java)
        viewModel.observeSongData().observe(this, Observer { it?.let { proceedResponse(it) } })
        viewModel.observeLoadingState().observe(this, Observer { it?.let { updateLoadingState(it) } })

        btn_search_lyric.setOnClickListener { viewModel.searchLyric(et_artist_title.text.toString(), et_song_title.text.toString()) }
    }

    private fun updateLoadingState(isUpdate: Boolean) {
        pb_progress.visibility = if (isUpdate) View.VISIBLE else View.GONE
    }

    private fun proceedResponse(data: String) {
        tv_song_lyric.text = data
    }
}