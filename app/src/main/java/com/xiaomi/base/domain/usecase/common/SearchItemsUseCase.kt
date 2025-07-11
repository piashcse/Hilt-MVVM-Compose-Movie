package com.xiaomi.base.domain.usecase.common

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.model.ItemStatus
import com.xiaomi.base.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Universal use case for searching and filtering items.
 * Supports flexible search criteria that can be adapted for any app domain.
 */
class SearchItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    /**
     * Search items with query and filters
     * Note: Using popular items as fallback until search is implemented
     */
    suspend operator fun invoke(
        query: String,
        filters: SearchFilters = SearchFilters(),
        sortBy: SortOption = SortOption.RELEVANCE
    ): Flow<PagingData<Item>> {
        // TODO: Implement actual search when API supports it
        return itemRepository.getPopularItems()
    }
    
    /**
     * Get items by category
     */
    suspend fun getByCategory(
        categoryId: Int,
        sortBy: SortOption = SortOption.NEWEST
    ): Flow<PagingData<Item>> {
        return itemRepository.getItemsByCategory(categoryId)
    }
    
    /**
     * Get items by status
     * Note: Using popular items as fallback until status filtering is implemented
     */
    suspend fun getByStatus(
        status: ItemStatus,
        sortBy: SortOption = SortOption.NEWEST
    ): Flow<PagingData<Item>> {
        // TODO: Implement status filtering when repository supports it
        return itemRepository.getPopularItems()
    }
    
    /**
     * Get trending items (based on score, views, recent activity)
     */
    suspend fun getTrending(
        timeFrame: TimeFrame = TimeFrame.WEEK,
        limit: Int = 20
    ): Flow<PagingData<Item>> {
        return itemRepository.getTrendingItems()
    }
    
    /**
     * Get featured/highlighted items
     * Note: Using popular items as fallback
     */
    suspend fun getFeatured(): Flow<PagingData<Item>> {
        return itemRepository.getPopularItems()
    }
    
    /**
     * Get recommended items for user
     * Note: Using top rated items as fallback
     */
    suspend fun getRecommended(
        userId: String,
        limit: Int = 10
    ): Flow<PagingData<Item>> {
        return itemRepository.getTopRatedItems()
    }
    
    /**
     * Get items by tags
     * Note: Using popular items as fallback
     */
    suspend fun getByTags(
        tags: List<String>,
        matchAll: Boolean = false
    ): Flow<PagingData<Item>> {
        return itemRepository.getPopularItems()
    }
}

/**
 * Flexible search filters that can be customized for different domains
 */
data class SearchFilters(
    val categories: List<Int> = emptyList(),
    val tags: List<String> = emptyList(),
    val status: List<ItemStatus> = emptyList(),
    val minScore: Float? = null,
    val maxScore: Float? = null,
    val dateRange: DateRange? = null,
    val userId: String? = null, // For user-specific content
    val metadata: Map<String, Any> = emptyMap() // Flexible key-value filters
)

/**
 * Universal sorting options
 */
enum class SortOption {
    RELEVANCE,      // Search relevance
    NEWEST,         // Most recent first
    OLDEST,         // Oldest first
    HIGHEST_SCORE,  // Highest rated/scored first
    LOWEST_SCORE,   // Lowest rated/scored first
    ALPHABETICAL,   // A-Z
    MOST_POPULAR,   // Most views/interactions
    TRENDING,       // Trending/hot content
    RANDOM          // Random order
}

/**
 * Time frame for trending analysis
 */
enum class TimeFrame {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    ALL_TIME
}

/**
 * Date range filter
 */
data class DateRange(
    val startDate: Long,
    val endDate: Long
) 