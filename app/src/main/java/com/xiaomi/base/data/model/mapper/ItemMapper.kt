package com.xiaomi.base.data.model.mapper

import com.xiaomi.base.data.model.CategoryDto
import com.xiaomi.base.data.model.ItemDto
import com.xiaomi.base.domain.model.Category
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.model.ItemStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Mapper class for converting between Item domain model and ItemDto data model.
 */
class ItemMapper {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val categoryMapper = CategoryMapper()
    
    /**
     * Convert from data model to domain model.
     *
     * @param dto The data model to convert.
     * @return The domain model.
     */
    fun mapToDomain(dto: ItemDto): Item {
        val createdDate: Date? = dto.releaseDate?.let {
            try {
                dateFormat.parse(it)
            } catch (e: Exception) {
                null
            }
        }
        
        val categories = when {
            dto.genres != null -> dto.genres.map { categoryMapper.mapToDomain(it) }
            dto.genreIds != null -> dto.genreIds.map { Category(it, "") }
            else -> emptyList()
        }
        
        // Build metadata from additional DTO fields
        val metadata = mutableMapOf<String, Any>()
        dto.originalLanguage?.let { metadata["language"] = it }
        dto.runtime?.let { metadata["duration"] = it }
        
        return Item(
            id = dto.id,
            title = dto.title,
            description = dto.overview,
            imageUrl = dto.posterPath,
            thumbnailUrl = dto.backdropPath,
            score = dto.voteAverage ?: 0f,
            createdDate = createdDate,
            lastModified = Date(), // Current time as last modified
            status = ItemStatus.ACTIVE, // Default status
            metadata = metadata,
            categories = categories,
            isFavorite = false, // Default to false, can be updated later
            tags = emptyList() // Can be populated from other sources
        )
    }
    
    /**
     * Convert from domain model to data model.
     *
     * @param domain The domain model to convert.
     * @return The data model.
     */
    fun mapToData(domain: Item): ItemDto {
        val releaseDateStr = domain.createdDate?.let {
            try {
                dateFormat.format(it)
            } catch (e: Exception) {
                null
            }
        }
        
        val genreIds = domain.categories.map { it.id }
        
        return ItemDto(
            id = domain.id,
            title = domain.title,
            overview = domain.description,
            posterPath = domain.imageUrl,
            backdropPath = domain.thumbnailUrl,
            voteAverage = domain.score,
            releaseDate = releaseDateStr,
            originalLanguage = domain.metadata["language"] as? String,
            runtime = domain.metadata["duration"] as? Int,
            genreIds = genreIds
        )
    }
    
    /**
     * Convert a list of data models to domain models.
     *
     * @param dtos The list of data models to convert.
     * @return The list of domain models.
     */
    fun mapToDomainList(dtos: List<ItemDto>): List<Item> {
        return dtos.map { mapToDomain(it) }
    }
}