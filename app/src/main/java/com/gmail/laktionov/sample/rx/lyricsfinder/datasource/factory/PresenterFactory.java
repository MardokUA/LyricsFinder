package com.gmail.laktionov.sample.rx.lyricsfinder.datasource.factory;

import android.content.Context;

import com.gmail.laktionov.sample.rx.lyricsfinder.BuildConfig;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.local.LyricCache;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchError;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.repository.SearchRepository;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.ui.SearchContract;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.ui.SearchPresenter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PresenterFactory implements FactoryContract {

    private static PresenterFactory INSTANCE;
    private final Retrofit retrofit;
    private LyricCache inMemoryStorage;
    private SearchError.Handler errorHandler;

    public static PresenterFactory getInstance() {
        return INSTANCE;
    }

    public static PresenterFactory initInstance(Context context) {
        INSTANCE = new PresenterFactory(context);
        return INSTANCE;
    }

    private PresenterFactory(Context context) {
        retrofit = initRetrofit();
        inMemoryStorage = new LyricCache(10);
        errorHandler = new SearchError.Handler(context);
    }

    private Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Override
    public SearchContract.Presenter getSearchPresenter() {
        return new SearchPresenter(new SearchRepository(retrofit, inMemoryStorage), errorHandler);
    }
}
