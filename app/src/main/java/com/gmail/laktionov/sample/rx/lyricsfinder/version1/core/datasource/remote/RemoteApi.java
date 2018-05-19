package com.gmail.laktionov.sample.rx.lyricsfinder.version1.core.datasource.remote;

import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.remote.model.SearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RemoteApi {

    @GET("{artist}/{title}")
    Single<SearchResponse> findLyric(@Path("artist") String artistName,
                                     @Path("title") String songName);
}
