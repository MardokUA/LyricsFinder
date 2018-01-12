package com.gmail.laktionov.sample.rx.lyricsfinder.datasource.factory;

import com.gmail.laktionov.sample.rx.lyricsfinder.search.ui.SearchContract;

public interface FactoryContract {
    /**
     * @return instance onf {@link com.gmail.laktionov.sample.rx.lyricsfinder.search.ui.SearchPresenter}
     */
    SearchContract.Presenter getSearchPresenter();

}
