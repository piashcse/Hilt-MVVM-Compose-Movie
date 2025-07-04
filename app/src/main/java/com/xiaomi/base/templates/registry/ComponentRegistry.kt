package com.xiaomi.base.templates.registry

/**
 * Central registry để quản lý tất cả component templates
 * Cung cấp search, filtering và discovery functionality
 */
object ComponentRegistry {
    private val components = mutableMapOf<String, ComponentMetadata>()
    private val categoryIndex = mutableMapOf<ComponentCategory, MutableList<String>>()
    private val tagIndex = mutableMapOf<String, MutableList<String>>()
    private val complexityIndex = mutableMapOf<ComplexityLevel, MutableList<String>>()
    
    init {
        registerDefaultComponents()
    }
    
    /**
     * Register một component mới vào registry
     */
    fun registerComponent(metadata: ComponentMetadata) {
        components[metadata.id] = metadata
        
        // Update category index
        categoryIndex.getOrPut(metadata.category) { mutableListOf() }.add(metadata.id)
        
        // Update tag index
        metadata.tags.forEach { tag ->
            tagIndex.getOrPut(tag) { mutableListOf() }.add(metadata.id)
        }
        
        // Update complexity index
        complexityIndex.getOrPut(metadata.complexity) { mutableListOf() }.add(metadata.id)
    }
    
    /**
     * Unregister component khỏi registry
     */
    fun unregisterComponent(id: String) {
        val component = components.remove(id) ?: return
        
        // Remove from category index
        categoryIndex[component.category]?.remove(id)
        
        // Remove from tag index
        component.tags.forEach { tag ->
            tagIndex[tag]?.remove(id)
        }
        
        // Remove from complexity index
        complexityIndex[component.complexity]?.remove(id)
    }
    
    /**
     * Tìm components với các filter criteria
     */
    fun findComponents(
        category: ComponentCategory? = null,
        tags: List<String> = emptyList(),
        complexity: ComplexityLevel? = null,
        searchQuery: String? = null,
        projectContext: ProjectContext? = null
    ): List<ComponentMetadata> {
        var candidates = components.values.toList()
        
        // Filter by category
        category?.let { cat ->
            candidates = candidates.filter { it.category == cat }
        }
        
        // Filter by complexity
        complexity?.let { comp ->
            candidates = candidates.filter { it.complexity == comp }
        }
        
        // Filter by tags (AND logic - component must have all specified tags)
        if (tags.isNotEmpty()) {
            candidates = candidates.filter { component ->
                tags.all { tag -> component.tags.contains(tag) }
            }
        }
        
        // Filter by project compatibility
        projectContext?.let { context ->
            candidates = candidates.filter { it.isCompatibleWith(context) }
        }
        
        // Filter and rank by search query
        searchQuery?.let { query ->
            candidates = candidates
                .map { component -> component to component.calculateRelevanceScore(query) }
                .filter { it.second > 0 }
                .sortedByDescending { it.second }
                .map { it.first }
        }
        
        return if (searchQuery == null) {
            candidates.sortedBy { it.name }
        } else {
            candidates
        }
    }
    
    /**
     * Lấy component theo ID
     */
    fun getComponent(id: String): ComponentMetadata? = components[id]
    
    /**
     * Lấy tất cả categories có sẵn
     */
    fun getAllCategories(): List<ComponentCategory> = ComponentCategory.values().toList()
    
    /**
     * Lấy components theo category
     */
    fun getComponentsByCategory(category: ComponentCategory): List<ComponentMetadata> {
        return categoryIndex[category]?.mapNotNull { components[it] } ?: emptyList()
    }
    
    /**
     * Search components theo tags (OR logic)
     */
    fun searchByTags(tags: List<String>): List<ComponentMetadata> {
        val componentIds = tags.flatMap { tag ->
            tagIndex[tag] ?: emptyList()
        }.distinct()
        
        return componentIds.mapNotNull { components[it] }
    }
    
    /**
     * Lấy components theo complexity level
     */
    fun getComponentsByComplexity(complexity: ComplexityLevel): List<ComponentMetadata> {
        return complexityIndex[complexity]?.mapNotNull { components[it] } ?: emptyList()
    }
    
    /**
     * Lấy tất cả tags có sẵn
     */
    fun getAllTags(): List<String> {
        return tagIndex.keys.sorted()
    }
    
