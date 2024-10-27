package com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("english_name")
    val english_name: String,
    @SerializedName("iso_639_1")
    val iso_639_1: String,
    @SerializedName("name")
    val name: String
)