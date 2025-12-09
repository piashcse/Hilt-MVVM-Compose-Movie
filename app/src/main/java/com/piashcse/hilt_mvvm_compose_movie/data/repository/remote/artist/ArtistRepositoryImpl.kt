package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.artist

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistMovies
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ArtistRepository {
    override suspend fun artistAllMovies(movieId: Int): Flow<DataState<ArtistMovies>> =
        safeApiCall { apiService.artistAllMovies(movieId) }
    override suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>> =
        safeApiCall { apiService.artistDetail(personId) }
}