package com.gmail.laktionov.sample.rx.lyricsfinder.version1.core.factory;

import android.content.Context;

import com.gmail.laktionov.sample.rx.lyricsfinder.BuildConfig;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.local.SearchCache;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.local.SearchLocalSource;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.remote.SearchRemoteSource;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.remote.model.SearchError;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.repository.SearchRepository;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.presentation.SearchContract;
import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.presentation.SearchPresenter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PresenterFactory implements FactoryContract {

    private static PresenterFactory INSTANCE;

    private final Retrofit retrofit;
    private final SearchCache inMemoryCache;
    private SearchError.Handler errorHandler;

    public static PresenterFactory getInstance() {
        return INSTANCE;
    }

    public static void initInstance(Context context) {
        INSTANCE = new PresenterFactory(context);
    }

    private PresenterFactory(Context context) {
        retrofit = initRetrofit();
        inMemoryCache = new SearchCache(10);
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
        return new SearchPresenter(new SearchRepository(new SearchRemoteSource(retrofit), new SearchLocalSource(inMemoryCache)), errorHandler);
    }
}
