package com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logo_path: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val origin_country: String
)