package com.xiaomi.base.domain.repository

import com.xiaomi.base.domain.model.UserData
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for user data operations.
 */
interface UserDataRepository {
    
    /**
     * Get all user data for a specific user.
     */
    fun getUserData(userId: String): Flow<List<UserData>>
    
    /**
     * Get user data by type for a specific user.
     */
    fun getUserDataByType(userId: String, dataType: String): Flow<List<UserData>>
    
    /**
     * Get user data within a time range with optional type filter.
     */
    fun getUserDataByTimeRange(
        userId: String, 
        startTime: Long, 
        endTime: Long, 
        dataType: String? = null
    ): Flow<List<UserData>>
    
    /**
     * Get latest user data entry of a specific type.
     */
    fun getLatestUserData(userId: String, dataType: String): Flow<UserData?>
    
    /**
     * Get user data with pagination support.
     */
    fun getUserDataPaginated(
        userId: String, 
        dataType: String? = null, 
        limit: Int = 20, 
        offset: Int = 0
    ): Flow<List<UserData>>
    
    /**
     * Save user data.
     */
    suspend fun saveUserData(userData: UserData): Long
    
    /**
     * Update user data.
     */
    suspend fun updateUserData(userData: UserData)
    
    /**
     * Delete user data by ID.
     */
    suspend fun deleteUserData(dataId: Int)
    
    /**
     * Delete all user data for a specific user.
     */
    suspend fun deleteAllUserData(userId: String)
    
    /**
     * Get user data count by type.
     */
    suspend fun getUserDataCount(userId: String, dataType: String? = null): Int
} 