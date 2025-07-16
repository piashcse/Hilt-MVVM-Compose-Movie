package com.piashcse.hilt_mvvm_compose_movie.data.model

import com.google.gson.annotations.SerializedName

data class SearchItem(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
)