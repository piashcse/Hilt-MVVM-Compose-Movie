package com.xiaomi.base.preview

import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.demos.ai.registerAIPreviews
import com.xiaomi.base.preview.demos.business.registerBusinessPreviews
import com.xiaomi.base.preview.demos.creative.registerCreativePreviews
import com.xiaomi.base.preview.demos.education.registerEducationPreviews
import com.xiaomi.base.preview.demos.entertainment.registerEntertainmentPreviews
import com.xiaomi.base.preview.demos.finance.registerFinancePreviews
import com.xiaomi.base.preview.demos.health.registerHealthTrackingPreviews
import com.xiaomi.base.preview.demos.lifestyle.registerLifestylePreviews
import com.xiaomi.base.preview.demos.social.registerSocialPreviews
import com.xiaomi.base.preview.demos.sports.registerSportsPreviews

/**
 * Central registration point for all preview demos.
 * This function should be called during app initialization to register
 * all available preview templates with the PreviewRegistry.
 */
fun registerAllPreviews() {
    // Health & Fitness
    registerHealthTrackingPreviews()
    registerSportsPreviews()
    
    // AI & Technology
    registerAIPreviews()
    
    // Creative & Design
    registerCreativePreviews()
    
    // Lifestyle & Personal
    registerLifestylePreviews()
    
    // Business & Professional
    registerBusinessPreviews()
    
    // Entertainment & Media
    registerEntertainmentPreviews()
    
    // Education & Learning
    registerEducationPreviews()
    
    // Social & Communication
    registerSocialPreviews()
    
    // Finance & Banking
    registerFinancePreviews()
}

/**
 * Get the total number of registered previews across all categories.
 */
fun getTotalPreviewCount(): Int {
    return PreviewRegistry.items.size
}

/**
 * Get preview count by category.
 */
fun getPreviewCountByCategory(): Map<PreviewCategory, Int> {
    return PreviewRegistry.items
        .groupBy { it.category }
        .mapValues { it.value.size }
}

/**
 * Get preview count by difficulty level.
 */
fun getPreviewCountByDifficulty(): Map<PreviewDifficulty, Int> {
    return PreviewRegistry.items
        .groupBy { it.difficulty }
        .mapValues { it.value.size }
}

/**
 * Get all available tags from registered previews.
 */
fun getAllAvailableTags(): List<String> {
    return PreviewRegistry.items
        .flatMap { it.tags }
        .distinct()
        .sorted()
}

/**
 * Search previews by tag.
 */
fun searchPreviewsByTag(tag: String): List<PreviewItem> {
    return PreviewRegistry.items
        .filter { preview -> preview.tags.any { it.contains(tag, ignoreCase = true) } }
}

/**
 * Get featured previews (can be used for highlighting certain demos).
 */
fun getFeaturedPreviews(): List<PreviewItem> {
    // Return a curated list of featured previews
    val featuredIds = listOf(
        "health_dashboard",
        "ai_chatbot",
        "creative_photo_editor",
        "social_feed",
        "finance_banking_dashboard",
        "sports_fitness_tracker",
        "business_dashboard",
        "entertainment_music_player"
    )
    
    return PreviewRegistry.items
        .filter { it.id in featuredIds }
}

/**
 * Get recently added previews (mock implementation).
 */
fun getRecentPreviews(limit: Int = 10): List<PreviewItem> {
    // In a real implementation, this could be based on creation date
    return PreviewRegistry.items.take(limit)
}

/**
 * Get popular previews based on usage (mock implementation).
 */
fun getPopularPreviews(limit: Int = 10): List<PreviewItem> {
    // In a real implementation, this could be based on usage analytics
    return PreviewRegistry.items
        .filter { it.difficulty == PreviewDifficulty.BEGINNER || it.difficulty == PreviewDifficulty.INTERMEDIATE }
        .take(limit)
}