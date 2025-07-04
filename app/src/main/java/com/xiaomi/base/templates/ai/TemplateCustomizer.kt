package com.xiaomi.base.templates.ai

import com.xiaomi.base.templates.registry.ComponentMetadata
import com.xiaomi.base.templates.registry.CustomizationOption
import com.xiaomi.base.templates.registry.OptionType
import com.xiaomi.base.templates.registry.ProjectContext
import com.xiaomi.base.templates.registry.ValidationResult

/**
 * AI-powered template customizer để tự động tùy chỉnh templates
 * dựa trên user requirements và context
 */
class TemplateCustomizer {
    
    /**
     * Generate customizations cho component dựa trên requirements
     */
    fun generateCustomizations(
        component: ComponentMetadata,
        requirements: String,
        context: ProjectContext
    ): CustomizationResult {
        val normalizedRequirements = normalizeRequirements(requirements)
        val extractedOptions = extractCustomizationOptions(normalizedRequirements, component)
        val validatedOptions = validateCustomizations(extractedOptions, component)
        val optimizedOptions = optimizeForContext(validatedOptions, context)
        
        return CustomizationResult(
            originalRequirements = requirements,
            component = component,
            customizations = optimizedOptions,
            validationResults = validatedOptions.map { (key, value) ->
                validateSingleOption(component, key, value)
            },
            suggestions = generateSuggestions(component, optimizedOptions, context),
            confidence = calculateCustomizationConfidence(optimizedOptions, component)
        )
    }
    
