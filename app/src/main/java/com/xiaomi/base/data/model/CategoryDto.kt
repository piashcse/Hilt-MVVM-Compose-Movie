package com.xiaomi.base.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for Category.
 */
data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String? = null,
    
    @SerializedName("image_path")
    val imagePath: String? = null
)