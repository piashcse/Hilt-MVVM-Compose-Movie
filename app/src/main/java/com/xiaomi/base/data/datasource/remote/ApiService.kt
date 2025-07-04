package com.xiaomi.base.data.datasource.remote

import com.xiaomi.base.data.model.CategoryDto
import com.xiaomi.base.data.model.CreatorDto
import com.xiaomi.base.data.model.ItemDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API service interface for remote data operations.
 */
interface ApiService {
    /**
     * Get popular items.
     *
     * @param page The page number to retrieve.
     * @return A response containing a list of popular items.
     */
    @GET("popular")
    suspend fun getPopularItems(@Query("page") page: Int): ApiResponse<List<ItemDto>>

    /**
     * Get top rated items.
     *
     * @param page The page number to retrieve.
     * @return A response containing a list of top rated items.
     */
    @GET("top_rated")
    suspend fun getTopRatedItems(@Query("page") page: Int): ApiResponse<List<ItemDto>>

    /**
     * Get upcoming items.
     *
     * @param page The page number to retrieve.
     * @return A response containing a list of upcoming items.
     */
    @GET("upcoming")
    suspend fun getUpcomingItems(@Query("page") page: Int): ApiResponse<List<ItemDto>>

    /**
     * Get trending items.
     *
     * @param page The page number to retrieve.
     * @return A response containing a list of trending items.
     */
    @GET("trending")
    suspend fun getTrendingItems(@Query("page") page: Int): ApiResponse<List<ItemDto>>

    /**
     * Get item details by ID.
     *
     * @param itemId The ID of the item to retrieve.
     * @return A response containing the item details.
     */
    @GET("item/{item_id}")
    suspend fun getItemDetails(@Path("item_id") itemId: Int): ApiResponse<ItemDto>

    /**
     * Get similar items for a specific item.
     *
     * @param itemId The ID of the item to find similar items for.
     * @param page The page number to retrieve.
     * @return A response containing a list of similar items.
     */
    @GET("item/{item_id}/similar")
    suspend fun getSimilarItems(
        @Path("item_id") itemId: Int,
        @Query("page") page: Int
    ): ApiResponse<List<ItemDto>>

    /**
     * Get all categories.
     *
     * @param page The page number to retrieve.
     * @return A response containing a list of categories.
     */
    @GET("categories")
    suspend fun getAllCategories(@Query("page") page: Int): ApiResponse<List<CategoryDto>>

    /**
     * Get category details by ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A response containing the category details.
     */
    @GET("category/{category_id}")
    suspend fun getCategoryDetails(@Path("category_id") categoryId: Int): ApiResponse<CategoryDto>

    /**
     * Get items by category.
     *
     * @param categoryId The category ID to filter items by.
     * @param page The page number to retrieve.
     * @return A response containing a list of items in the specified category.
     */
    @GET("category/{category_id}/items")
    suspend fun getItemsByCategory(
        @Path("category_id") categoryId: Int,
        @Query("page") page: Int
    ): ApiResponse<List<ItemDto>>

    /**
     * Get popular creators.
     *
     * @param page The page number to retrieve.
     * @return A response containing a list of popular creators.
     */
    @GET("creators/popular")
    suspend fun getPopularCreators(@Query("page") page: Int): ApiResponse<List<CreatorDto>>

    /**
     * Get trending creators.
     *
     * @param page The page number to retrieve.
     * @return A response containing a list of trending creators.
     */
    @GET("creators/trending")
    suspend fun getTrendingCreators(@Query("page") page: Int): ApiResponse<List<CreatorDto>>

    /**
     * Get creator details by ID.
     *
     * @param creatorId The ID of the creator to retrieve.
     * @return A response containing the creator details.
     */
    @GET("creator/{creator_id}")
    suspend fun getCreatorDetails(@Path("creator_id") creatorId: Int): ApiResponse<CreatorDto>

    /**
     * Get creators associated with a specific item.
     *
     * @param itemId The ID of the item to find creators for.
     * @param page The page number to retrieve.
     * @return A response containing a list of creators associated with the item.
     */
    @GET("item/{item_id}/creators")
    suspend fun getCreatorsByItem(
        @Path("item_id") itemId: Int,
        @Query("page") page: Int
    ): ApiResponse<List<CreatorDto>>
}