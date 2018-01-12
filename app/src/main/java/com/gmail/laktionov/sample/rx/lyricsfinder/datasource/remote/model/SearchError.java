package com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model;

public class SearchError extends Throwable {

    public static final int ERROR_CONNECTION = 101;

    private final int statusCode;

    public SearchError(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
