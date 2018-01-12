package com.gmail.laktionov.sample.rx.lyricsfinder.search.repository;

import io.reactivex.Single;

public interface RepositoryContract {

    Single<String> searchLyric(String[] request);

    Single<String> getLastResponse();
}


