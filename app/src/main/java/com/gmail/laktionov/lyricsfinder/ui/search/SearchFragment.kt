package com.gmail.laktionov.lyricsfinder.ui.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.domain.hideKeyboard
import com.gmail.laktionov.lyricsfinder.ui.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by lazy {
        ViewModelFactory.getInstance().create(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchStartBtn.setOnClickListener {
            viewModel.searchLyric(
                    searchArtistEt.text.toString(),
                    searchSongEt.text.toString())
        }
    }

    private fun setupObservers() {
        viewModel.observeSongData().observe(this,
                Observer { data -> data?.let { proceedResponse(it) } })
        viewModel.observeLoadingState().observe(this,
                Observer { state -> state?.let { updateLoadingState(it) } })
    }

    private fun updateLoadingState(isUpdate: Boolean) {
        if (isUpdate) {
            searchSongTextTv.text = null
            activity?.hideKeyboard()
        }
        searchProgress.visibility = if (isUpdate) View.VISIBLE else View.GONE
        searchStartBtn.isEnabled = !isUpdate
    }

    private fun proceedResponse(data: String) {
        searchSongTextTv.text = data
    }

    companion object {
        fun createInstance() = SearchFragment()
    }
}