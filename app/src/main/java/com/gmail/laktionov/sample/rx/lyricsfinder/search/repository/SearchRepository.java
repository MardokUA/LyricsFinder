package com.gmail.laktionov.sample.rx.lyricsfinder.search.repository;

import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.local.LyricCache;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.local.LocalCache;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.RemoteApi;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchError;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchRepository implements RepositoryContract {

    private static final int ARTIST_NAME = 0;
    private static final int SONG_NAME = 1;

    private final RemoteApi searchApi;
    private final LocalCache localCache;

    public SearchRepository(Retrofit retrofit, LyricCache storage) {
        localCache = storage;
        searchApi = retrofit.create(RemoteApi.class);
    }

    @Override
    public Single<String> searchLyric(String[] request) {
        return Single.create(emitter -> {
            String cachedText = localCache.getData(request[SONG_NAME]);
            if (cachedText != null) {
                emitter.onSuccess(cachedText);
            } else {
                searchApi.findLyric(request[ARTIST_NAME], request[SONG_NAME])
                        .filter(this::checkResponse)
                        .doOnSuccess(searchResponse -> cacheResponse(request[SONG_NAME], searchResponse.getSongText()))
                        .subscribe(
                                searchResponse -> emitter.onSuccess(searchResponse.getSongText()),
                                throwable -> emitter.onError(new SearchError(SearchError.ERROR_CONNECTION)));
            }
        });
    }

    private boolean checkResponse(SearchResponse searchResponse) {
        return searchResponse != null && searchResponse.getSongText() != null && !searchResponse.getSongText().isEmpty();
    }

    private void cacheResponse(String songName, String songText) {
        Completable.fromRunnable(() -> localCache.saveData(songName, songText))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    @Override
    public Single<String> getLastResponse() {
        return Single.create(emitter -> emitter.onSuccess(localCache.getLastSavedData()));
    }
}
