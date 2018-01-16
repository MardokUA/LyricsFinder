package com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.local;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class SearchLocalSource implements LocalSource {

    private final LyricCache inMemoryCache;

    public SearchLocalSource(LyricCache inMemoryCache) {
        this.inMemoryCache = inMemoryCache;
    }

    @Override
    public void saveData(String key, String value) {
        Completable.fromRunnable(() -> inMemoryCache.saveData(key, value))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    @Override
    public String getData(String key) {
        return inMemoryCache.getData(key);
    }

    @Override
    public String getLastSavedData() {
        return inMemoryCache.getLastSavedData();
    }
}
