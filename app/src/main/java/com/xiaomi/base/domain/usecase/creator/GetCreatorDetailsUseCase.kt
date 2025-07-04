package com.xiaomi.base.domain.usecase.creator

import com.xiaomi.base.domain.model.Creator
import com.xiaomi.base.domain.repository.CreatorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving details of a specific creator.
 *
 * @property creatorRepository The repository for creator operations.
 */
class GetCreatorDetailsUseCase @Inject constructor(
    private val creatorRepository: CreatorRepository
) {
    /**
     * Execute the use case to get details of a specific creator.
     *
     * @param creatorId The ID of the creator to retrieve.
     * @return A Flow emitting the creator details.
     */
    suspend operator fun invoke(creatorId: Int): Flow<Creator> {
        return creatorRepository.getCreatorDetails(creatorId)
    }
}