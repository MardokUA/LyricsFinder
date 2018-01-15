package com.gmail.laktionov.sample.rx.lyricsfinder.search.ui;

public interface SearchContract {

    interface Presenter {

        /**
         * Set activity reference to {@link SearchPresenter}
         *
         * @param view represent {@link SearchActivity}
         */
        void onActivityCreated(SearchContract.View view);

        /**
         * Transfer search request data to {@link Presenter}
         *
         * @param searchParams array, that contains search params;
         */
        void onSearchButtonClick(String[] searchParams);

        /**
         * Invalidate {@link SearchPresenter} state, when activity is destroying;
         */

        void onActivityRecreated();

        void onDestroy();
    }

    interface View {

        /**
         * Force {@link SearchActivity} to show or hide progress bar
         *
         * @param isShown state of progress bar visibility;
         */
        void showProgress(boolean isShown);

        /**
         * Force {@link SearchActivity} to show response result;
         *
         * @param lyric String with song text
         */

        void showResult(String lyric);

        /**
         * Show error text or clear TextView,
         * if message is empty;
         *
         * @param statusCode type of error;
         */
        void showError(int statusCode);
    }
}
