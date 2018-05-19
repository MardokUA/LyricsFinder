package com.gmail.laktionov.sample.rx.lyricsfinder.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gmail.laktionov.sample.rx.lyricsfinder.R;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.presentation.SearchActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    private Intent intent;
    private Disposable subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        delayStart();
    }

    private void delayStart() {
        subscription = Completable.fromRunnable(() -> {
            intent = new Intent(this, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(2, TimeUnit.SECONDS)
                .subscribe(() -> {
                    startActivity(intent);
                    finish();
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
