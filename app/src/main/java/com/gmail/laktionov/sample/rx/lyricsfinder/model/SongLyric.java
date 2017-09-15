package com.gmail.laktionov.sample.rx.lyricsfinder.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongLyric {

    @Expose
    @SerializedName("statusCode")
    private Integer mStatusCode;
    @Expose
    @SerializedName("lyrics")
    private String mSongLyric;

    public SongLyric() {
    }

    public Integer getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Integer mStatusCode) {
        this.mStatusCode = mStatusCode;
    }

    public String getSongLyric() {
        return mSongLyric;
    }

    public void setSongLyric(String mSongLyric) {
        this.mSongLyric = mSongLyric;
    }
}
