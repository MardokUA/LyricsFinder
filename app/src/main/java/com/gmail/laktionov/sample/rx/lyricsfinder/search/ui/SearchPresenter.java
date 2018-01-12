package com.gmail.laktionov.sample.rx.lyricsfinder.search.ui;

import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchError;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.repository.RepositoryContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private final RepositoryContract repository;
    private SearchContract.View view;
    private Disposable subscribe;

    public SearchPresenter(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void onActivityCreated(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onSearchButtonClick(String[] searchParams) {
        disposeSubscription();
        subscribe = repository.searchLyric(searchParams).toObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> changeLoadingState(true))
                .doAfterTerminate(() -> changeLoadingState(false))
                .subscribe(
                        this::showSongText,
                        throwable -> showError(((SearchError) throwable).getStatusCode()));
    }

    @Override
    public void onActivityRecreated() {
        repository.getLastResponse()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showSongText);
    }

    private void showSongText(String songText) {
        if (isViewAttached()) {
            view.showResult(songText);
        }
    }

    private void showError(int errorStatus) {
        if (isViewAttached()) view.showError(errorStatus);
    }

    private void changeLoadingState(boolean isShown) {
        if (isViewAttached()) {
            view.showProgress(isShown);
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void onDestroy() {
        view = null;
        disposeSubscription();
    }

    private void disposeSubscription() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }
}
