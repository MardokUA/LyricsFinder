package com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    @Expose
    @SerializedName("statusCode")
    private Integer statusCode;
    @Expose
    @SerializedName("lyrics")
    private String songText;
    @Expose
    @SerializedName("error")
    private String error;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getSongText() {
        return songText;
    }

    public void setSongText(String songText) {
        this.songText = songText;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
