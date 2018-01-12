package com.gmail.laktionov.sample.rx.lyricsfinder.search.repository;

import android.content.SharedPreferences;
import android.util.Log;

import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.LyricsApi;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchError;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchRepository implements RepositoryContract {

    private static final String TAG = SearchRepository.class.getSimpleName();
    private static final String LAST_ARTIST_SONG = "artist_song";

    private final LyricsApi searchApi;
    private final SharedPreferences sharedPrefs;

    public SearchRepository(Retrofit retrofit, SharedPreferences sharedPrefs) {
        this.sharedPrefs = sharedPrefs;
        searchApi = retrofit.create(LyricsApi.class);
    }

    @Override
    public Single<String> searchLyric(String[] request) {
        return Single.create(emitter -> searchApi.findLyric(request[0], request[1])
                .filter(this::checkResponse)
                .doOnSuccess(this::persistResponse)
                .subscribe(
                        searchResponse -> emitter.onSuccess(searchResponse.getSongText()),
                        throwable -> emitter.onError(new SearchError(SearchError.ERROR_CONNECTION))));
    }

    private boolean checkResponse(SearchResponse searchResponse) {
        return searchResponse != null && searchResponse.getSongText() != null && !searchResponse.getSongText().isEmpty();
    }

    private void persistResponse(SearchResponse searchResponse) {
        Completable.fromRunnable(() -> saveLocalData(searchResponse))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    private void saveLocalData(SearchResponse searchResponse) {
        sharedPrefs.edit()
                .putString(LAST_ARTIST_SONG, searchResponse.getSongText())
                .apply();
        Log.d(TAG, "Response saved");
    }

    @Override
    public Single<String> getLastResponse() {
        return Single.create(emitter -> {
            String lastSongText = sharedPrefs.getString(LAST_ARTIST_SONG, "");
            emitter.onSuccess(lastSongText);
        });
    }
}