    /**
     * Normalize requirements text
     */
    private fun normalizeRequirements(requirements: String): String {
        return requirements
            .lowercase()
            .replace(Regex("[^a-z0-9\\s]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
    
    /**
     * Extract customization options từ requirements
     */
    private fun extractCustomizationOptions(
        requirements: String,
        component: ComponentMetadata
    ): Map<String, Any> {
        val customizations = mutableMapOf<String, Any>()
        
        // Extract colors
        extractColors(requirements)?.let { color ->
            customizations["PRIMARY_COLOR"] = color
        }
        
        // Extract sizes
        extractSize(requirements)?.let { size ->
            customizations["SIZE"] = size
        }
        
        // Extract text content
        extractText(requirements)?.let { text ->
            customizations["TEXT"] = text
            customizations["COMPONENT_NAME"] = generateComponentName(text)
        }
        
        // Extract boolean features
        customizations.putAll(extractBooleanFeatures(requirements))
        
        // Extract component-specific options
        customizations.putAll(extractComponentSpecificOptions(requirements, component))
        
        // Apply default values for missing required options
        customizations.putAll(applyDefaultValues(component, customizations))
        
        return customizations
    }
    
    /**
     * Extract color information
     */
    private fun extractColors(requirements: String): String? {
        val colorPatterns = mapOf(
            "red" to "#F44336",
            "blue" to "#2196F3",
            "green" to "#4CAF50",
            "orange" to "#FF9800",
            "purple" to "#9C27B0",
            "pink" to "#E91E63",
            "yellow" to "#FFEB3B",
            "cyan" to "#00BCD4",
            "teal" to "#009688",
            "indigo" to "#3F51B5",
            "primary" to "#6200EE",
            "secondary" to "#03DAC6",
            "error" to "#B00020"
        )
        
        return colorPatterns.entries.firstOrNull { (colorName, _) ->
            requirements.contains(colorName)
        }?.value
    }
    
    /**
     * Extract size information
     */
    private fun extractSize(requirements: String): String? {
        return when {
            requirements.contains("small") || requirements.contains("compact") -> "small"
            requirements.contains("large") || requirements.contains("big") -> "large"
            requirements.contains("medium") || requirements.contains("normal") -> "medium"
            else -> null
        }
    }
    
    /**
     * Extract text content
     */
    private fun extractText(requirements: String): String? {
        // Look for quoted text
        val quotedTextRegex = "\"([^\"]+)\"".toRegex()
        quotedTextRegex.find(requirements)?.let {
            return it.groupValues[1]
        }
        
        // Look for text after "text", "label", "title" keywords
        val textKeywords = listOf("text", "label", "title", "caption", "name")
        textKeywords.forEach { keyword ->
            val pattern = "$keyword\\s+(\\w+(?:\\s+\\w+)*)".toRegex()
            pattern.find(requirements)?.let {
                return it.groupValues[1]
            }
        }
        
        return null
    }
    
    /**
     * Extract boolean features
     */
    private fun extractBooleanFeatures(requirements: String): Map<String, Boolean> {
        val features = mutableMapOf<String, Boolean>()
        
        val featurePatterns = mapOf(
            "HAS_ICON" to listOf("icon", "with icon", "has icon"),
            "HAS_LOADING" to listOf("loading", "spinner", "progress"),
            "HAS_VALIDATION" to listOf("validation", "validate", "error", "required"),
            "HAS_ANIMATION" to listOf("animation", "animated", "transition"),
            "IS_OUTLINED" to listOf("outlined", "border", "stroke"),
            "IS_FILLED" to listOf("filled", "solid", "background"),
            "IS_ELEVATED" to listOf("elevated", "shadow", "raised"),
            "IS_ROUNDED" to listOf("rounded", "round", "circular"),
            "IS_DISABLED" to listOf("disabled", "inactive", "readonly"),
            "IS_MULTILINE" to listOf("multiline", "multiple lines", "textarea")
        )
        
        featurePatterns.forEach { (feature, patterns) ->
            features[feature] = patterns.any { pattern ->
                requirements.contains(pattern)
            }
        }
        
        return features.filterValues { it }
    }
    
    /**
     * Extract component-specific options
     */
    private fun extractComponentSpecificOptions(
        requirements: String,
        component: ComponentMetadata
    ): Map<String, Any> {
        val options = mutableMapOf<String, Any>()
        
        when (component.category) {
            com.xiaomi.base.templates.registry.ComponentCategory.BUTTON -> {
                // Button-specific options
                if (requirements.contains("floating")) {
                    options["BUTTON_TYPE"] = "floating"
                } else if (requirements.contains("text button")) {
                    options["BUTTON_TYPE"] = "text"
                } else {
                    options["BUTTON_TYPE"] = "filled"
                }
            }
            
            com.xiaomi.base.templates.registry.ComponentCategory.INPUT -> {
                // Input-specific options
                if (requirements.contains("password")) {
                    options["INPUT_TYPE"] = "password"
                    options["HAS_VISIBILITY_TOGGLE"] = true
                } else if (requirements.contains("email")) {
                    options["INPUT_TYPE"] = "email"
                } else if (requirements.contains("number")) {
                    options["INPUT_TYPE"] = "number"
                } else {
                    options["INPUT_TYPE"] = "text"
                }
                
                if (requirements.contains("placeholder")) {
                    val placeholderRegex = "placeholder\\s+\"([^\"]+)\"".toRegex()
                    placeholderRegex.find(requirements)?.let {
                        options["PLACEHOLDER"] = it.groupValues[1]
                    }
                }
            }
            
            com.xiaomi.base.templates.registry.ComponentCategory.CARD -> {
                // Card-specific options
                if (requirements.contains("clickable")) {
                    options["IS_CLICKABLE"] = true
                }
                
                if (requirements.contains("image")) {
                    options["HAS_IMAGE"] = true
                }
            }
            
            else -> {}
        }
        
        return options
    }
    
    /**
     * Apply default values cho missing required options
     */
    private fun applyDefaultValues(
        component: ComponentMetadata,
        existingCustomizations: Map<String, Any>
    ): Map<String, Any> {
        val defaults = mutableMapOf<String, Any>()
        
        component.customizationOptions.forEach { option ->
            if (!existingCustomizations.containsKey(option.key) && option.defaultValue != null) {
                defaults[option.key] = option.defaultValue
            }
        }
        
        // Component name default
        if (!existingCustomizations.containsKey("COMPONENT_NAME")) {
            defaults["COMPONENT_NAME"] = generateComponentName(component.name)
        }
        
        return defaults
    }
    
    /**
     * Validate customizations
     */
    private fun validateCustomizations(
        customizations: Map<String, Any>,
        component: ComponentMetadata
    ): Map<String, Any> {
        val validated = mutableMapOf<String, Any>()
        
        customizations.forEach { (key, value) ->
            val option = component.customizationOptions.find { it.key == key }
            if (option != null) {
                val validatedValue = validateOptionValue(option, value)
                if (validatedValue != null) {
                    validated[key] = validatedValue
                }
            } else {
                // Allow unknown options for flexibility
                validated[key] = value
            }
        }
        
        return validated
    }
    
    /**
     * Validate single option value
     */
    private fun validateOptionValue(option: CustomizationOption, value: Any): Any? {
        return when (option.type) {
            OptionType.TEXT -> {
                val stringValue = value.toString()
                option.possibleValues?.let { allowedValues ->
                    if (allowedValues.isNotEmpty() && !allowedValues.contains(stringValue)) {
                        return option.defaultValue
                    }
                }
                stringValue
            }
            
            OptionType.BOOLEAN -> {
                when (value) {
                    is Boolean -> value
                    is String -> value.lowercase() in listOf("true", "yes", "1")
                    is Number -> value.toDouble() != 0.0
                    else -> option.defaultValue as? Boolean ?: false
                }
            }
            
            OptionType.NUMBER -> {
                when (value) {
                    is Number -> value
                    is String -> value.toDoubleOrNull() ?: option.defaultValue
                    else -> option.defaultValue
                }
            }
            
            OptionType.COLOR -> {
                val stringValue = value.toString()
                if (isValidColor(stringValue)) {
                    stringValue
                } else {
                    option.defaultValue
                }
            }
            
            OptionType.ENUM -> {
                val stringValue = value.toString()
                option.possibleValues?.let { allowedValues ->
                    if (allowedValues.contains(stringValue)) {
                        return stringValue
                    }
                }
                option.defaultValue
            }
            
            else -> option.defaultValue
        }
    }
    
    /**
     * Validate single option
     */
    private fun validateSingleOption(
        component: ComponentMetadata,
        key: String,
        value: Any
    ): ValidationResult {
        val option = component.customizationOptions.find { it.key == key }
            ?: return ValidationResult.Success
        
        val errors = mutableListOf<String>()
        
        // Type validation
        when (option.type) {
            OptionType.TEXT -> {
                if (value !is String) {
                    errors.add("Expected string value for $key")
                }
            }
            OptionType.BOOLEAN -> {
                if (value !is Boolean && value.toString().lowercase() !in listOf("true", "false", "yes", "no", "1", "0")) {
                    errors.add("Expected boolean value for $key")
                }
            }
            OptionType.NUMBER -> {
                if (value !is Number && value.toString().toDoubleOrNull() == null) {
                    errors.add("Expected numeric value for $key")
                }
            }
            OptionType.COLOR -> {
                if (!isValidColor(value.toString())) {
                    errors.add("Invalid color format for $key")
                }
            }
            OptionType.ENUM -> {
                option.possibleValues?.let { allowedValues ->
                    if (!allowedValues.contains(value.toString())) {
                        errors.add("Invalid value for $key. Allowed: ${allowedValues.joinToString(", ")}")
                    }
                }
            }
            else -> {
                // Handle other option types if needed
            }
        }
        
        // Custom validation rule
        option.validation?.let { rule ->
            val result = rule.validate(value)
            if (result is ValidationResult.Error) {
                errors.add(result.message)
            }
        }
        
        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(errors.joinToString("; "))
        }
    }
    
    /**
     * Check if color format is valid
     */
    private fun isValidColor(color: String): Boolean {
        return color.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) ||
               color.matches(Regex("^rgb\\(\\d+,\\s*\\d+,\\s*\\d+\\)$")) ||
               color.matches(Regex("^rgba\\(\\d+,\\s*\\d+,\\s*\\d+,\\s*[0-1]?(\\.[0-9]+)?\\)$"))
    }
    
    /**
     * Optimize customizations cho context
     */
    private fun optimizeForContext(
        customizations: Map<String, Any>,
        context: ProjectContext
    ): Map<String, Any> {
        val optimized = customizations.toMutableMap()
        
        // Apply theme-based optimizations
        if (context.customTheme && !optimized.containsKey("PRIMARY_COLOR")) {
            optimized["PRIMARY_COLOR"] = "#BB86FC"
        }
        
        // Apply accessibility optimizations
        if (context.hasDataBinding) {
            optimized["HAS_CONTENT_DESCRIPTION"] = true
            optimized["MIN_TOUCH_TARGET_SIZE"] = 48
        }
        
        // Apply performance optimizations based on architecture pattern
        if (context.architecturePattern == "MVVM") {
            optimized["HAS_ANIMATION"] = true
        }
        
        return optimized
    }
    
    /**
     * Generate suggestions cho improvements
     */
    private fun generateSuggestions(
        component: ComponentMetadata,
        customizations: Map<String, Any>,
        context: ProjectContext
    ): List<CustomizationSuggestion> {
        val suggestions = mutableListOf<CustomizationSuggestion>()
        
        // Accessibility suggestions
        if (!customizations.containsKey("HAS_CONTENT_DESCRIPTION")) {
            suggestions.add(
                CustomizationSuggestion(
                    type = SuggestionType.ACCESSIBILITY,
                    title = "Add Content Description",
                    description = "Consider adding content description for better accessibility",
                    suggestedValue = mapOf("HAS_CONTENT_DESCRIPTION" to true),
                    priority = SuggestionPriority.MEDIUM
                )
            )
        }
        
        // Performance suggestions
        if (customizations["HAS_ANIMATION"] == true && context.minSdk < 24) {
            suggestions.add(
                CustomizationSuggestion(
                    type = SuggestionType.PERFORMANCE,
                    title = "Disable Animation",
                    description = "Consider disabling animation for older Android versions",
                    suggestedValue = mapOf("HAS_ANIMATION" to false),
                    priority = SuggestionPriority.LOW
                )
            )
        }
        
        // Design suggestions
        if (!customizations.containsKey("IS_ELEVATED") && component.category == com.xiaomi.base.templates.registry.ComponentCategory.CARD) {
            suggestions.add(
                CustomizationSuggestion(
                    type = SuggestionType.DESIGN,
                    title = "Add Elevation",
                    description = "Cards typically look better with elevation",
                    suggestedValue = mapOf("IS_ELEVATED" to true),
                    priority = SuggestionPriority.LOW
                )
            )
        }
        
        return suggestions.sortedBy { it.priority }
    }
    
    /**
     * Calculate customization confidence
     */
    private fun calculateCustomizationConfidence(
        customizations: Map<String, Any>,
        component: ComponentMetadata
    ): Double {
        if (customizations.isEmpty()) return 0.0
        
        val totalOptions = component.customizationOptions.size.coerceAtLeast(1)
        val customizedOptions = customizations.keys.count { key ->
            component.customizationOptions.any { it.key == key }
        }
        
        val coverageScore = customizedOptions.toDouble() / totalOptions
        val validationScore = customizations.count { (key, value) ->
            val option = component.customizationOptions.find { it.key == key }
            option?.let { validateOptionValue(it, value) != null } ?: true
        }.toDouble() / customizations.size
        
        return (coverageScore * 0.4 + validationScore * 0.6).coerceIn(0.0, 1.0)
    }
    
    /**
     * Generate component name từ text
     */
    private fun generateComponentName(text: String): String {
        return text
            .split(" ")
            .joinToString("") { word -> 
                word.replaceFirstChar { it.uppercase() }
            }
            .replace(Regex("[^a-zA-Z0-9]"), "")
            .let { if (it.isBlank()) "CustomComponent" else it }
    }
}

/**
 * Customization result
 */
data class CustomizationResult(
    val originalRequirements: String,
    val component: ComponentMetadata,
    val customizations: Map<String, Any>,
    val validationResults: List<ValidationResult>,
    val suggestions: List<CustomizationSuggestion>,
    val confidence: Double,
    val processingTime: Long = System.currentTimeMillis()
) {
    /**
     * Check if all validations passed
     */
    fun isValid(): Boolean {
        return validationResults.all { it is ValidationResult.Success }
    }
    
    /**
     * Get all validation errors
     */
    fun getAllErrors(): List<String> {
        return validationResults.filterIsInstance<ValidationResult.Error>().map { it.message }
    }
    
    /**
     * Get high priority suggestions
     */
    fun getHighPrioritySuggestions(): List<CustomizationSuggestion> {
        return suggestions.filter { it.priority == SuggestionPriority.HIGH }
    }
}

/**
 * Customization suggestion
 */
data class CustomizationSuggestion(
    val type: SuggestionType,
    val title: String,
    val description: String,
    val suggestedValue: Map<String, Any>,
    val priority: SuggestionPriority
)

/**
 * Suggestion types
 */
enum class SuggestionType {
    ACCESSIBILITY,
    PERFORMANCE,
    DESIGN,
    USABILITY,
    BEST_PRACTICE
}

/**
 * Suggestion priority
 */
enum class SuggestionPriority {
    HIGH,
    MEDIUM,
    LOW
}