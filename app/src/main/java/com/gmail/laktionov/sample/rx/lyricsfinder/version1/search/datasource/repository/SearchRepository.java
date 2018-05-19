package com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.repository;

import android.annotation.SuppressLint;

import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.local.SearchLocalSource;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.remote.SearchRemoteSource;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.remote.model.SearchError;

import io.reactivex.Single;

public class SearchRepository implements RepositoryContract {

    private final SearchRemoteSource remoteSource;
    private final SearchLocalSource localSource;

    public SearchRepository(SearchRemoteSource remoteSource, SearchLocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<String> searchLyric(String artistName, String songName) {
        return Single.create(emitter -> {
            String cachedText = localSource.getData(songName);
            if (cachedText != null) {
                emitter.onSuccess(cachedText);
            } else {
                remoteSource.searchLyric(artistName, songName)
                        .doOnSuccess(searchResponse -> localSource.saveData(songName, searchResponse.getSongText()))
                        .subscribe(
                                searchResponse -> emitter.onSuccess(searchResponse.getSongText()),
                                throwable -> emitter.onError(new SearchError(throwable.getMessage())));
            }
        });
    }

    @Override
    public Single<String> getLastResponse() {
        return Single.create(emitter -> {
            if (localSource.getLastSavedData() != null) {
                emitter.onSuccess(localSource.getLastSavedData());
            }
        });
    }
}
