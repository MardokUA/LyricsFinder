package com.gmail.laktionov.sample.rx.lyricsfinder.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gmail.laktionov.sample.rx.lyricsfinder.R;
import com.gmail.laktionov.sample.rx.lyricsfinder.model.SongLyric;
import com.gmail.laktionov.sample.rx.lyricsfinder.remote.ApiFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable mDisposable;

    private EditText mArtistName;
    private EditText mSongName;
    private TextView mSongLyric;
    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        hideKeyBoard();
    }

    private void initViews() {
        mArtistName = findViewById(R.id.et_artist_title);
        mSongName = findViewById(R.id.et_song_title);
        mSongLyric = findViewById(R.id.tv_song_lyric);
        mSearchButton = findViewById(R.id.btn_search_lyric);
    }

    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Observable<String[]> searchObservable = createSearchObserver();
        searchObservable.subscribe(mSongLyricObserver);
    }

    private Observable<String[]> createSearchObserver() {
        return Observable.create((ObservableOnSubscribe<String[]>) emitter -> mSearchButton.setOnClickListener(view -> {
            String artistName = mArtistName.getText().toString().trim();
            String songName = mSongName.getText().toString().trim();
            emitter.onNext(new String[]{artistName, songName});
        })).debounce(1000, TimeUnit.MILLISECONDS);
    }

    private Consumer<SongLyric> mLyricResponse = songLyric -> mSongLyric.setText(songLyric.getSongLyric());
    private Consumer<Throwable> mLyricOnError = error -> mSongLyric.setText(R.string.lyrics_not_found);

    private Observer<String[]> mSongLyricObserver = new Observer<String[]>() {
        @Override
        public void onSubscribe(Disposable d) {
            mDisposable = d;
        }

        @Override
        public void onNext(String[] strings) {
            Observable<SongLyric> songFinder = ApiFactory.getApi().findLyric(strings[0], strings[1]);
            songFinder
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mLyricResponse, mLyricOnError);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {

        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
