package com.xiaomi.base.di

import com.xiaomi.base.domain.repository.CategoryRepository
import com.xiaomi.base.domain.repository.CreatorRepository
import com.xiaomi.base.domain.repository.ItemRepository
import com.xiaomi.base.domain.usecase.category.GetCategoriesUseCase
import com.xiaomi.base.domain.usecase.category.GetCategoryDetailsUseCase
import com.xiaomi.base.domain.usecase.creator.GetCreatorDetailsUseCase
import com.xiaomi.base.domain.usecase.creator.GetPopularCreatorsUseCase
import com.xiaomi.base.domain.usecase.item.GetItemDetailsUseCase
import com.xiaomi.base.domain.usecase.item.GetPopularItemsUseCase
import com.xiaomi.base.domain.usecase.item.GetTopRatedItemsUseCase
import com.xiaomi.base.domain.usecase.item.ToggleFavoriteItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides use case dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    
    /**
     * Provides a GetPopularItemsUseCase instance.
     *
     * @param itemRepository The repository for item operations.
     * @return GetPopularItemsUseCase instance.
     */
    @Provides
    @Singleton
    fun provideGetPopularItemsUseCase(itemRepository: ItemRepository): GetPopularItemsUseCase {
        return GetPopularItemsUseCase(itemRepository)
    }
    
    /**
     * Provides a GetTopRatedItemsUseCase instance.
     *
     * @param itemRepository The repository for item operations.
     * @return GetTopRatedItemsUseCase instance.
     */
    @Provides
    @Singleton
    fun provideGetTopRatedItemsUseCase(itemRepository: ItemRepository): GetTopRatedItemsUseCase {
        return GetTopRatedItemsUseCase(itemRepository)
    }
    
    /**
     * Provides a GetItemDetailsUseCase instance.
     *
     * @param itemRepository The repository for item operations.
     * @return GetItemDetailsUseCase instance.
     */
    @Provides
    @Singleton
    fun provideGetItemDetailsUseCase(itemRepository: ItemRepository): GetItemDetailsUseCase {
        return GetItemDetailsUseCase(itemRepository)
    }
    
    /**
     * Provides a ToggleFavoriteItemUseCase instance.
     *
     * @param itemRepository The repository for item operations.
     * @return ToggleFavoriteItemUseCase instance.
     */
    @Provides
    @Singleton
    fun provideToggleFavoriteItemUseCase(itemRepository: ItemRepository): ToggleFavoriteItemUseCase {
        return ToggleFavoriteItemUseCase(itemRepository)
    }
    
    /**
     * Provides a GetCategoriesUseCase instance.
     *
     * @param categoryRepository The repository for category operations.
     * @return GetCategoriesUseCase instance.
     */
    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }
    
    /**
     * Provides a GetCategoryDetailsUseCase instance.
     *
     * @param categoryRepository The repository for category operations.
     * @return GetCategoryDetailsUseCase instance.
     */
    @Provides
    @Singleton
    fun provideGetCategoryDetailsUseCase(categoryRepository: CategoryRepository): GetCategoryDetailsUseCase {
        return GetCategoryDetailsUseCase(categoryRepository)
    }
    
    /**
     * Provides a GetPopularCreatorsUseCase instance.
     *
     * @param creatorRepository The repository for creator operations.
     * @return GetPopularCreatorsUseCase instance.
     */
    @Provides
    @Singleton
    fun provideGetPopularCreatorsUseCase(creatorRepository: CreatorRepository): GetPopularCreatorsUseCase {
        return GetPopularCreatorsUseCase(creatorRepository)
    }
    
    /**
     * Provides a GetCreatorDetailsUseCase instance.
     *
     * @param creatorRepository The repository for creator operations.
     * @return GetCreatorDetailsUseCase instance.
     */
    @Provides
    @Singleton
    fun provideGetCreatorDetailsUseCase(creatorRepository: CreatorRepository): GetCreatorDetailsUseCase {
        return GetCreatorDetailsUseCase(creatorRepository)
    }
}