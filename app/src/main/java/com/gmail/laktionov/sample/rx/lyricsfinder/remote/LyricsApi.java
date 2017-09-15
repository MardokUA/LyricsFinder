package com.gmail.laktionov.sample.rx.lyricsfinder.remote;

import com.gmail.laktionov.sample.rx.lyricsfinder.model.SongLyric;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LyricsApi {

    @GET("{artist}/{title}")
    Observable<SongLyric> findLyric(@Path("artist") String artistName,
                                    @Path("title") String songName);

}
