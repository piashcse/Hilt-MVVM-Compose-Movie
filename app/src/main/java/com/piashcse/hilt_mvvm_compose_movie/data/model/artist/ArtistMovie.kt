package com.piashcse.hilt_mvvm_compose_movie.data.model.artist


import com.google.gson.annotations.SerializedName

data class ArtistMovie(
@SerializedName("adult")
val adult: Boolean,
@SerializedName("backdrop_path")
val backdropPath: String,
@SerializedName("character")
val character: String,
@SerializedName("credit_id")
val creditId: String,
@SerializedName("genre_ids")
val genreIds: List<Int>,
@SerializedName("id")
val id: Int,
@SerializedName("order")
val order: Int,
@SerializedName("original_language")
val originalLanguage: String,
@SerializedName("original_title")
val originalTitle: String,
@SerializedName("overview")
val overview: String,
@SerializedName("popularity")
val popularity: Double,
@SerializedName("poster_path")
val posterPath: String,
@SerializedName("release_date")
val releaseDate: String,
@SerializedName("title")
val title: String,
@SerializedName("video")
val video: Boolean,
@SerializedName("vote_average")
val voteAverage: Double,
@SerializedName("vote_count")
val voteCount: Int,
@SerializedName("media_type")
val mediaType: String
)