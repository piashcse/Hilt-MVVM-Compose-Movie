package com.xiaomi.base.di

import com.xiaomi.base.data.datasource.local.dao.FavoriteCategoryDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteCreatorDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteItemDao
import com.xiaomi.base.data.datasource.remote.ApiService
import com.xiaomi.base.data.model.mapper.CategoryMapper
import com.xiaomi.base.data.model.mapper.CreatorMapper
import com.xiaomi.base.data.model.mapper.ItemMapper
import com.xiaomi.base.data.repository.CategoryRepositoryImpl
import com.xiaomi.base.data.repository.CreatorRepositoryImpl
import com.xiaomi.base.data.repository.ItemRepositoryImpl
import com.xiaomi.base.data.repository.local.LocalCategoryRepository as LocalCategoryRepositoryImpl
import com.xiaomi.base.data.repository.local.LocalCreatorRepository as LocalCreatorRepositoryImpl
import com.xiaomi.base.data.repository.local.LocalItemRepository as LocalItemRepositoryImpl
import com.xiaomi.base.domain.repository.CategoryRepository
import com.xiaomi.base.domain.repository.CreatorRepository
import com.xiaomi.base.domain.repository.ItemRepository
import com.xiaomi.base.domain.repository.local.LocalCategoryRepository
import com.xiaomi.base.domain.repository.local.LocalCreatorRepository
import com.xiaomi.base.domain.repository.local.LocalItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides repository-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    /**
     * Provides an ItemMapper instance.
     *
     * @return ItemMapper instance.
     */
    @Provides
    @Singleton
    fun provideItemMapper(): ItemMapper {
        return ItemMapper()
    }
    
    /**
     * Provides a CategoryMapper instance.
     *
     * @return CategoryMapper instance.
     */
    @Provides
    @Singleton
    fun provideCategoryMapper(): CategoryMapper {
        return CategoryMapper()
    }
    
    /**
     * Provides a CreatorMapper instance.
     *
     * @return CreatorMapper instance.
     */
    @Provides
    @Singleton
    fun provideCreatorMapper(): CreatorMapper {
        return CreatorMapper()
    }
    
    /**
     * Provides an ItemRepository implementation.
     *
     * @param apiService The API service for remote data operations.
     * @return ItemRepository implementation.
     */
    @Provides
    @Singleton
    fun provideItemRepository(
        apiService: ApiService
    ): ItemRepository {
        return ItemRepositoryImpl(apiService)
    }
    
    /**
     * Provides a CategoryRepository implementation.
     *
     * @param apiService The API service for remote data operations.
     * @return CategoryRepository implementation.
     */
    @Provides
    @Singleton
    fun provideCategoryRepository(
        apiService: ApiService
    ): CategoryRepository {
        return CategoryRepositoryImpl(apiService)
    }
    
    /**
     * Provides a CreatorRepository implementation.
     *
     * @param apiService The API service for remote data operations.
     * @return CreatorRepository implementation.
     */
    @Provides
    @Singleton
    fun provideCreatorRepository(
        apiService: ApiService
    ): CreatorRepository {
        return CreatorRepositoryImpl(apiService)
    }
    
    /**
     * Provides a LocalItemRepository instance.
     *
     * @param favoriteItemDao The DAO for favorite items operations.
     * @return LocalItemRepository instance.
     */
    @Provides
    @Singleton
    fun provideLocalItemRepository(favoriteItemDao: FavoriteItemDao): LocalItemRepository {
        return LocalItemRepositoryImpl(favoriteItemDao)
    }
    
    /**
     * Provides a LocalCategoryRepository instance.
     *
     * @param favoriteCategoryDao The DAO for favorite categories operations.
     * @return LocalCategoryRepository instance.
     */
    @Provides
    @Singleton
    fun provideLocalCategoryRepository(favoriteCategoryDao: FavoriteCategoryDao): LocalCategoryRepository {
        return LocalCategoryRepositoryImpl(favoriteCategoryDao)
    }
    
    /**
     * Provides a LocalCreatorRepository instance.
     *
     * @param favoriteCreatorDao The DAO for favorite creators operations.
     * @return LocalCreatorRepository instance.
     */
    @Provides
    @Singleton
    fun provideLocalCreatorRepository(favoriteCreatorDao: FavoriteCreatorDao): LocalCreatorRepository {
        return LocalCreatorRepositoryImpl(favoriteCreatorDao)
    }
}