package com.gmail.laktionov.sample.rx.lyricsfinder.search.datasource.local;

import android.support.v4.util.LruCache;

public class LyricCache extends LruCache<String, String> {

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

    void saveData(String songName, String songText) {
        super.put(songName, songText);
        lastResult = songText;
    }

    String getData(String key) {
        return super.get(key);
    }

    String getLastSavedData() {
        return getLastResult();
    }
}
