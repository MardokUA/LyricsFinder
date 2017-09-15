package com.gmail.laktionov.sample.rx.lyricsfinder.remote;

import com.gmail.laktionov.sample.rx.lyricsfinder.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static Retrofit mRetrofit;

    public static LyricsApi getApi() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return mRetrofit.create(LyricsApi.class);
    }

}
