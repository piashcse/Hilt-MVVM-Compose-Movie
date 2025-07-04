package com.xiaomi.base.templates.registry

/**
 * Metadata cho mỗi component template trong hệ thống
 * Chứa tất cả thông tin cần thiết để AI có thể select và customize component
 */
data class ComponentMetadata(
    val id: String,
    val name: String,
    val category: ComponentCategory,
    val description: String,
    val templatePath: String,
    val dependencies: List<String> = emptyList(),
    val customizationOptions: List<CustomizationOption> = emptyList(),
    val previewImage: String? = null,
    val useCases: List<String> = emptyList(),
    val complexity: ComplexityLevel = ComplexityLevel.SIMPLE,
    val tags: List<String> = emptyList(),
    val version: String = "1.0.0",
    val author: String = "AI Generated",
    val lastModified: Long = System.currentTimeMillis(),
    val minSdk: Int = 21,
    val requiredPermissions: List<String> = emptyList(),
    val estimatedLines: Int = 50,
    val hasPreview: Boolean = true
) {
    /**
     * Kiểm tra xem component có compatible với project context không
     */
    fun isCompatibleWith(context: ProjectContext): Boolean {
        return context.minSdk >= minSdk
    }
    
    /**
     * Lấy customization option theo key
     */
    fun getCustomizationOption(key: String): CustomizationOption? {
        return customizationOptions.find { it.key == key }
    }
    
    /**
     * Kiểm tra xem có tag cụ thể không
     */
    fun hasTag(tag: String): Boolean {
        return tags.contains(tag)
    }
    
    /**
     * Tính relevance score với search query
     */
    fun calculateRelevanceScore(query: String): Float {
        val queryLower = query.lowercase()
        var score = 0f
        
        // Exact name match
        if (name.lowercase() == queryLower) score += 100f
        
        // Name contains query
        if (name.lowercase().contains(queryLower)) score += 50f
        
        // Description contains query
        if (description.lowercase().contains(queryLower)) score += 30f
        
        // Tags contain query
        tags.forEach { tag ->
            if (tag.lowercase().contains(queryLower)) score += 20f
        }
        
        // Use cases contain query
        useCases.forEach { useCase ->
            if (useCase.lowercase().contains(queryLower)) score += 15f
        }
        
        return score
    }
}

/**
 * Các category của components
 */
enum class ComponentCategory(val displayName: String, val icon: String, val description: String) {
    // Core UI Components
    BUTTON("Buttons", "🔘", "Interactive buttons và action triggers"),
    INPUT("Inputs", "📝", "Text fields và form inputs"),
    CARD("Cards", "🃏", "Content containers và display cards"),
    LAYOUT("Layouts", "📐", "Layout containers và positioning"),
    NAVIGATION("Navigation", "🧭", "Navigation components và routing"),
    DIALOG("Dialogs", "💬", "Modal dialogs và overlays"),
    LIST("Lists", "📋", "List displays và data presentation"),
    CHART("Charts", "📊", "Data visualization và charts"),
    MEDIA("Media", "🎬", "Image, video và media components"),
    FORM("Forms", "📄", "Form layouts và validation"),
    
    // Integration Components
    AUTHENTICATION("Auth", "🔐", "Login, signup và authentication"),
    PAYMENT("Payment", "💳", "Payment processing components"),
    SOCIAL("Social", "👥", "Social media integration"),
    CAMERA("Camera", "📷", "Camera và image capture"),
    UTILITY("Utility", "🔧", "Helper components và utilities"),
    
    // Enhanced Components (New)
    ANIMATION("Animation", "✨", "Animations và transitions"),
    ACCESSIBILITY("A11y", "♿", "Accessibility components"),
    GESTURE("Gesture", "👆", "Gesture handling components"),
    FEEDBACK("Feedback", "📳", "User feedback components"),
    AI_ML("AI/ML", "🤖", "AI và Machine Learning components"),
    ADAPTIVE("Adaptive", "📱", "Adaptive và responsive components"),
    ENTERPRISE("Enterprise", "🏢", "Enterprise-grade components"),
    SECURITY("Security", "🔒", "Security và privacy components"),
    TESTING("Testing", "🧪", "Testing utilities và mocks")
}

