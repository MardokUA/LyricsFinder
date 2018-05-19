package com.gmail.laktionov.sample.rx.lyricsfinder.version1.search.datasource.local;

public interface LocalSource {

    /**
     * Cache data in memory storage.
     *
     * @param key   name of the song;
     * @param value text of the song;
     */
    void saveData(String key, String value);

    /**
     * Get data from memory storage;
     *
     * @param key name of the song;
     * @return lyric or null, if ho such data was cached;
     */
    String getData(String key);

    /**
     * When getData() was invoked and end successful, name of the song stored in local field;
     * This method will call, when orientation of device changes;
     *
     * @return last successful lyric.
     */
    String getLastSavedData();
}
