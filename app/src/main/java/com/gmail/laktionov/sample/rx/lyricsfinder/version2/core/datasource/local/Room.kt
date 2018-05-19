package com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local

import android.arch.persistence.room.*
import com.gmail.laktionov.sample.rx.lyricsfinder.version2.core.datasource.local.RoomDataBase.Companion.DATABASE_VERSION


/**
 * Represent database instance
 * @see [RoomDatabase]
 */
@Database(
        entities = [SongEntity::class],
        version = DATABASE_VERSION,
        exportSchema = false)

abstract class RoomDataBase : RoomDatabase() {

    abstract fun getDao(): RoomDao

    companion object {
        const val DATABASE_VERSION = 1
    }
}


/**
 * Represent DAO class
 * @see [Dao]
 */
@Dao
interface RoomDao {

    @Insert
    fun addSong(data: SongEntity)
}


/**
 * Represent database entity (table)
 * @see [Entity]
 */

@Entity(tableName = "songs")
data class SongEntity(@ColumnInfo(name = "artist") val artistName: String,
                      @ColumnInfo(name = "song_name") val songName: String,
                      @ColumnInfo(name = "song_lyric") val songLyric: String)