    /**
     * Lấy statistics về registry
     */
    fun getRegistryStats(): RegistryStats {
        return RegistryStats(
            totalComponents = components.size,
            componentsByCategory = categoryIndex.mapValues { it.value.size },
            componentsByComplexity = complexityIndex.mapValues { it.value.size },
            totalTags = tagIndex.size,
            mostUsedTags = tagIndex.entries.sortedByDescending { it.value.size }.take(10)
                .map { it.key to it.value.size }
        )
    }
    
    /**
     * Suggest components dựa trên usage patterns
     */
    fun suggestComponents(
        baseComponent: ComponentMetadata,
        limit: Int = 5
    ): List<ComponentMetadata> {
        // Find components with similar tags
        val similarByTags = baseComponent.tags.flatMap { tag ->
            searchByTags(listOf(tag))
        }.filter { it.id != baseComponent.id }
        
        // Find components in same category
        val similarByCategory = getComponentsByCategory(baseComponent.category)
            .filter { it.id != baseComponent.id }
        
        // Combine and rank
        val suggestions = (similarByTags + similarByCategory)
            .groupBy { it.id }
            .mapValues { it.value.first() }
            .values
            .sortedBy { it.name }
            .take(limit)
        
        return suggestions
    }
    
    /**
     * Validate component metadata
     */
    fun validateComponent(metadata: ComponentMetadata): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()
        
        // Check required fields
        if (metadata.id.isBlank()) {
            errors.add(ValidationError.EmptyField("id"))
        }
        
        if (metadata.name.isBlank()) {
            errors.add(ValidationError.EmptyField("name"))
        }
        
        if (metadata.templatePath.isBlank()) {
            errors.add(ValidationError.EmptyField("templatePath"))
        }
        
        // Check for duplicate ID
        if (components.containsKey(metadata.id)) {
            errors.add(ValidationError.DuplicateId(metadata.id))
        }
        
        // Validate customization options
        metadata.customizationOptions.forEach { option ->
            if (option.key.isBlank()) {
                errors.add(ValidationError.InvalidCustomizationOption("Empty key"))
            }
        }
        
