package com.gmail.laktionov.sample.rx.lyricsfinder.version1.core.factory;

import com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.presentation.SearchContract;

public interface FactoryContract {
    /**
     * @return instance of presenter for {@link com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.presentation.SearchActivity}
     */
    SearchContract.Presenter getSearchPresenter();
}
