package com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso_3166_1: String,
    @SerializedName("name")
    val name: String
)