        return errors
    }
    
    /**
     * Clear tất cả components (for testing)
     */
    fun clear() {
        components.clear()
        categoryIndex.clear()
        tagIndex.clear()
        complexityIndex.clear()
    }
    
    /**
     * Register default components
     */
    private fun registerDefaultComponents() {
        registerBasicButtons()
        registerBasicInputs()
        registerBasicCards()
        registerBasicLayouts()
    }
    
    private fun registerBasicButtons() {
        registerComponent(
            ComponentMetadata(
                id = "primary_button",
                name = "Primary Button",
                category = ComponentCategory.BUTTON,
                description = "Material 3 primary button với optional icon và loading state",
                templatePath = "templates/buttons/primary_button.kt.template",
                dependencies = listOf("androidx.compose.material3:material3"),
                customizationOptions = listOf(
                    CustomizationOption(
                        key = "HAS_ICON",
                        displayName = "Include Icon",
                        type = OptionType.BOOLEAN,
                        defaultValue = false,
                        description = "Add icon support to button"
                    ),
                    CustomizationOption(
                        key = "HAS_LOADING",
                        displayName = "Loading State",
                        type = OptionType.BOOLEAN,
                        defaultValue = true,
                        description = "Include loading state functionality"
                    ),
                    CustomizationOption(
                        key = "BUTTON_SIZE",
                        displayName = "Button Size",
                        type = OptionType.ENUM,
                        defaultValue = "MEDIUM",
                        possibleValues = listOf("SMALL", "MEDIUM", "LARGE"),
                        description = "Button size variant"
                    )
                ),
                complexity = ComplexityLevel.SIMPLE,
                tags = listOf("button", "primary", "material3", "interactive", "cta"),
                estimatedLines = 45
            )
        )
        
        registerComponent(
            ComponentMetadata(
                id = "floating_action_button",
                name = "Floating Action Button",
                category = ComponentCategory.BUTTON,
                description = "Material 3 FAB với animation và extended variant",
                templatePath = "templates/buttons/floating_action_button.kt.template",
                dependencies = listOf("androidx.compose.material3:material3"),
                customizationOptions = listOf(
                    CustomizationOption(
                        key = "IS_EXTENDED",
                        displayName = "Extended FAB",
                        type = OptionType.BOOLEAN,
                        defaultValue = false,
                        description = "Use extended FAB with text"
                    ),
                    CustomizationOption(
                        key = "FAB_SIZE",
                        displayName = "FAB Size",
                        type = OptionType.ENUM,
                        defaultValue = "NORMAL",
                        possibleValues = listOf("SMALL", "NORMAL", "LARGE"),
                        description = "FAB size variant"
                    )
                ),
                complexity = ComplexityLevel.SIMPLE,
                tags = listOf("fab", "floating", "action", "material3"),
                estimatedLines = 35
            )
        )
    }
    
    private fun registerBasicInputs() {
        registerComponent(
            ComponentMetadata(
                id = "text_input",
                name = "Text Input Field",
                category = ComponentCategory.INPUT,
                description = "Material 3 text field với validation và error handling",
                templatePath = "templates/inputs/text_input.kt.template",
                dependencies = listOf("androidx.compose.material3:material3"),
                customizationOptions = listOf(
                    CustomizationOption(
                        key = "HAS_VALIDATION",
                        displayName = "Include Validation",
                        type = OptionType.BOOLEAN,
                        defaultValue = true,
                        description = "Add input validation support"
                    ),
                    CustomizationOption(
                        key = "INPUT_TYPE",
                        displayName = "Input Type",
                        type = OptionType.ENUM,
                        defaultValue = "TEXT",
                        possibleValues = listOf("TEXT", "EMAIL", "PHONE", "NUMBER"),
                        description = "Type of input field"
                    ),
                    CustomizationOption(
                        key = "HAS_COUNTER",
                        displayName = "Character Counter",
                        type = OptionType.BOOLEAN,
                        defaultValue = false,
                        description = "Show character counter"
                    )
                ),
                complexity = ComplexityLevel.MEDIUM,
                tags = listOf("input", "text", "validation", "material3", "form"),
                estimatedLines = 80
            )
        )
    }
    
    private fun registerBasicCards() {
        registerComponent(
            ComponentMetadata(
                id = "info_card",
                name = "Information Card",
                category = ComponentCategory.CARD,
                description = "Basic card để display thông tin với optional actions",
                templatePath = "templates/cards/info_card.kt.template",
                dependencies = listOf("androidx.compose.material3:material3"),
                customizationOptions = listOf(
                    CustomizationOption(
                        key = "HAS_ACTIONS",
                        displayName = "Include Actions",
                        type = OptionType.BOOLEAN,
                        defaultValue = false,
                        description = "Add action buttons to card"
                    ),
                    CustomizationOption(
                        key = "CARD_ELEVATION",
                        displayName = "Card Elevation",
                        type = OptionType.ENUM,
                        defaultValue = "MEDIUM",
                        possibleValues = listOf("NONE", "LOW", "MEDIUM", "HIGH"),
                        description = "Card shadow elevation"
                    )
                ),
                complexity = ComplexityLevel.SIMPLE,
                tags = listOf("card", "info", "display", "material3"),
                estimatedLines = 50
            )
        )
    }
    
    private fun registerBasicLayouts() {
        registerComponent(
            ComponentMetadata(
                id = "responsive_grid",
                name = "Responsive Grid",
                category = ComponentCategory.LAYOUT,
                description = "Auto-adjusting grid layout cho different screen sizes",
                templatePath = "templates/layouts/responsive_grid.kt.template",
                dependencies = listOf("androidx.compose.foundation:foundation"),
                customizationOptions = listOf(
                    CustomizationOption(
                        key = "MIN_ITEM_WIDTH",
                        displayName = "Minimum Item Width",
                        type = OptionType.DIMENSION,
                        defaultValue = "120.dp",
                        description = "Minimum width for grid items"
                    ),
                    CustomizationOption(
                        key = "SPACING",
                        displayName = "Grid Spacing",
                        type = OptionType.DIMENSION,
                        defaultValue = "8.dp",
                        description = "Spacing between grid items"
                    )
                ),
                complexity = ComplexityLevel.MEDIUM,
                tags = listOf("layout", "grid", "responsive", "adaptive"),
                estimatedLines = 60
            )
        )
    }
}

/**
 * Statistics về component registry
 */
data class RegistryStats(
    val totalComponents: Int,
    val componentsByCategory: Map<ComponentCategory, Int>,
    val componentsByComplexity: Map<ComplexityLevel, Int>,
    val totalTags: Int,
    val mostUsedTags: List<Pair<String, Int>>
)

/**
 * Validation errors cho component metadata
 */
sealed class ValidationError {
    data class EmptyField(val fieldName: String) : ValidationError()
    data class DuplicateId(val id: String) : ValidationError()
    data class InvalidCustomizationOption(val reason: String) : ValidationError()
    data class MissingDependency(val dependency: String) : ValidationError()
}