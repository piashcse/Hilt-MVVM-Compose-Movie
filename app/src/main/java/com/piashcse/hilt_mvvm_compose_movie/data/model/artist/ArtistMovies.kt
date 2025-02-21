package com.piashcse.hilt_mvvm_compose_movie.data.model.artist


import com.google.gson.annotations.SerializedName

data class ArtistMovies(
    @SerializedName("cast")
    val cast: List<ArtistMovie>,
    @SerializedName("id")
    val id: Int
)