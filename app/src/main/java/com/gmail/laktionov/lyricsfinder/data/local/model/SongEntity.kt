package com.gmail.laktionov.lyricsfinder.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represent database entity (table)
 * @see [Entity]
 */

@Entity(tableName = "songs")
data class SongEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                      @ColumnInfo(name = "artist") val artistName: String,
                      @ColumnInfo(name = "song_name") val songName: String,
                      @ColumnInfo(name = "song_lyric") val songLyric: String) {

    fun isNotEmpty() = artistName.isNotEmpty() && songName.isNotEmpty() && songLyric.isNotEmpty()
}