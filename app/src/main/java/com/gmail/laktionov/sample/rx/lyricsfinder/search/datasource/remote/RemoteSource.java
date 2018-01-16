package com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.remote;

import com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.remote.model.SearchResponse;

import io.reactivex.Maybe;

public interface RemoteSource {
    Maybe<SearchResponse> searchLyric(String artistName, String artistSong);
}
