package com.xiaomi.base.domain.model

import java.util.Date

/**
 * Domain model representing a generic item in the application.
 * This can be specialized for different types of content.
 */
data class Item(
    val id: Int,
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val backdropUrl: String? = null,
    val rating: Float = 0f,
    val releaseDate: Date? = null,
    val language: String? = null,
    val duration: Int? = null, // in minutes
    val categories: List<Category> = emptyList(),
    val isFavorite: Boolean = false
)