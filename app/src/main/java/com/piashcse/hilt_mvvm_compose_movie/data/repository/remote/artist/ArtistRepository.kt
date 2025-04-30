package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.artist

import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistMovies
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    suspend fun artistAllMovies(movieId: Int): Flow<DataState<ArtistMovies>>
    suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>>
}