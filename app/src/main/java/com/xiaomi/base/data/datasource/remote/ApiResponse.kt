package com.xiaomi.base.data.datasource.remote

import com.google.gson.annotations.SerializedName

/**
 * Generic API response wrapper class.
 *
 * @param T The type of data contained in the response.
 * @property status The status of the API response (success or error).
 * @property data The data returned by the API if the request was successful.
 * @property message Error message if the request failed.
 */
data class ApiResponse<T>(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("data")
    val data: T? = null,
    
    @SerializedName("message")
    val message: String? = null
) {
    companion object {
        const val STATUS_SUCCESS = "success"
        const val STATUS_ERROR = "error"
        
        /**
         * Create a success response.
         *
         * @param data The data to include in the response.
         * @return A success API response containing the data.
         */
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(STATUS_SUCCESS, data)
        }
        
        /**
         * Create an error response.
         *
         * @param message The error message.
         * @return An error API response with the specified message.
         */
        fun <T> error(message: String): ApiResponse<T> {
            return ApiResponse(STATUS_ERROR, message = message)
        }
    }
    
    /**
     * Check if the response was successful.
     *
     * @return True if the response status is success, false otherwise.
     */
    fun isSuccessful(): Boolean {
        return status == STATUS_SUCCESS && data != null
    }
}