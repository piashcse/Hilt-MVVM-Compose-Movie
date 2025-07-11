package com.xiaomi.base.data.repository

import com.xiaomi.base.data.datasource.local.dao.UserDataDao
import com.xiaomi.base.data.datasource.local.entity.toUserData
import com.xiaomi.base.data.datasource.local.entity.toUserDataEntity
import com.xiaomi.base.domain.model.UserData
import com.xiaomi.base.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of UserDataRepository.
 */
@Singleton
class UserDataRepositoryImpl @Inject constructor(
    private val userDataDao: UserDataDao
) : UserDataRepository {
    
    override fun getUserData(userId: String): Flow<List<UserData>> {
        return userDataDao.getUserData(userId).map { entities ->
            entities.map { it.toUserData() }
        }
    }
    
    override fun getUserDataByType(userId: String, dataType: String): Flow<List<UserData>> {
        return userDataDao.getUserDataByType(userId, dataType).map { entities ->
            entities.map { it.toUserData() }
        }
    }
    
    override fun getUserDataByTimeRange(
        userId: String, 
        startTime: Long, 
        endTime: Long, 
        dataType: String?
    ): Flow<List<UserData>> {
        return userDataDao.getUserDataByTimeRange(userId, startTime, endTime, dataType).map { entities ->
            entities.map { it.toUserData() }
        }
    }
    
    override fun getLatestUserData(userId: String, dataType: String): Flow<UserData?> {
        return userDataDao.getLatestUserData(userId, dataType).map { entity ->
            entity?.toUserData()
        }
    }
    
    override fun getUserDataPaginated(
        userId: String, 
        dataType: String?, 
        limit: Int, 
        offset: Int
    ): Flow<List<UserData>> {
        return userDataDao.getUserDataPaginated(userId, dataType, limit, offset).map { entities ->
            entities.map { it.toUserData() }
        }
    }
    
    override suspend fun saveUserData(userData: UserData): Long {
        return userDataDao.insertUserData(userData.toUserDataEntity())
    }
    
    override suspend fun updateUserData(userData: UserData) {
        userDataDao.updateUserData(userData.toUserDataEntity())
    }
    
    override suspend fun deleteUserData(dataId: Int) {
        userDataDao.deleteUserData(dataId)
    }
    
    override suspend fun deleteAllUserData(userId: String) {
        userDataDao.deleteAllUserData(userId)
    }
    
    override suspend fun getUserDataCount(userId: String, dataType: String?): Int {
        return userDataDao.getUserDataCount(userId, dataType)
    }
} 