package com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.remote;

import com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.remote.RemoteApi;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.remote.model.SearchResponse;

import io.reactivex.Maybe;
import retrofit2.Retrofit;

public class SearchRemoteSource implements RemoteSource {

    private final RemoteApi api;

    public SearchRemoteSource(Retrofit retrofit) {
        api = retrofit.create(RemoteApi.class);
    }

    @Override
    public Maybe<SearchResponse> searchLyric(String artistName, String artistSong) {
        return api.findLyric(artistName, artistSong)
                .filter(this::checkResponse);
    }

    private boolean checkResponse(SearchResponse searchResponse) {
        return searchResponse != null && searchResponse.getSongText() != null && !searchResponse.getSongText().isEmpty();
    }
}
