package com.gmail.laktionov.lyricsfinder.di.data.remote

import com.gmail.laktionov.lyricsfinder.di.data.remote.model.AllResponse
import com.gmail.laktionov.lyricsfinder.di.data.remote.model.ArtistSongsResponse
import com.gmail.laktionov.lyricsfinder.di.data.remote.model.SongResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeniusLyricsApi {

    @GET("search/")
    suspend fun searchAnywhere(@Query(value = "q") itemName: String): AllResponse

    @GET("/artists/{id}/songs")
    suspend fun searchByArtistId(
        @Path(value = "id") artistId: Long,
        @Query(value = "per_page") pageCount: Int
    ): ArtistSongsResponse

    @GET("/songs/{id}")
    suspend fun getSongById(@Path(value = "id") songId: Long): SongResponse
}