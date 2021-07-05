package com.gmail.laktionov.lyricsfinder.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.appComponent
import com.gmail.laktionov.lyricsfinder.domain.hideKeyboard
import com.gmail.laktionov.lyricsfinder.ui.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: SearchViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observeSongData().observe(this,
            { data -> data?.let { proceedResponse(it) } })
        viewModel.observeLoadingState().observe(this,
            { state -> state?.let { updateLoadingState(it) } })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchStartBtn.setOnClickListener {
            viewModel.searchLyric(
                searchArtistEt.text.toString(),
                searchSongEt.text.toString()
            )
        }
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