package com.gmail.laktionov.lyricsfinder.di.data.remote.model.all

import com.gmail.laktionov.lyricsfinder.di.data.remote.model.PrimaryArtist
import com.google.gson.annotations.SerializedName

/*
"id":2918448
"annotation_count":0
"api_path":"/songs/2918448"
"full_title":"Hammer High by HammerFall"
"header_image_thumbnail_url":"https://images.genius.com/eac8ab755a2a45f4ee8c7ed539725bc6.300x300x1.jpg"
"lyrics_owner_id":2006252
"lyrics_state":"complete"
"path":"/Hammerfall-hammer-high-lyrics"
"pyongs_count":NULL
"song_art_image_thumbnail_url":"https://images.genius.com/eac8ab755a2a45f4ee8c7ed539725bc6.300x300x1.jpg"
"song_art_image_url":"https://images.genius.com/eac8ab755a2a45f4ee8c7ed539725bc6.480x480x1.jpg"
"title":"Hammer High"
"url":"https://genius.com/Hammerfall-hammer-high-lyrics"
"song_art_primary_color":"#c86f41"
"song_art_secondary_color":"#8f4223"
"song_art_text_color":"#fff"
"primary_artist": PrimaryArtist

//skipped
"title_with_featured":"Hammer High"
"header_image_url":"https://images.genius.com/eac8ab755a2a45f4ee8c7ed539725bc6.480x480x1.jpg"
"stats":{...}
}
 */
data class SearchItem(
    @SerializedName("id") val id: Long,
    @SerializedName("lyrics_owner_id") val ownerId: Long,
    @SerializedName("lyrics_state") val state: LyricState,
    @SerializedName("title") val title: String,
    @SerializedName("annotation_count") val annotationCount: Int,
    @SerializedName("api_path") val apiPath: String,
    @SerializedName("full_title") val fullTitle: String,
    @SerializedName("header_image_thumbnail_url") val headerThumbnail: String?,
    @SerializedName("song_art_image_thumbnail_url") val songArtThumbnail: String?,
    @SerializedName("song_art_primary_color") val songArtPrimaryColor: String?,
    @SerializedName("song_art_secondary_color") val songArtSecondaryColor: String?,
    @SerializedName("song_art_text_color") val songArtTextColor: String?,
    @SerializedName("primary_artist") val primaryArtist: PrimaryArtist,
) {

    enum class LyricState {
        @SerializedName("complete")
        COMPLETED
    }
}
