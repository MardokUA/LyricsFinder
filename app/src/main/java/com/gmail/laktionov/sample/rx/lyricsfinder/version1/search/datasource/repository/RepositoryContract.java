package com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.repository;

import io.reactivex.Single;

public interface RepositoryContract {
    /**
     * Method invokes, when user click on "Search button" in {@link com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.presentation.SearchActivity}
     * It tries to get data from local cache. If data isn't exist, send request to lyrics.ovh.
     *
     * @param artistName name of artist;
     * @param songName   name of the song;
     * @return {@link Single<String> with lyric}
     */
    Single<String> searchLyric(String artistName, String songName);

    /**
     * Get last cached data from local storage, if it exist. Method invokes, when orientation changes;
     *
     * @return {@link Single<String> with lyric}
     */
    Single<String> getLastResponse();
}