/**
 * Mức độ phức tạp của component
 */
enum class ComplexityLevel(
    val displayName: String, 
    val estimatedTime: String,
    val description: String,
    val skillLevel: String
) {
    SIMPLE(
        "Simple", 
        "5-10 min",
        "Chỉ UI component, không có logic phức tạp",
        "Beginner"
    ),
    MEDIUM(
        "Medium", 
        "15-30 min",
        "UI + basic logic, có state management",
        "Intermediate"
    ),
    COMPLEX(
        "Complex", 
        "45-60 min",
        "UI + logic + integration, có external dependencies",
        "Advanced"
    ),
    EXPERT(
        "Expert",
        "2+ hours",
        "Phức tạp cao, cần customization sâu",
        "Expert"
    )
}

/**
 * Option để customize component
 */
data class CustomizationOption(
    val key: String,
    val displayName: String,
    val type: OptionType,
    val defaultValue: Any,
    val possibleValues: List<Any>? = null,
    val description: String,
    val required: Boolean = false,
    val group: String? = null,
    val dependsOn: String? = null,
    val validation: ValidationRule? = null
) {
    /**
     * Validate giá trị input
     */
    fun validateValue(value: Any): ValidationResult {
        validation?.let { rule ->
            return rule.validate(value)
        }
        return ValidationResult.Success
    }
    
    /**
     * Kiểm tra xem option có enabled không dựa trên dependencies
     */
    fun isEnabled(currentValues: Map<String, Any>): Boolean {
        dependsOn?.let { dependency ->
            return currentValues[dependency] == true
        }
        return true
    }
}

/**
 * Các loại option type
 */
enum class OptionType {
    COLOR,      // Color picker
    SIZE,       // Size selection (small, medium, large)
    TEXT,       // Text input
    BOOLEAN,    // Toggle switch
    ENUM,       // Dropdown selection
    NUMBER,     // Number input
    ICON,       // Icon picker
    DIMENSION,  // Dimension input (dp, sp)
    FILE,       // File picker
    RANGE       // Range slider
}

/**
 * Validation rule cho customization options
 */
sealed class ValidationRule {
    abstract fun validate(value: Any): ValidationResult
    
    object Required : ValidationRule() {
        override fun validate(value: Any): ValidationResult {
            return if (value.toString().isNotBlank()) {
                ValidationResult.Success
            } else {
                ValidationResult.Error("This field is required")
            }
        }
    }
    
    data class MinLength(val min: Int) : ValidationRule() {
        override fun validate(value: Any): ValidationResult {
            return if (value.toString().length >= min) {
                ValidationResult.Success
            } else {
                ValidationResult.Error("Minimum length is $min characters")
            }
        }
    }
    
    data class Range(val min: Number, val max: Number) : ValidationRule() {
        override fun validate(value: Any): ValidationResult {
            val numValue = value.toString().toDoubleOrNull()
            return if (numValue != null && numValue >= min.toDouble() && numValue <= max.toDouble()) {
                ValidationResult.Success
            } else {
                ValidationResult.Error("Value must be between $min and $max")
            }
        }
    }
}

/**
 * Kết quả validation
 */
sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

/**
 * Project context để customize components
 */
data class ProjectContext(
    val projectName: String,
    val basePackage: String,
    val minSdk: Int,
    val targetSdk: Int,
    val composeVersion: String,
    val materialVersion: String,
    val colorScheme: Map<String, String> = emptyMap(),
    val typography: Map<String, String> = emptyMap(),
    val customTheme: Boolean = false,
    val supportedLanguages: List<String> = listOf("en"),
    val hasDataBinding: Boolean = false,
    val hasViewBinding: Boolean = false,
    val architecturePattern: String = "MVVM"
) {
    /**
     * Generate package name cho component
     */
    fun generateComponentPackage(category: ComponentCategory): String {
        return "$basePackage.components.${category.name.lowercase()}"
    }
    
    /**
     * Kiểm tra compatibility với component
     */
    fun isCompatibleWith(component: ComponentMetadata): Boolean {
        return minSdk >= component.minSdk
    }
}