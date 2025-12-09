package com.piashcse.hilt_mvvm_compose_movie.utils.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Extension function to handle API calls with consistent loading, success, error pattern
 */
fun <T> safeApiCall(apiCall: suspend () -> T): Flow<DataState<T>> = flow {
    emit(DataState.Loading)
    try {
        val result = apiCall()
        emit(DataState.Success(result))
    } catch (e: Exception) {
        emit(DataState.Error(e))
    }
}