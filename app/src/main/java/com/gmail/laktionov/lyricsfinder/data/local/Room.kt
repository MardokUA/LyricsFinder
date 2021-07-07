package com.gmail.laktionov.lyricsfinder.data.local

import androidx.room.*
import com.gmail.laktionov.lyricsfinder.data.local.DataBase.Companion.DATABASE_VERSION
import com.gmail.laktionov.lyricsfinder.data.local.model.SongEntity

/**
 * Represent database instance
 * @see [RoomDatabase]
 */
@Database(
    entities = [SongEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {

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

    @Query("SELECT * FROM songs WHERE artist = :artistName AND song_name = :songName")
    fun getSong(artistName: String, songName: String): SongEntity
}