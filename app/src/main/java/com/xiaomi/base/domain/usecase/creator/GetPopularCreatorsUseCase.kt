package com.xiaomi.base.domain.usecase.creator

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Creator
import com.xiaomi.base.domain.repository.CreatorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a paginated list of popular creators.
 *
 * @property creatorRepository The repository for creator operations.
 */
class GetPopularCreatorsUseCase @Inject constructor(
    private val creatorRepository: CreatorRepository
) {
    /**
     * Execute the use case to get a paginated list of popular creators.
     *
     * @return A Flow emitting PagingData of popular creators.
     */
    operator fun invoke(): Flow<PagingData<Creator>> {
        return creatorRepository.getPopularCreators()
    }
}