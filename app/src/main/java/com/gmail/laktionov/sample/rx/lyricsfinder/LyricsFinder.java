package com.gmail.laktionov.sample.rx.lyricsfinder;

import android.app.Application;

import com.gmail.laktionov.sample.rx.lyricsfinder.version1.core.factory.PresenterFactory;

public class LyricsFinder extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PresenterFactory.initInstance(this);
    }
}
