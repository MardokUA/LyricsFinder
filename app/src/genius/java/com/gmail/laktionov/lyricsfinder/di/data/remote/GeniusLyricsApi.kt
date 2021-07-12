package com.gmail.laktionov.lyricsfinder.di.data.remote

import com.gmail.laktionov.lyricsfinder.di.data.remote.model.AllResponse
import com.gmail.laktionov.lyricsfinder.di.data.remote.model.ArtistSongsResponse
import com.gmail.laktionov.lyricsfinder.di.data.remote.model.SongResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeniusLyricsApi {

    /**
     * The search capability covers all content hosted on Genius (all songs).
     */
    @GET("search/")
    suspend fun searchAnywhere(@Query(value = "q") itemName: String): AllResponse

    /**
     * Documents (songs) for the artist specified. By default, 20 items are returned for each request.
     */
    @GET("/artists/{id}/songs")
    suspend fun searchByArtistId(
        @Path(value = "id") artistId: Long,
        @Query(value = "per_page") pageCount: Int
    ): ArtistSongsResponse

    /**
     * A song is a document hosted on Genius. It's usually music lyrics. Data for a song includes details about
     * the document itself and information about all the referents that are attached to it,
     * including the text to which they refer.
     */
    @GET("/songs/{id}")
    suspend fun getSongById(@Path(value = "id") songId: Long): SongResponse
}