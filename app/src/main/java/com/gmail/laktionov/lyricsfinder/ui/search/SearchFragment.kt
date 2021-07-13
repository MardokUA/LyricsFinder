package com.gmail.laktionov.lyricsfinder.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.laktionov.lyricsfinder.R
import com.gmail.laktionov.lyricsfinder.appComponent
import com.gmail.laktionov.lyricsfinder.domain.model.SongLyric
import com.gmail.laktionov.lyricsfinder.ui.UIState
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
        viewModel.observeLyricState().observe(this,
            { data -> data?.let { applyState(it) } })
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

    private fun applyState(state: UIState<SongLyric>) {
        when (state) {
            UIState.Loading -> searchProgress.visibility = View.VISIBLE
            is UIState.Data -> {
                searchProgress.visibility = View.GONE
                searchSongTextTv.text = state.data.songText
            }
            is UIState.Error -> {
                searchProgress.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun createInstance() = SearchFragment()
    }
}