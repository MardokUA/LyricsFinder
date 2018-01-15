package com.gmail.laktionov.sample.rx.lyricsfinder;

import android.app.Application;

import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.factory.PresenterFactory;

public class LyricsFinder extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PresenterFactory.initInstance();
    }
}
