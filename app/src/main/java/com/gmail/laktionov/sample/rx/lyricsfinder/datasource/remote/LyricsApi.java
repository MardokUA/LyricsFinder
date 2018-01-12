package com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote;

import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LyricsApi {

    @GET("{artist}/{title}")
    Single<SearchResponse> findLyric(@Path("artist") String artistName,
                                     @Path("title") String songName);
}
