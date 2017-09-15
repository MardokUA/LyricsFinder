package com.gmail.laktionov.sample.rx.lyricsfinder.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_LYRIC = "current_lyric";

    private Disposable mDisposable;

    private EditText mArtistName;
    private EditText mSongName;
    private TextView mSongLyric;
    private Button mSearchButton;
    private ProgressBar mProgress;

    private String mCurrentSongLyric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        hideKeyBoard();
        if (savedInstanceState != null) {
            mCurrentSongLyric = savedInstanceState.getString(CURRENT_LYRIC);
            mSongLyric.setText(mCurrentSongLyric);
        }
    }

    private void initViews() {
        mArtistName = findViewById(R.id.et_artist_title);
        mSongName = findViewById(R.id.et_song_title);
        mSongLyric = findViewById(R.id.tv_song_lyric);
        mSearchButton = findViewById(R.id.btn_search_lyric);
        mProgress = findViewById(R.id.pb_progress);
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
            showProgress();
            hideKeyBoard();
            String artistName = mArtistName.getText().toString().trim();
            String songName = mSongName.getText().toString().trim();
            emitter.onNext(new String[]{artistName, songName});
        })).debounce(1000, TimeUnit.MILLISECONDS);
    }

    private Consumer<SongLyric> mLyricResponse = songLyric -> {
        hideProgress();
        mSongLyric.setText(songLyric.getSongLyric());
        mCurrentSongLyric = mSongLyric.getText().toString();

    };
    private Consumer<Throwable> mLyricOnError = error -> {
        hideProgress();
        mSongLyric.setText(R.string.lyrics_not_found);
        mCurrentSongLyric = mSongLyric.getText().toString();
    };

    private Observer<String[]> mSongLyricObserver = new Observer<String[]>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(String[] strings) {
            Observable<SongLyric> songFinder = ApiFactory.getApi().findLyric(strings[0], strings[1]);
            mDisposable = songFinder
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

    private void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_LYRIC, mCurrentSongLyric);
    }
}
