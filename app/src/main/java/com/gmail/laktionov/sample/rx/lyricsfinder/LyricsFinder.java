package com.gmail.laktionov.sample.rx.lyricsfinder;

import android.app.Application;

import com.gmail.laktionov.sample.rx.lyricsfinder.version2.DIManager;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.LyricRepository;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.ViewModelFactory;

public class LyricsFinder extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //starts version1
//        PresenterFactory.initInstance(this);
        //starts version2
        ViewModelFactory.Companion.initViewModelFactory(createRepository());
    }

    private LyricRepository createRepository() {
        return DIManager.INSTANCE.createRepository(this);
    }
}
