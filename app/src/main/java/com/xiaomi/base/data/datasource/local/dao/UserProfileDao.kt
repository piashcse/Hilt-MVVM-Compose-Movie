package com.xiaomi.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.xiaomi.base.data.datasource.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the user profiles table.
 */
@Dao
interface UserProfileDao : BaseDao<UserProfileEntity> {
    
    /**
     * Get a user profile by its ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user profile entity or null if not found
     */
    @Query("SELECT * FROM user_profiles WHERE userId = :userId LIMIT 1")
    suspend fun getUserProfile(userId: String): UserProfileEntity?
    
    /**
     * Get a user profile by its ID as a Flow.
     *
     * @param userId the ID of the user to retrieve
     * @return Flow of the user profile entity or null if not found
     */
    @Query("SELECT * FROM user_profiles WHERE userId = :userId LIMIT 1")
    fun getUserProfileFlow(userId: String): Flow<UserProfileEntity?>
    
    /**
     * Get all user profiles.
     *
     * @return Flow of list of all user profiles
     */
    @Query("SELECT * FROM user_profiles ORDER BY lastActiveDate DESC")
    fun getAllUserProfiles(): Flow<List<UserProfileEntity>>
    
    /**
     * Update user's last active date.
     *
     * @param userId the ID of the user
     */
    @Query("UPDATE user_profiles SET lastActiveDate = datetime('now') WHERE userId = :userId")
    suspend fun updateLastActiveDate(userId: String)
    
    /**
     * Search user profiles by display name.
     *
     * @param query the search query
     * @return Flow of list of matching user profiles
     */
    @Query("SELECT * FROM user_profiles WHERE displayName LIKE '%' || :query || '%' ORDER BY displayName ASC")
    fun searchUserProfiles(query: String): Flow<List<UserProfileEntity>>
} 