package com.gmail.laktionov.sample.rx.lyricsfinder.datasource.local;

import android.support.v4.util.LruCache;

public class CacheStorage extends LruCache<String, String> {

    private String lastResult;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public CacheStorage(int maxSize) {
        super(maxSize);
    }


    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }
}
