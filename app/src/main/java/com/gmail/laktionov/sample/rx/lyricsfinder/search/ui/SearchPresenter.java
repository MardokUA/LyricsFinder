package com.gmail.laktionov.sample.rx.lyricsfinder.search.ui;

import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchError;
import com.gmail.laktionov.sample.rx.lyricsfinder.search.repository.RepositoryContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.gmail.laktionov.sample.rx.lyricsfinder.datasource.remote.model.SearchError.ERROR_EMPTY_INPUT;

public class SearchPresenter implements SearchContract.Presenter {

    private final RepositoryContract repository;
    private final SearchError.Handler errorHandler;
    private SearchContract.View view;
    private Disposable subscribe;

    public SearchPresenter(RepositoryContract repository, SearchError.Handler errorHandler) {
        this.repository = repository;
        this.errorHandler = errorHandler;
    }

    @Override
    public void onActivityCreated(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onSearchButtonClick(String name, String song) {
        disposeSubscription();
        if (isInputNotVerified(name, song)) {
            showError(ERROR_EMPTY_INPUT);
            return;
        }
        subscribe = repository.searchLyric(new String[]{name, song}).toObservable()
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

    private void showError(int statusCode) {
        if (isViewAttached()) {
            view.showError(errorHandler.getErrorMessage(statusCode));
        }
    }

    private void changeLoadingState(boolean isShown) {
        if (isViewAttached()) {
            view.showProgress(isShown);
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    private boolean isInputNotVerified(String name, String song) {
        return name.trim().length() == 0 && song.trim().length() == 0;
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
