package com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail
import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)