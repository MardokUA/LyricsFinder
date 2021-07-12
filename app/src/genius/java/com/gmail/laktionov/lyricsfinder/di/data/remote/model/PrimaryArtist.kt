package com.gmail.laktionov.lyricsfinder.di.data.remote.model

import com.google.gson.annotations.SerializedName

/*
"api_path":"/artists/298648"
"header_image_url":"https://images.genius.com/ae8ddac5e694348d986128909fce0181.1000x194x1.jpg"
"id":298648
"image_url":"https://images.genius.com/a96f78561af38cdb1cf82566303b94b2.866x866x1.jpg"
"is_meme_verified":false
"is_verified":false
"name":"HammerFall"
"url":"https://genius.com/artists/Hammerfall"
 */
data class PrimaryArtist(
    @SerializedName("id") val id: Long,
    @SerializedName("api_path") val apiPath: String,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("header_image_url") val headerImageUrl: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_meme_verified") val isMemeVerified: Boolean,
    @SerializedName("is_verified") val isVerified: Boolean
)