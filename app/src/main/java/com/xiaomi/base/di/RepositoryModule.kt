package com.xiaomi.base.di

import com.xiaomi.base.data.datasource.local.dao.FavoriteCategoryDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteItemDao
import com.xiaomi.base.data.datasource.local.dao.UserDataDao
import com.xiaomi.base.data.datasource.remote.ApiService
import com.xiaomi.base.data.model.mapper.CategoryMapper
import com.xiaomi.base.data.model.mapper.ItemMapper
import com.xiaomi.base.data.repository.CategoryRepositoryImpl
import com.xiaomi.base.data.repository.ItemRepositoryImpl
import com.xiaomi.base.data.repository.UserDataRepositoryImpl
import com.xiaomi.base.data.repository.local.LocalCategoryRepository as LocalCategoryRepositoryImpl
import com.xiaomi.base.data.repository.local.LocalItemRepository as LocalItemRepositoryImpl
import com.xiaomi.base.domain.repository.CategoryRepository
import com.xiaomi.base.domain.repository.ItemRepository
import com.xiaomi.base.domain.repository.UserDataRepository
import com.xiaomi.base.domain.repository.local.LocalCategoryRepository
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
     * Provides a UserDataRepository implementation.
     *
     * @param userDataDao The DAO for user data operations.
     * @return UserDataRepository implementation.
     */
    @Provides
    @Singleton
    fun provideUserDataRepository(userDataDao: UserDataDao): UserDataRepository {
        return UserDataRepositoryImpl(userDataDao)
    }

}