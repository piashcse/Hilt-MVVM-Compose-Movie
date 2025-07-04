package com.xiaomi.base.preview.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

/**
 * Data class representing a preview item in the catalog
 */
data class PreviewItem(
    val id: String,
    val title: String,
    val description: String,
    val category: PreviewCategory,
    val icon: ImageVector,
    val tags: List<String> = emptyList(),
    val difficulty: PreviewDifficulty = PreviewDifficulty.BEGINNER,
    val estimatedTime: String = "5 min",
    val content: @Composable () -> Unit
)

/**
 * Categories for organizing preview items
 */
enum class PreviewCategory(
    val displayName: String,
    val icon: ImageVector,
    val color: Long = 0xFF6200EE
) {
    HEALTH(
        displayName = "Health & Fitness",
        icon = Icons.Default.FavoriteBorder,
        color = 0xFF4CAF50
    ),
    AI(
        displayName = "AI & Technology",
        icon = Icons.Default.Psychology,
        color = 0xFF2196F3
    ),
    CREATIVE(
        displayName = "Creative & Media",
        icon = Icons.Default.Palette,
        color = 0xFF9C27B0
    ),
    LIFESTYLE(
        displayName = "Lifestyle",
        icon = Icons.Default.Home,
        color = 0xFFFF9800
    ),
    ECOMMERCE(
        displayName = "E-commerce",
        icon = Icons.Default.ShoppingCart,
        color = 0xFF795548
    ),
    SOCIAL(
        displayName = "Social Media",
        icon = Icons.Default.People,
        color = 0xFFE91E63
    ),
    FINANCE(
        displayName = "Finance",
        icon = Icons.Default.AccountBalance,
        color = 0xFF009688
    ),
    COMPONENTS(
        displayName = "UI Components",
        icon = Icons.Default.Widgets,
        color = 0xFF607D8B
    ),
    SPORTS(
        displayName = "Sports",
        icon = Icons.Default.SportsBaseball,
        color = 0xFFFF5722
    ),
    ENTERTAINMENT(
        displayName = "Entertainment",
        icon = Icons.Default.Movie,
        color = 0xFFE91E63
    ),
    BUSINESS(
        displayName = "Business",
        icon = Icons.Default.Business,
        color = 0xFF3F51B5
    ),
    EDUCATION(
        displayName = "Education",
        icon = Icons.Default.School,
        color = 0xFF8BC34A
    )
}

/**
 * Difficulty levels for preview items
 */
enum class PreviewDifficulty(
    val displayName: String,
    val color: Long
) {
    BEGINNER(
        displayName = "Beginner",
        color = 0xFF4CAF50
    ),
    INTERMEDIATE(
        displayName = "Intermediate",
        color = 0xFFFF9800
    ),
    ADVANCED(
        displayName = "Advanced",
        color = 0xFFF44336
    )
}

/**
 * Filter options for the preview catalog
 */
data class PreviewFilter(
    val categories: Set<PreviewCategory> = emptySet(),
    val difficulties: Set<PreviewDifficulty> = emptySet(),
    val searchQuery: String = "",
    val tags: Set<String> = emptySet()
) {
    fun matches(item: PreviewItem): Boolean {
        val categoryMatch = categories.isEmpty() || item.category in categories
        val difficultyMatch = difficulties.isEmpty() || item.difficulty in difficulties
        val searchMatch = searchQuery.isBlank() || 
            item.title.contains(searchQuery, ignoreCase = true) ||
            item.description.contains(searchQuery, ignoreCase = true)
        val tagMatch = tags.isEmpty() || item.tags.any { it in tags }
        
        return categoryMatch && difficultyMatch && searchMatch && tagMatch
    }
}