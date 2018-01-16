package com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.remote.model;

import android.content.Context;

import com.gmail.laktionov.sample.rx.lyricsfinder.R;

public class SearchError extends Throwable {

    public static final int ERROR_CONNECTION = 101;
    public static final int ERROR_EMPTY_INPUT = 102;
    public static final int ERROR_FOUND_NOTHING = 404;
    public static final int ERROR_DEFAULT = 109;
    private int statusCode;

    public SearchError() {

    }

    public SearchError(int statusCode) {
        this.statusCode = statusCode;
    }

    public SearchError(String message) {
        super(message);
        if (message.contains(String.valueOf(ERROR_FOUND_NOTHING))) {
            statusCode = ERROR_FOUND_NOTHING;
        } else {
            statusCode = ERROR_CONNECTION;
        }
    }

    public int getStatusCode() {
        return statusCode == 0 ? ERROR_DEFAULT : statusCode;
    }

    public static class Handler {

        final String CONNECTION;
        final String EMPTY_INPUT;
        final String FOUND_NOTHING;
        final String DEFAULT;

        public Handler(Context context) {
            CONNECTION = context.getResources().getString(R.string.error_connection);
            EMPTY_INPUT = context.getResources().getString(R.string.error_empty_inputs);
            FOUND_NOTHING = context.getResources().getString(R.string.error_not_found);
            DEFAULT = context.getResources().getString(R.string.error_default);
        }

        public String getErrorMessage(int statusCode) {
            switch (statusCode) {
                case SearchError.ERROR_EMPTY_INPUT:
                    return EMPTY_INPUT;
                case SearchError.ERROR_CONNECTION:
                    return CONNECTION;
                case SearchError.ERROR_FOUND_NOTHING:
                    return FOUND_NOTHING;
                default:
                    return DEFAULT;
            }
        }
    }
}
