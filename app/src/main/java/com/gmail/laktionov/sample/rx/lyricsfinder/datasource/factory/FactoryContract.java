package com.gmail.laktionov.sample.rx.lyricsfinder.datasource.factory;

import com.gmail.laktionov.sample.rx.lyricsfinder.search.ui.SearchContract;

public interface FactoryContract {
    /**
     * @return instance of presenter for {@link com.gmail.laktionov.sample.rx.lyricsfinder.search.ui.SearchActivity}
     */
    SearchContract.Presenter getSearchPresenter();

}
