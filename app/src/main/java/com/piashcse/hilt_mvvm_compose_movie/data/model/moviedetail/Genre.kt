package com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String
)