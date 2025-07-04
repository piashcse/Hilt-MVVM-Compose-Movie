package com.xiaomi.base.domain.model

/**
 * Domain model representing a category for items.
 */
data class Category(
    val id: Int,
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null
)