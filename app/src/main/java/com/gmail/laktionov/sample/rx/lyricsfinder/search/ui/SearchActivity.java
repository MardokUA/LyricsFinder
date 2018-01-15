package com.gmail.laktionov.sample.rx.lyricsfinder.search.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmail.laktionov.sample.rx.lyricsfinder.R;
import com.gmail.laktionov.sample.rx.lyricsfinder.datasource.factory.PresenterFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private SearchContract.Presenter presenter;

    @BindView(R.id.et_artist_title)
    EditText mArtistName;
    @BindView(R.id.et_song_title)
    EditText mSongName;
    @BindView(R.id.tv_song_lyric)
    TextView mSongLyric;
    @BindView(R.id.btn_search_lyric)
    Button mSearchButton;
    @BindView(R.id.pb_progress)
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initPresenter();
        initSearchEngine();
        hideKeyBoard();

        if (savedInstanceState != null) {
            presenter.onActivityRecreated();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initPresenter() {
        presenter = PresenterFactory.getInstance().getSearchPresenter();
        presenter.onActivityCreated(this);
    }

    private void initSearchEngine() {
        mSearchButton.setOnClickListener(v -> {
            presenter.onSearchButtonClick(mArtistName.getText().toString(), mSongName.getText().toString());
            hideKeyBoard();
        });
    }

    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void showProgress(boolean isShown) {
        if (isShown && !mSongLyric.getText().toString().isEmpty()) {
            mSongLyric.setText(null);
        }
        mProgress.setVisibility(isShown ? View.VISIBLE : View.GONE);
        mSearchButton.setEnabled(!isShown);
    }

    @Override
    public void showResult(String lyric) {
        mSongLyric.setText(lyric);
    }

    @Override
    public void showError(String message) {
        mSongLyric.setText(message);

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
