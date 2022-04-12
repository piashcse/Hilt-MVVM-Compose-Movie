package com.piashcse.hilt_mvvm_compose_movie.data.model.artist


import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)