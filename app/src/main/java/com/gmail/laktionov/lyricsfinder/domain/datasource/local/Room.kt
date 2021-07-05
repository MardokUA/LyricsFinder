package com.gmail.laktionov.lyricsfinder.domain.datasource.local

import androidx.room.*
import com.gmail.laktionov.lyricsfinder.domain.datasource.local.DataBase.Companion.DATABASE_VERSION

/**
 * Represent database instance
 * @see [RoomDatabase]
 */
@Database(
        entities = [SongEntity::class],
        version = DATABASE_VERSION,
        exportSchema = false)

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