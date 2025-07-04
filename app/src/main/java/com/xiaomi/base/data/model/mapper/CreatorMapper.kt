package com.xiaomi.base.data.model.mapper

import com.xiaomi.base.data.model.CreatorDto
import com.xiaomi.base.domain.model.Creator
import com.xiaomi.base.domain.model.Item
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Mapper class for converting between Creator domain model and CreatorDto data model.
 */
class CreatorMapper {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val itemMapper = ItemMapper()
    
    /**
     * Convert from data model to domain model.
     *
     * @param dto The data model to convert.
     * @return The domain model.
     */
    fun mapToDomain(dto: CreatorDto): Creator {
        val birthDate: Date? = dto.birthday?.let {
            try {
                dateFormat.parse(it)
            } catch (e: Exception) {
                null
            }
        }
        
        val gender = when (dto.gender) {
            1 -> "Female"
            2 -> "Male"
            else -> null
        }
        
        val knownFor = dto.knownForDepartment?.let { listOf(it) } ?: emptyList()
        
        val relatedItems = dto.knownFor?.let { itemDtos ->
            itemDtos.map { itemMapper.mapToDomain(it) }
        } ?: emptyList()
        
        return Creator(
            id = dto.id,
            name = dto.name,
            profileImageUrl = dto.profilePath,
            biography = dto.biography,
            birthDate = birthDate,
            placeOfBirth = dto.placeOfBirth,
            gender = gender,
            knownFor = knownFor,
            relatedItems = relatedItems
        )
    }
    
    /**
     * Convert a list of data models to domain models.
     *
     * @param dtos The list of data models to convert.
     * @return The list of domain models.
     */
    fun mapToDomainList(dtos: List<CreatorDto>): List<Creator> {
        return dtos.map { mapToDomain(it) }
    }
}