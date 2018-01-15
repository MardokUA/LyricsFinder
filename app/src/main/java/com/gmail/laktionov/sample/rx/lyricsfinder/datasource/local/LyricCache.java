package com.gmail.laktionov.sample.rx.lyricsfinder.datasource.local;

import android.support.v4.util.LruCache;

public class LyricCache extends LruCache<String, String> implements LocalCache {

    private String lastResult;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LyricCache(int maxSize) {
        super(maxSize);
    }


    private String getLastResult() {
        return lastResult;
    }


    @Override
    public void saveData(String songName, String songText) {
        super.put(songName, songText);
        lastResult = songText;
    }

    @Override
    public String getData(String key) {
        return super.get(key);
    }

    @Override
    public String getLastSavedData() {
        return getLastResult();
    }
}
