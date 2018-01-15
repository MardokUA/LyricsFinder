package com.gmail.laktionov.sample.rx.lyricsfinder.search.repository;

import io.reactivex.Single;

public interface RepositoryContract {

    /**
     * Method invokes, when user click on "Search button" in {@link com.gmail.laktionov.sample.rx.lyricsfinder.search.ui.SearchActivity}
     * It tries to get data from local cache. If data isn't exist, send request to lyrics.ovh.
     *
     * @param request array, that contains request data, where [0] = artist's name, [1] = name of the song;
     * @return {@link Single<String> with lyric}
     */
    Single<String> searchLyric(String[] request);

    /**
     * Get last cached data from local storage, if it exist. Method invokes, when orientation changes;
     *
     * @return {@link Single<String> with lyric}
     */
    Single<String> getLastResponse();
}


