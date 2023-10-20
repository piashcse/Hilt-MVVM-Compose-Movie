package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.mappers

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.MovieEntity
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem

fun MovieItem.toBeEntity(): MovieEntity {
    return MovieEntity(
        id,
        adult,
        backdropPath,
        genreIds,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )
}

fun MovieEntity.toMovie(): MovieItem {
    return MovieItem(
        id,
        adult,
        backdropPath,
        genreIds,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount

    )
}