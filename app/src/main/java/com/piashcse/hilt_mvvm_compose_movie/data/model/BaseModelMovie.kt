package com.piashcse.hilt_mvvm_compose_movie.data.model


import com.google.gson.annotations.SerializedName

data class BaseModelMovie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)