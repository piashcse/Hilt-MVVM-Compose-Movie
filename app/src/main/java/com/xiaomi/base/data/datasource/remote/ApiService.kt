package com.xiaomi.base.data.datasource.remote

import com.xiaomi.base.data.model.CategoryDto
import com.xiaomi.base.data.model.ItemDto
import com.xiaomi.base.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API service interface for remote data access.
 */
interface ApiService {

    @GET("category")
    suspend fun getCategoryList(
        @Query("page") page: Int
    ): Response<BaseResponse<CategoryDto>>

    @GET("category/{category_id}")
    suspend fun getCategoryDetail(
        @Path("category_id") categoryId: Int
    ): Response<CategoryDto>

    @GET("items")
    suspend fun getItemList(
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("items/popular")
    suspend fun getPopularItems(
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("items/top_rated")
    suspend fun getTopRatedItems(
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("items/{item_id}")
    suspend fun getItemDetail(
        @Path("item_id") itemId: Int
    ): Response<ItemDto>

    @GET("items/category/{category_id}")
    suspend fun getItemsByCategory(
        @Path("category_id") categoryId: Int,
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("items/search")
    suspend fun searchItems(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("trending")
    suspend fun getTrendingItems(
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("favorites")
    suspend fun getFavoriteItems(
        @Query("user_id") userId: String,
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("recommendations")
    suspend fun getRecommendedItems(
        @Query("user_id") userId: String,
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("items/recent")
    suspend fun getRecentItems(
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("items/featured")
    suspend fun getFeaturedItems(
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("categories")
    suspend fun getAllCategories(): Response<List<CategoryDto>>

    @GET("items/by_tags")
    suspend fun getItemsByTags(
        @Query("tags") tags: String,
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>

    @GET("items/status/{status}")
    suspend fun getItemsByStatus(
        @Path("status") status: String,
        @Query("page") page: Int
    ): Response<BaseResponse<ItemDto>>
}