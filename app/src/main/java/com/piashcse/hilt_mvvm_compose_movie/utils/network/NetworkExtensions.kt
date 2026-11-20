package com.piashcse.hilt_mvvm_compose_movie.utils.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Wraps a single API call in [DataState] so callers don't need their own
 * try/catch. Coroutine cancellation is rethrown instead of being reported as
 * a network error.
 */
fun <T> safeApiCall(apiCall: suspend () -> T): Flow<DataState<T>> = flow {
    emit(DataState.Loading)
    try {
        emit(DataState.Success(apiCall()))
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: Exception) {
        emit(DataState.Error(exception))
    }
}
