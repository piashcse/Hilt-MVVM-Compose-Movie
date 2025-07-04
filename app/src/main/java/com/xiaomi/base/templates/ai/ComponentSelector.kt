package com.xiaomi.base.templates.ai

import com.xiaomi.base.templates.registry.ComponentCategory
import com.xiaomi.base.templates.registry.ComponentMetadata
import com.xiaomi.base.templates.registry.ComponentRegistry
import com.xiaomi.base.templates.registry.ComplexityLevel
import com.xiaomi.base.templates.registry.ProjectContext

/**
 * AI-powered component selector để chọn và kết hợp components
 * dựa trên natural language requirements
 */
class ComponentSelector(
    private val registry: ComponentRegistry
) {
    
    /**
     * Analyze user requirement và suggest components
     */
    fun analyzeRequirement(requirement: String, context: ProjectContext): ComponentSuggestion {
        val normalizedRequirement = normalizeRequirement(requirement)
        val keywords = extractKeywords(normalizedRequirement)
        val intent = detectIntent(keywords, normalizedRequirement)
        
        val suggestedComponents = findMatchingComponents(keywords, intent, context)
        val combinations = generateCombinations(suggestedComponents, intent)
        
        return ComponentSuggestion(
            originalRequirement = requirement,
            detectedIntent = intent,
            keywords = keywords,
            suggestedComponents = suggestedComponents,
            recommendedCombinations = combinations,
            confidence = calculateConfidence(suggestedComponents, keywords),
            alternatives = findAlternatives(suggestedComponents, context)
        )
    }
    
    /**
     * Normalize user requirement text
     */
    private fun normalizeRequirement(requirement: String): String {
        return requirement
            .lowercase()
            .replace(Regex("[^a-z0-9\\s]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
    
    /**
     * Extract keywords từ requirement
     */
    private fun extractKeywords(requirement: String): List<String> {
        val commonWords = setOf(
            "a", "an", "the", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with", "by",
            "i", "want", "need", "create", "make", "build", "add", "show", "display", "have", "get"
        )
        
        return requirement
            .split(" ")
            .filter { it.length > 2 && !commonWords.contains(it) }
            .distinct()
    }
    
    /**
     * Detect intent từ keywords và requirement
     */
    private fun detectIntent(keywords: List<String>, requirement: String): UserIntent {
        val intentPatterns = mapOf(
            UserIntent.CREATE_FORM to listOf("form", "input", "field", "submit", "register", "login", "signup"),
            UserIntent.CREATE_LIST to listOf("list", "items", "scroll", "recyclerview", "collection", "grid"),
            UserIntent.CREATE_NAVIGATION to listOf("navigation", "menu", "tab", "drawer", "bottom", "navigate"),
            UserIntent.CREATE_CARD to listOf("card", "item", "product", "post", "article", "content"),
            UserIntent.CREATE_BUTTON to listOf("button", "click", "action", "submit", "save", "delete", "edit"),
            UserIntent.CREATE_DIALOG to listOf("dialog", "popup", "modal", "alert", "confirmation", "picker"),
            UserIntent.CREATE_LAYOUT to listOf("layout", "screen", "page", "container", "wrapper", "section"),
            UserIntent.CREATE_MEDIA to listOf("image", "video", "photo", "gallery", "camera", "media"),
            UserIntent.CREATE_CHART to listOf("chart", "graph", "plot", "analytics", "statistics", "data")
        )
        
        val scores = intentPatterns.mapValues { (_, patterns) ->
            patterns.count { pattern -> 
                keywords.any { keyword -> keyword.contains(pattern) || pattern.contains(keyword) } ||
                requirement.contains(pattern)
            }
        }
        
        return scores.maxByOrNull { it.value }?.key ?: UserIntent.UNKNOWN
    }
    
    /**
     * Find matching components dựa trên keywords và intent
     */
    private fun findMatchingComponents(
        keywords: List<String>,
        intent: UserIntent,
        context: ProjectContext
    ): List<ComponentMatch> {
        val allComponents = registry.findComponents()
        
        return allComponents.mapNotNull { component ->
            val score = calculateMatchScore(component, keywords, intent, context)
            if (score > 0.3) {
                ComponentMatch(
                    component = component,
                    matchScore = score,
                    matchReasons = getMatchReasons(component, keywords, intent)
                )
            } else null
        }.sortedByDescending { it.matchScore }
    }
    
    /**
     * Calculate match score cho component
     */
    private fun calculateMatchScore(
        component: ComponentMetadata,
        keywords: List<String>,
        intent: UserIntent,
        context: ProjectContext
    ): Double {
        var score = 0.0
        
        // Intent matching (40% weight)
        score += when (intent) {
            UserIntent.CREATE_BUTTON -> if (component.category == ComponentCategory.BUTTON) 0.4 else 0.0
            UserIntent.CREATE_FORM -> if (component.category == ComponentCategory.INPUT) 0.4 else 0.0
            UserIntent.CREATE_CARD -> if (component.category == ComponentCategory.CARD) 0.4 else 0.0
            UserIntent.CREATE_LIST -> if (component.category == ComponentCategory.LIST) 0.4 else 0.0
            UserIntent.CREATE_NAVIGATION -> if (component.category == ComponentCategory.NAVIGATION) 0.4 else 0.0
            UserIntent.CREATE_DIALOG -> if (component.category == ComponentCategory.DIALOG) 0.4 else 0.0
            UserIntent.CREATE_LAYOUT -> if (component.category == ComponentCategory.LAYOUT) 0.4 else 0.0
            else -> 0.1
        }
        
        // Keyword matching (30% weight)
        val keywordMatches = keywords.count { keyword ->
            component.name.lowercase().contains(keyword) ||
            component.description.lowercase().contains(keyword) ||
            component.tags.any { tag -> tag.lowercase().contains(keyword) }
        }
        score += (keywordMatches.toDouble() / keywords.size.coerceAtLeast(1)) * 0.3
        
        // Tag matching (20% weight)
        val tagMatches = component.tags.count { tag ->
            keywords.any { keyword -> tag.lowercase().contains(keyword) }
        }
        score += (tagMatches.toDouble() / component.tags.size.coerceAtLeast(1)) * 0.2
        
        // Complexity preference (10% weight) - prefer simpler components by default
        score += when (component.complexity) {
            ComplexityLevel.SIMPLE -> 0.1
            ComplexityLevel.MEDIUM -> 0.05
            ComplexityLevel.COMPLEX -> 0.02
            ComplexityLevel.EXPERT -> 0.0
        }
        
        return score.coerceIn(0.0, 1.0)
    }
    
    /**
     * Get match reasons cho component
     */
    private fun getMatchReasons(
        component: ComponentMetadata,
        keywords: List<String>,
        intent: UserIntent
    ): List<String> {
        val reasons = mutableListOf<String>()
        
        // Intent match
        if (isIntentMatch(component.category, intent)) {
            reasons.add("Matches intended component type")
        }
        
        // Keyword matches
        keywords.forEach { keyword ->
            when {
                component.name.lowercase().contains(keyword) -> 
                    reasons.add("Name contains '$keyword'")
                component.description.lowercase().contains(keyword) -> 
                    reasons.add("Description mentions '$keyword'")
                component.tags.any { tag -> tag.lowercase().contains(keyword) } -> 
                    reasons.add("Tagged with '$keyword'")
            }
        }
        
        return reasons
    }
    
    /**
     * Check if component category matches intent
     */
    private fun isIntentMatch(category: ComponentCategory, intent: UserIntent): Boolean {
        return when (intent) {
            UserIntent.CREATE_BUTTON -> category == ComponentCategory.BUTTON
            UserIntent.CREATE_FORM -> category == ComponentCategory.INPUT
            UserIntent.CREATE_CARD -> category == ComponentCategory.CARD
            UserIntent.CREATE_LIST -> category == ComponentCategory.LIST
            UserIntent.CREATE_NAVIGATION -> category == ComponentCategory.NAVIGATION
            UserIntent.CREATE_DIALOG -> category == ComponentCategory.DIALOG
            UserIntent.CREATE_LAYOUT -> category == ComponentCategory.LAYOUT
            UserIntent.CREATE_MEDIA -> category == ComponentCategory.MEDIA
            UserIntent.CREATE_CHART -> category == ComponentCategory.CHART
            else -> false
        }
    }
    
    /**
     * Generate component combinations
     */
    private fun generateCombinations(
        components: List<ComponentMatch>,
        intent: UserIntent
    ): List<ComponentCombination> {
        if (components.isEmpty()) return emptyList()
        
        val combinations = mutableListOf<ComponentCombination>()
        
        // Single component combinations
        components.take(3).forEach { match ->
            combinations.add(
                ComponentCombination(
                    components = listOf(match.component),
                    description = "Single ${match.component.name}",
                    confidence = match.matchScore,
                    estimatedComplexity = match.component.complexity
                )
            )
        }
        
        // Multi-component combinations based on intent
        when (intent) {
            UserIntent.CREATE_FORM -> {
                val inputs = components.filter { it.component.category == ComponentCategory.INPUT }
                val buttons = components.filter { it.component.category == ComponentCategory.BUTTON }
                
                if (inputs.isNotEmpty() && buttons.isNotEmpty()) {
                    combinations.add(
                        ComponentCombination(
                            components = listOf(inputs.first().component, buttons.first().component),
                            description = "Form with input field and submit button",
                            confidence = (inputs.first().matchScore + buttons.first().matchScore) / 2,
                            estimatedComplexity = ComplexityLevel.MEDIUM
                        )
                    )
                }
            }
            UserIntent.CREATE_LIST -> {
                val lists = components.filter { it.component.category == ComponentCategory.LIST }
                val cards = components.filter { it.component.category == ComponentCategory.CARD }
                
                if (lists.isNotEmpty() && cards.isNotEmpty()) {
                    combinations.add(
                        ComponentCombination(
                            components = listOf(lists.first().component, cards.first().component),
                            description = "List with custom card items",
                            confidence = (lists.first().matchScore + cards.first().matchScore) / 2,
                            estimatedComplexity = ComplexityLevel.MEDIUM
                        )
                    )
                }
            }
            else -> {}
        }
        
        return combinations.sortedByDescending { it.confidence }
    }
    
    /**
     * Calculate overall confidence score
     */
    private fun calculateConfidence(components: List<ComponentMatch>, keywords: List<String>): Double {
        if (components.isEmpty()) return 0.0
        
        val avgScore = components.take(3).map { it.matchScore }.average()
        val keywordCoverage = if (keywords.isNotEmpty()) {
            keywords.count { keyword ->
                components.any { match ->
                    match.component.name.lowercase().contains(keyword) ||
                    match.component.tags.any { tag -> tag.lowercase().contains(keyword) }
                }
            }.toDouble() / keywords.size
        } else 0.5
        
        return (avgScore * 0.7 + keywordCoverage * 0.3).coerceIn(0.0, 1.0)
    }
    
    /**
     * Find alternative components
     */
    private fun findAlternatives(
        suggestedComponents: List<ComponentMatch>,
        context: ProjectContext
    ): List<ComponentMetadata> {
        if (suggestedComponents.isEmpty()) return emptyList()
        
        val primaryCategory = suggestedComponents.first().component.category
        
        return registry.getComponentsByCategory(primaryCategory)
            .filter { component ->
                suggestedComponents.none { it.component.id == component.id }
            }
            .take(3)
    }
}

/**
 * User intent types
 */
enum class UserIntent(val displayName: String) {
    CREATE_BUTTON("Create Button"),
    CREATE_FORM("Create Form"),
    CREATE_LIST("Create List"),
    CREATE_CARD("Create Card"),
    CREATE_NAVIGATION("Create Navigation"),
    CREATE_DIALOG("Create Dialog"),
    CREATE_LAYOUT("Create Layout"),
    CREATE_MEDIA("Create Media Component"),
    CREATE_CHART("Create Chart"),
    UNKNOWN("Unknown Intent")
}

/**
 * Component suggestion result
 */
data class ComponentSuggestion(
    val originalRequirement: String,
    val detectedIntent: UserIntent,
    val keywords: List<String>,
    val suggestedComponents: List<ComponentMatch>,
    val recommendedCombinations: List<ComponentCombination>,
    val confidence: Double,
    val alternatives: List<ComponentMetadata>,
    val processingTime: Long = System.currentTimeMillis()
) {
    /**
     * Get best suggestion
     */
    fun getBestSuggestion(): ComponentMatch? {
        return suggestedComponents.firstOrNull()
    }
    
    /**
     * Get best combination
     */
    fun getBestCombination(): ComponentCombination? {
        return recommendedCombinations.firstOrNull()
    }
    
    /**
     * Check if suggestion is reliable
     */
    fun isReliable(): Boolean {
        return confidence > 0.6 && suggestedComponents.isNotEmpty()
    }
}

/**
 * Component match với score
 */
data class ComponentMatch(
    val component: ComponentMetadata,
    val matchScore: Double,
    val matchReasons: List<String>
)

/**
 * Component combination suggestion
 */
data class ComponentCombination(
    val components: List<ComponentMetadata>,
    val description: String,
    val confidence: Double,
    val estimatedComplexity: ComplexityLevel,
    val estimatedDevelopmentTime: String = estimateTime(components, estimatedComplexity)
) {
    companion object {
        private fun estimateTime(components: List<ComponentMetadata>, complexity: ComplexityLevel): String {
            val baseTime = components.size * when (complexity) {
                ComplexityLevel.SIMPLE -> 15
                ComplexityLevel.MEDIUM -> 30
                ComplexityLevel.COMPLEX -> 60
                ComplexityLevel.EXPERT -> 90
            }
            
            return when {
                baseTime < 30 -> "15-30 minutes"
                baseTime < 60 -> "30-60 minutes"
                baseTime < 120 -> "1-2 hours"
                else -> "2+ hours"
            }
        }
    }
}