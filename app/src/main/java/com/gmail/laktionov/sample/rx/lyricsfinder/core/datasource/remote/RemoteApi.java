package com.gmail.laktionov.sample.rx.lyricsfinder.core.datasource.remote;

import com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.remote.models.SearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RemoteApi {

    @GET("{artist}/{title}")
    Single<SearchResponse> findLyric(@Path("artist") String artistName,
                                     @Path("title") String songName);
}
