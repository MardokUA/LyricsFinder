package com.gmail.laktionov.sample.rx.lyricsfinder;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.gmail.laktionov.sample.rx.lyricsfinder.version1.core.factory.PresenterFactory;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.DIManager;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.LyricRepository;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.ViewModelFactory;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.LocalSource;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.RemoteSource;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.Repository;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.DataBase;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.LocalDataSource;
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.remote.RemoteDataSource;

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
