package com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail

import com.google.gson.annotations.SerializedName
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)