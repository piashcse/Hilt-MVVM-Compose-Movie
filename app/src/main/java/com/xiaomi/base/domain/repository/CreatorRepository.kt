package com.xiaomi.base.domain.repository

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Creator
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing creators.
 */
interface CreatorRepository {
    /**
     * Get popular creators as a paginated list.
     *
     * @return A Flow of PagingData containing popular creators.
     */
    fun getPopularCreators(): Flow<PagingData<Creator>>

    /**
     * Get trending creators as a paginated list.
     *
     * @return A Flow of PagingData containing trending creators.
     */
    fun getTrendingCreators(): Flow<PagingData<Creator>>

    /**
     * Get creator details by ID.
     *
     * @param creatorId The ID of the creator to retrieve.
     * @return A Flow containing the creator details.
     */
    suspend fun getCreatorDetails(creatorId: Int): Flow<Creator>

    /**
     * Get creators associated with a specific item.
     *
     * @param itemId The ID of the item to find creators for.
     * @return A Flow of PagingData containing creators associated with the item.
     */
    fun getCreatorsByItem(itemId: Int): Flow<PagingData<Creator>>
}