package com.xiaomi.base.data.model

/**
 * Base response wrapper for API calls
 * 
 * @param T The type of data returned in the response
 */
data class BaseResponse<T>(
    val data: List<T>? = null,
    val page: Int? = null,
    val totalPages: Int? = null,
    val totalResults: Int? = null,
    val success: Boolean = true,
    val message: String? = null,
    val errorCode: String? = null
) 