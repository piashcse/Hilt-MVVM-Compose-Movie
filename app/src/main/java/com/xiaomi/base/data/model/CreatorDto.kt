package com.xiaomi.base.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for Creator.
 */
data class CreatorDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("profile_path")
    val profilePath: String?,
    
    @SerializedName("biography")
    val biography: String?,
    
    @SerializedName("birthday")
    val birthday: String?,
    
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    
    @SerializedName("gender")
    val gender: Int?,
    
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    
    @SerializedName("known_for")
    val knownFor: List<ItemDto>? = null
)