package com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail
import com.google.gson.annotations.SerializedName
data class Network(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)