package com.xiaomi.base.domain.usecase.common

import com.xiaomi.base.domain.model.UserData
import com.xiaomi.base.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

/**
 * Universal use case for retrieving user data.
 * Can be used for any type of user-generated content: metrics, preferences, content, etc.
 */
class GetUserDataUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    /**
     * Get all user data for a specific user
     */
    suspend operator fun invoke(userId: String): Flow<List<UserData>> {
        return userDataRepository.getUserData(userId)
    }
    
    /**
     * Get user data by type for a specific user
     */
    suspend fun getByType(
        userId: String,
        dataType: String
    ): Flow<List<UserData>> {
        return userDataRepository.getUserDataByType(userId, dataType)
    }
    
    /**
     * Get user data within a time range
     */
    suspend fun getByTimeRange(
        userId: String,
        startDate: Date,
        endDate: Date,
        dataType: String? = null
    ): Flow<List<UserData>> {
        return userDataRepository.getUserDataByTimeRange(userId, startDate.time, endDate.time, dataType)
    }
    
    /**
     * Get latest user data entry of a specific type
     */
    suspend fun getLatest(
        userId: String,
        dataType: String
    ): Flow<UserData?> {
        return userDataRepository.getLatestUserData(userId, dataType)
    }
    
    /**
     * Get user data with pagination support
     */
    suspend fun getPaginated(
        userId: String,
        dataType: String? = null,
        limit: Int = 20,
        offset: Int = 0
    ): Flow<List<UserData>> {
        return userDataRepository.getUserDataPaginated(userId, dataType, limit, offset)
    }
} 