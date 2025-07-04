package com.xiaomi.base.preview.catalog

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Central registry for managing all preview items
 * Provides search, filtering, and discovery functionality
 */
object PreviewRegistry {
    private val _items = mutableStateListOf<PreviewItem>()
    val items: SnapshotStateList<PreviewItem> = _items
    
    private val categoryIndex = mutableMapOf<PreviewCategory, MutableList<PreviewItem>>()
    private val tagIndex = mutableMapOf<String, MutableList<PreviewItem>>()
    private val difficultyIndex = mutableMapOf<PreviewDifficulty, MutableList<PreviewItem>>()
    
    init {
        registerDefaultPreviews()
    }
    
    /**
     * Register a new preview item
     */
    fun registerPreview(item: PreviewItem) {
        if (_items.any { it.id == item.id }) {
            throw IllegalArgumentException("Preview with id '${item.id}' already exists")
        }
        
        _items.add(item)
        
        // Update category index
        categoryIndex.getOrPut(item.category) { mutableListOf() }.add(item)
        
        // Update tag index
        item.tags.forEach { tag ->
            tagIndex.getOrPut(tag) { mutableListOf() }.add(item)
        }
        
        // Update difficulty index
        difficultyIndex.getOrPut(item.difficulty) { mutableListOf() }.add(item)
    }
    
    /**
     * Unregister a preview item
     */
    fun unregisterPreview(id: String) {
        val item = _items.find { it.id == id } ?: return
        _items.remove(item)
        
        // Remove from category index
        categoryIndex[item.category]?.remove(item)
        
        // Remove from tag index
        item.tags.forEach { tag ->
            tagIndex[tag]?.remove(item)
        }
        
        // Remove from difficulty index
        difficultyIndex[item.difficulty]?.remove(item)
    }
    
    /**
     * Find preview items with filtering
     */
    fun findPreviews(
        category: PreviewCategory? = null,
        difficulty: PreviewDifficulty? = null,
        searchQuery: String? = null,
        tags: List<String> = emptyList()
    ): List<PreviewItem> {
        var candidates = _items.toList()
        
        // Filter by category
        category?.let { cat ->
            candidates = candidates.filter { it.category == cat }
        }
        
        // Filter by difficulty
        difficulty?.let { diff ->
            candidates = candidates.filter { it.difficulty == diff }
        }
        
        // Filter by search query
        searchQuery?.takeIf { it.isNotBlank() }?.let { query ->
            candidates = candidates.filter {
                it.title.contains(query, true) ||
                it.description.contains(query, true) ||
                it.tags.any { tag -> tag.contains(query, true) }
            }
        }
        
        // Filter by tags
        if (tags.isNotEmpty()) {
            candidates = candidates.filter { item ->
                tags.any { tag -> item.tags.contains(tag) }
            }
        }
        
        return candidates.sortedBy { it.title }
    }
    
    /**
     * Get preview item by ID
     */
    fun getPreview(id: String): PreviewItem? = _items.find { it.id == id }
    
    /**
     * Get all categories with item counts
     */
    fun getCategoriesWithCounts(): List<Pair<PreviewCategory, Int>> {
        return PreviewCategory.values().map { category ->
            category to (categoryIndex[category]?.size ?: 0)
        }.filter { it.second > 0 }
    }
    
    /**
     * Get all difficulties with item counts
     */
    fun getDifficultiesWithCounts(): List<Pair<PreviewDifficulty, Int>> {
        return PreviewDifficulty.values().map { difficulty ->
            difficulty to (difficultyIndex[difficulty]?.size ?: 0)
        }.filter { it.second > 0 }
    }
    
    /**
     * Get all tags with item counts
     */
    fun getTagsWithCounts(): List<Pair<String, Int>> {
        return tagIndex.entries.map { (tag, items) ->
            tag to items.size
        }.sortedByDescending { it.second }
    }
    
    /**
     * Get registry statistics
     */
    fun getStats(): PreviewRegistryStats {
        return PreviewRegistryStats(
            totalItems = _items.size,
            itemsByCategory = categoryIndex.mapValues { it.value.size },
            itemsByDifficulty = difficultyIndex.mapValues { it.value.size },
            totalTags = tagIndex.size,
            mostUsedTags = tagIndex.entries.sortedByDescending { it.value.size }
                .take(10).map { it.key to it.value.size }
        )
    }
    
    /**
     * Clear all registered previews
     */
    fun clear() {
        _items.clear()
        categoryIndex.clear()
        tagIndex.clear()
        difficultyIndex.clear()
    }
    
    /**
     * Register default preview items
     */
    private fun registerDefaultPreviews() {
        // This will be populated as we create more preview templates
        // For now, we'll add some basic examples
    }
}

/**
 * Statistics about the preview registry
 */
data class PreviewRegistryStats(
    val totalItems: Int,
    val itemsByCategory: Map<PreviewCategory, Int>,
    val itemsByDifficulty: Map<PreviewDifficulty, Int>,
    val totalTags: Int,
    val mostUsedTags: List<Pair<String, Int>>
)