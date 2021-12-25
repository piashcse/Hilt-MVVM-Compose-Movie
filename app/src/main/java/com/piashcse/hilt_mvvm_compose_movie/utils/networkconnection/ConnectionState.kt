package com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}