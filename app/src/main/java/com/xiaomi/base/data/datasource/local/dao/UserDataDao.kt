package com.xiaomi.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.xiaomi.base.data.datasource.local.entity.UserDataEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the user data table.
 */
@Dao
interface UserDataDao : BaseDao<UserDataEntity> {
    
    /**
     * Get all user data for a specific user.
     *
     * @param userId the ID of the user
     * @return Flow of list of user data
     */
    @Query("SELECT * FROM user_data WHERE userId = :userId ORDER BY timestamp DESC")
    fun getUserData(userId: String): Flow<List<UserDataEntity>>
    
    /**
     * Get user data by type for a specific user.
     *
     * @param userId the ID of the user
     * @param dataType the type of data to retrieve
     * @return Flow of list of user data
     */
    @Query("SELECT * FROM user_data WHERE userId = :userId AND dataType = :dataType ORDER BY timestamp DESC")
    fun getUserDataByType(userId: String, dataType: String): Flow<List<UserDataEntity>>
    
    /**
     * Get a specific user data entry by ID.
     *
     * @param id the ID of the data entry
     * @return the user data entity or null if not found
     */
    @Query("SELECT * FROM user_data WHERE id = :id LIMIT 1")
    suspend fun getUserDataById(id: Int): UserDataEntity?
    
    /**
     * Get user data within a time range.
     *
     * @param userId the ID of the user
     * @param startTime the start time
     * @param endTime the end time
     * @return Flow of list of user data within the time range
     */
    @Query("SELECT * FROM user_data WHERE userId = :userId AND timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun getUserDataInTimeRange(userId: String, startTime: Long, endTime: Long): Flow<List<UserDataEntity>>
    
    /**
     * Delete user data older than specified timestamp.
     *
     * @param userId the ID of the user
     * @param timestamp the timestamp threshold
     */
    @Query("DELETE FROM user_data WHERE userId = :userId AND timestamp < :timestamp")
    suspend fun deleteOldUserData(userId: String, timestamp: Long)
    
    /**
     * Get user data within a time range with optional type filter.
     *
     * @param userId the ID of the user
     * @param startTime the start time in milliseconds
     * @param endTime the end time in milliseconds
     * @param dataType optional data type filter
     * @return Flow of list of user data within the time range
     */
    @Query("""
        SELECT * FROM user_data 
        WHERE userId = :userId 
        AND timestamp BETWEEN :startTime AND :endTime 
        AND (:dataType IS NULL OR dataType = :dataType) 
        ORDER BY timestamp DESC
    """)
    fun getUserDataByTimeRange(
        userId: String, 
        startTime: Long, 
        endTime: Long, 
        dataType: String?
    ): Flow<List<UserDataEntity>>
    
    /**
     * Get latest user data entry of a specific type.
     *
     * @param userId the ID of the user
     * @param dataType the type of data
     * @return Flow of the latest user data entity or null
     */
    @Query("""
        SELECT * FROM user_data 
        WHERE userId = :userId AND dataType = :dataType 
        ORDER BY timestamp DESC 
        LIMIT 1
    """)
    fun getLatestUserData(userId: String, dataType: String): Flow<UserDataEntity?>
    
    /**
     * Get user data with pagination support.
     *
     * @param userId the ID of the user
     * @param dataType optional data type filter
     * @param limit maximum number of results
     * @param offset number of results to skip
     * @return Flow of list of user data
     */
    @Query("""
        SELECT * FROM user_data 
        WHERE userId = :userId 
        AND (:dataType IS NULL OR dataType = :dataType) 
        ORDER BY timestamp DESC 
        LIMIT :limit OFFSET :offset
    """)
    fun getUserDataPaginated(
        userId: String, 
        dataType: String?, 
        limit: Int, 
        offset: Int
    ): Flow<List<UserDataEntity>>
    
    /**
     * Insert user data and return the row ID.
     *
     * @param userData the user data entity to insert
     * @return the row ID of the inserted data
     */
    suspend fun insertUserData(userData: UserDataEntity): Long {
        return insert(userData)
    }
    
    /**
     * Update user data.
     *
     * @param userData the user data entity to update
     */
    suspend fun updateUserData(userData: UserDataEntity) {
        update(userData)
    }
    
    /**
     * Delete user data by ID.
     *
     * @param dataId the ID of the data to delete
     */
    @Query("DELETE FROM user_data WHERE id = :dataId")
    suspend fun deleteUserData(dataId: Int)
    
    /**
     * Delete all user data for a specific user.
     *
     * @param userId the ID of the user
     */
    @Query("DELETE FROM user_data WHERE userId = :userId")
    suspend fun deleteAllUserData(userId: String)
    
    /**
     * Get user data count by type.
     *
     * @param userId the ID of the user
     * @param dataType optional data type filter
     * @return the count of user data entries
     */
    @Query("""
        SELECT COUNT(*) FROM user_data 
        WHERE userId = :userId 
        AND (:dataType IS NULL OR dataType = :dataType)
    """)
    suspend fun getUserDataCount(userId: String, dataType: String?): Int
} 