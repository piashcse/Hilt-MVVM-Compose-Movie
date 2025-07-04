package com.xiaomi.base.data.model.mapper

import com.xiaomi.base.data.model.CategoryDto
import com.xiaomi.base.domain.model.Category

/**
 * Mapper class for converting between Category domain model and CategoryDto data model.
 */
class CategoryMapper {
    /**
     * Convert from data model to domain model.
     *
     * @param dto The data model to convert.
     * @return The domain model.
     */
    fun mapToDomain(dto: CategoryDto): Category {
        return Category(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            imageUrl = dto.imagePath
        )
    }
    
    /**
     * Convert from domain model to data model.
     *
     * @param domain The domain model to convert.
     * @return The data model.
     */
    fun mapToData(domain: Category): CategoryDto {
        return CategoryDto(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            imagePath = domain.imageUrl
        )
    }
    
    /**
     * Convert a list of data models to domain models.
     *
     * @param dtos The list of data models to convert.
     * @return The list of domain models.
     */
    fun mapToDomainList(dtos: List<CategoryDto>): List<Category> {
        return dtos.map { mapToDomain(it) }
    }
}