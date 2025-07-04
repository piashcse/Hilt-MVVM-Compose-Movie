package com.xiaomi.base.data.repository.local

import com.xiaomi.base.data.datasource.local.dao.FavoriteCreatorDao
import com.xiaomi.base.data.datasource.local.entity.FavoriteCreatorEntity
import com.xiaomi.base.domain.model.Creator
import com.xiaomi.base.domain.repository.local.LocalCreatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

/**
 * Repository implementation for handling local creator data operations.
 */
class LocalCreatorRepository @Inject constructor(
    private val favoriteCreatorDao: FavoriteCreatorDao
) : LocalCreatorRepository {
    
    /**
     * Get all favorite creators as a Flow.
     *
     * @return a Flow of list of all favorite creators as domain models
     */
    override fun getAllFavoriteCreators(): Flow<List<Creator>> {
        return favoriteCreatorDao.getAllFavoriteCreators().map { entities ->
            entities.map { entity ->
                Creator(
                    id = entity.creatorId,
                    name = entity.name,
                    profileImageUrl = entity.profilePath,
                    biography = entity.biography,
                    knownFor = listOfNotNull(entity.knownForDepartment)
                )
            }
        }
    }
    
    /**
     * Check if a creator is marked as favorite.
     *
     * @param creatorId the ID of the creator to check
     * @return Flow of boolean indicating if the creator is favorite
     */
    override fun isCreatorFavorite(creatorId: Int): Flow<Boolean> {
        return favoriteCreatorDao.isFavoriteFlow(creatorId)
    }
    
    /**
     * Get a favorite creator by ID.
     *
     * @param creatorId The ID of the creator to retrieve.
     * @return A Flow containing the favorite creator, or null if not found.
     */
    override fun getFavoriteCreatorById(creatorId: Int): Flow<Creator?> {
        return favoriteCreatorDao.getFavoriteById(creatorId).map { entity ->
            entity?.let {
                Creator(
                    id = it.creatorId,
                    name = it.name,
                    profileImageUrl = it.profilePath,
                    biography = it.biography,
                    knownFor = listOfNotNull(it.knownForDepartment)
                )
            }
        }
    }
    
    /**
     * Helper method to check if a creator is marked as favorite.
     *
     * @param creatorId the ID of the creator to check
     * @return true if the creator is favorite, false otherwise
     */
    private suspend fun isFavorite(creatorId: Int): Boolean {
        return favoriteCreatorDao.isFavorite(creatorId)
    }
    
    /**
     * Add a creator to favorites.
     *
     * @param creator the creator to add to favorites
     */
    override suspend fun addToFavorites(creator: Creator) {
        val favoriteEntity = FavoriteCreatorEntity(
            creatorId = creator.id,
            name = creator.name,
            profilePath = creator.profileImageUrl,
            biography = creator.biography,
            knownForDepartment = creator.knownFor.firstOrNull()
        )
        favoriteCreatorDao.insert(favoriteEntity)
    }
    
    /**
     * Remove a creator from favorites.
     *
     * @param creatorId the ID of the creator to remove from favorites
     */
    override suspend fun removeFromFavorites(creatorId: Int) {
        favoriteCreatorDao.deleteFavoriteById(creatorId)
    }
    
    /**
     * Toggle the favorite status of a creator.
     * If the creator is already a favorite, it will be removed.
     * If the creator is not a favorite, it will be added.
     *
     * @param creator the creator to toggle
     * @return true if the creator is now a favorite, false otherwise
     */
    override suspend fun toggleFavorite(creator: Creator): Boolean {
        return if (isFavorite(creator.id)) {
            removeFromFavorites(creator.id)
            false
        } else {
            addToFavorites(creator)
            true
        }
    }
    
    /**
     * Clear all favorite creators.
     */
    override suspend fun clearAllFavorites() {
        favoriteCreatorDao.deleteAllFavorites()
    }
}