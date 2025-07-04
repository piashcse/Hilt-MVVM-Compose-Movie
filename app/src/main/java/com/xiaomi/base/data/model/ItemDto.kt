package com.xiaomi.base.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for Item.
 */
data class ItemDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("overview")
    val overview: String?,
    
    @SerializedName("poster_path")
    val posterPath: String?,
    
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    
    @SerializedName("vote_average")
    val voteAverage: Float?,
    
    @SerializedName("release_date")
    val releaseDate: String?,
    
    @SerializedName("original_language")
    val originalLanguage: String?,
    
    @SerializedName("runtime")
    val runtime: Int?,
    
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    
    @SerializedName("genres")
    val genres: List<CategoryDto>? = null
)