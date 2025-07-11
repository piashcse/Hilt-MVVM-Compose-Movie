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
    suspend fun getUserDataById(id: String): UserDataEntity?
    
    /**
     * Search user data by tags.
     *
     * @param userId the ID of the user
     * @param tag the tag to search for
     * @return Flow of list of matching user data
     */
    @Query("SELECT * FROM user_data WHERE userId = :userId AND tags LIKE '%' || :tag || '%' ORDER BY timestamp DESC")
    fun getUserDataByTag(userId: String, tag: String): Flow<List<UserDataEntity>>
    
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
} 