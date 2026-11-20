package com.piashcse.hilt_mvvm_compose_movie.utils.network

import retrofit2.HttpException
import java.io.IOException

/** Maps technical exceptions to short, user-facing messages. */
fun Throwable.toUserMessage(): String = when (this) {
    is IOException -> "No internet connection"
    is HttpException -> "Server error (${code()})"
    else -> message ?: "Something went wrong"
}
