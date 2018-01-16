package com.gmail.laktionov.sample.rx.lyricsfinder.core.factory;

import com.gmail.laktionov.sample.rx.lyricsfinder.search.presentation.SearchContract;

public interface FactoryContract {
    /**
     * @return instance of presenter for {@link com.gmail.laktionov.sample.rx.lyricsfinder.search.presentation.SearchActivity}
     */
    SearchContract.Presenter getSearchPresenter();
}
