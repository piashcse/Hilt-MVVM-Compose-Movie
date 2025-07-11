package com.xiaomi.base.domain.model

import java.util.Date

/**
 * Universal domain model representing any type of item in the application.
 * Can be adapted for various app domains (e.g., content, products, data, etc.)
 */
data class Item(
    val id: Int,
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val thumbnailUrl: String? = null, // Renamed from backdropUrl (more universal)
    val score: Float = 0f, // Renamed from rating (more universal)
    val createdDate: Date? = null, // Renamed from releaseDate (more universal)
    val lastModified: Date? = null, // New field for tracking updates
    val status: ItemStatus = ItemStatus.ACTIVE, // New field for item lifecycle
    val metadata: Map<String, Any> = emptyMap(), // New flexible field for domain-specific data
    val categories: List<Category> = emptyList(),
    val isFavorite: Boolean = false,
    val tags: List<String> = emptyList() // New field for flexible tagging
)

/**
 * Universal item status enum for tracking item lifecycle
 */
enum class ItemStatus {
    ACTIVE,    // Item is live and visible
    INACTIVE,  // Item is temporarily hidden
    DRAFT,     // Item is being worked on
    ARCHIVED,  // Item is stored but not active
    DELETED    // Item is marked for deletion
}