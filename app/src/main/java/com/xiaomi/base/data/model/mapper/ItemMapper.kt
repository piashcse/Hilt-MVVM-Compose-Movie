package com.xiaomi.base.data.model.mapper

import com.xiaomi.base.data.model.CategoryDto
import com.xiaomi.base.data.model.ItemDto
import com.xiaomi.base.domain.model.Category
import com.xiaomi.base.domain.model.Item
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
        val releaseDate: Date? = dto.releaseDate?.let {
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
        
        return Item(
            id = dto.id,
            title = dto.title,
            description = dto.overview,
            imageUrl = dto.posterPath,
            backdropUrl = dto.backdropPath,
            rating = dto.voteAverage ?: 0f,
            releaseDate = releaseDate,
            language = dto.originalLanguage,
            duration = dto.runtime,
            categories = categories
        )
    }
    
    /**
     * Convert from domain model to data model.
     *
     * @param domain The domain model to convert.
     * @return The data model.
     */
    fun mapToData(domain: Item): ItemDto {
        val releaseDateStr = domain.releaseDate?.let {
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
            backdropPath = domain.backdropUrl,
            voteAverage = domain.rating,
            releaseDate = releaseDateStr,
            originalLanguage = domain.language,
            runtime = domain.duration,
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