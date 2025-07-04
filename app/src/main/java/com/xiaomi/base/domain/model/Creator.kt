package com.xiaomi.base.domain.model

import java.util.Date

/**
 * Domain model representing a creator (author, artist, director, etc.).
 */
data class Creator(
    val id: Int,
    val name: String,
    val profileImageUrl: String? = null,
    val biography: String? = null,
    val birthDate: Date? = null,
    val placeOfBirth: String? = null,
    val gender: String? = null,
    val knownFor: List<String> = emptyList(),
    val relatedItems: List<Item> = emptyList()
)