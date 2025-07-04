# Implementation Plan: Lego Component System

## 🎯 Mục Tiêu Implementation

Triển khai hệ thống component modular cho phép AI tự động select, customize và generate code từ các template blocks có sẵn.

## 📋 Phase 1: Foundation Setup (Week 1-2)

### 1.1 Package Structure Setup

#### Tạo Template Package
```kotlin
// File: app/src/main/java/com/xiaomi/base/templates/TemplatePackage.kt
package com.xiaomi.base.templates

/**
 * Marker interface để đánh dấu template package
 * Package này có thể được xóa sau khi hoàn thành dự án
 */
interface TemplatePackage {
    companion object {
        const val PACKAGE_NAME = "com.xiaomi.base.templates"
        const val VERSION = "1.0.0"
        const val CAN_BE_REMOVED = true
    }
}
```

#### Component Registry Core
```kotlin
// File: app/src/main/java/com/xiaomi/base/templates/registry/ComponentMetadata.kt
package com.xiaomi.base.templates.registry

import androidx.compose.ui.graphics.vector.ImageVector

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
    val lastModified: Long = System.currentTimeMillis()
)

enum class ComponentCategory(val displayName: String, val icon: String) {
    BUTTON("Buttons", "🔘"),
    INPUT("Inputs", "📝"),
    CARD("Cards", "🃏"),
    LAYOUT("Layouts", "📐"),
    NAVIGATION("Navigation", "🧭"),
    DIALOG("Dialogs", "💬"),
    LIST("Lists", "📋"),
    CHART("Charts", "📊"),
    MEDIA("Media", "🎬"),
    FORM("Forms", "📄"),
    AUTHENTICATION("Auth", "🔐"),
    PAYMENT("Payment", "💳"),
    SOCIAL("Social", "👥"),
    CAMERA("Camera", "📷"),
    UTILITY("Utility", "🔧")
}

enum class ComplexityLevel(val displayName: String, val estimatedTime: String) {
    SIMPLE("Simple", "5-10 min"),
    MEDIUM("Medium", "15-30 min"),
    COMPLEX("Complex", "45-60 min")
}

data class CustomizationOption(
    val key: String,
    val displayName: String,
    val type: OptionType,
    val defaultValue: Any,
    val possibleValues: List<Any>? = null,
    val description: String,
    val required: Boolean = false,
    val group: String? = null
)

enum class OptionType {
    COLOR, SIZE, TEXT, BOOLEAN, ENUM, NUMBER, ICON, DIMENSION
}
```

### 1.2 Template Engine Core

```kotlin
// File: app/src/main/java/com/xiaomi/base/templates/engine/TemplateEngine.kt
package com.xiaomi.base.templates.engine

import com.xiaomi.base.templates.registry.ComponentMetadata
import com.xiaomi.base.templates.registry.CustomizationOption

class TemplateEngine {
    
    fun generateComponent(
        metadata: ComponentMetadata,
        customizations: Map<String, Any>,
        projectContext: ProjectContext
    ): GeneratedComponent {
        val templateContent = loadTemplate(metadata.templatePath)
        val processedContent = processTemplate(templateContent, customizations, projectContext)
        
        return GeneratedComponent(
            fileName = generateFileName(metadata, customizations),
            packageName = projectContext.basePackage + ".components." + metadata.category.name.lowercase(),
            sourceCode = processedContent,
            dependencies = resolveDependencies(metadata, customizations),
            imports = generateImports(metadata, customizations, projectContext),
            documentation = generateDocumentation(metadata, customizations)
        )
    }
    
    private fun loadTemplate(templatePath: String): String {
        // Load template từ assets hoặc resources
        return javaClass.classLoader?.getResourceAsStream(templatePath)
            ?.bufferedReader()
            ?.use { it.readText() }
            ?: throw IllegalArgumentException("Template not found: $templatePath")
    }
    
    private fun processTemplate(
        template: String,
        customizations: Map<String, Any>,
        context: ProjectContext
    ): String {
        var processed = template
        
        // Replace basic placeholders
        processed = processed.replace("{{PACKAGE_NAME}}", context.basePackage)
        processed = processed.replace("{{PROJECT_NAME}}", context.projectName)
        
        // Process customizations
        customizations.forEach { (key, value) ->
            processed = processed.replace("{{$key}}", value.toString())
        }
        
        // Process conditional blocks
        processed = processConditionalBlocks(processed, customizations)
        
        // Process loops
        processed = processLoops(processed, customizations)
        
        return processed
    }
    
    private fun processConditionalBlocks(template: String, customizations: Map<String, Any>): String {
        val conditionalRegex = """\{\{#if\s+(\w+)\}\}([\s\S]*?)\{\{/if\}\}""".toRegex()
        
        return conditionalRegex.replace(template) { matchResult ->
            val condition = matchResult.groupValues[1]
            val content = matchResult.groupValues[2]
            
            if (customizations[condition] == true) {
                content
            } else {
                ""
            }
        }
    }
    
    private fun processLoops(template: String, customizations: Map<String, Any>): String {
        val loopRegex = """\{\{#each\s+(\w+)\}\}([\s\S]*?)\{\{/each\}\}""".toRegex()
        
        return loopRegex.replace(template) { matchResult ->
            val listKey = matchResult.groupValues[1]
            val content = matchResult.groupValues[2]
            
            @Suppress("UNCHECKED_CAST")
            val list = customizations[listKey] as? List<Any> ?: emptyList()
            
            list.joinToString("\n") { item ->
                content.replace("{{this}}", item.toString())
            }
        }
    }
}

data class ProjectContext(
    val projectName: String,
    val basePackage: String,
    val minSdk: Int,
    val targetSdk: Int,
    val composeVersion: String,
    val materialVersion: String,
    val colorScheme: Map<String, String>,
    val typography: Map<String, String>,
    val customTheme: Boolean = false
)

data class GeneratedComponent(
    val fileName: String,
    val packageName: String,
    val sourceCode: String,
    val dependencies: List<String>,
    val imports: List<String>,
    val documentation: String,
    val previewCode: String? = null
)
```

### 1.3 Component Registry Implementation

```kotlin
// File: app/src/main/java/com/xiaomi/base/templates/registry/ComponentRegistry.kt
package com.xiaomi.base.templates.registry

object ComponentRegistry {
    private val components = mutableMapOf<String, ComponentMetadata>()
    private val categoryIndex = mutableMapOf<ComponentCategory, MutableList<String>>()
    private val tagIndex = mutableMapOf<String, MutableList<String>>()
    
    init {
        registerDefaultComponents()
    }
    
    fun registerComponent(metadata: ComponentMetadata) {
        components[metadata.id] = metadata
        
        // Update category index
        categoryIndex.getOrPut(metadata.category) { mutableListOf() }.add(metadata.id)
        
        // Update tag index
        metadata.tags.forEach { tag ->
            tagIndex.getOrPut(tag) { mutableListOf() }.add(metadata.id)
        }
    }
    
    fun findComponents(
        category: ComponentCategory? = null,
        tags: List<String> = emptyList(),
        complexity: ComplexityLevel? = null,
        searchQuery: String? = null
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
        
        // Filter by tags
        if (tags.isNotEmpty()) {
            candidates = candidates.filter { component ->
                component.tags.any { it in tags }
            }
        }
        
        // Filter by search query
        searchQuery?.let { query ->
            candidates = candidates.filter { component ->
                component.name.contains(query, true) ||
                component.description.contains(query, true) ||
                component.tags.any { it.contains(query, true) }
            }
        }
        
        return candidates.sortedBy { it.name }
    }
    
    fun getComponent(id: String): ComponentMetadata? = components[id]
    
    fun getAllCategories(): List<ComponentCategory> = ComponentCategory.values().toList()
    
    fun getComponentsByCategory(category: ComponentCategory): List<ComponentMetadata> {
        return categoryIndex[category]?.mapNotNull { components[it] } ?: emptyList()
    }
    
    fun searchByTags(tags: List<String>): List<ComponentMetadata> {
        val componentIds = tags.flatMap { tag ->
            tagIndex[tag] ?: emptyList()
        }.distinct()
        
        return componentIds.mapNotNull { components[it] }
    }
    
    private fun registerDefaultComponents() {
        // Register basic components
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
                tags = listOf("button", "primary", "material3", "interactive", "cta")
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
                tags = listOf("fab", "floating", "action", "material3")
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
                tags = listOf("input", "text", "form", "validation", "material3")
            )
        )
    }
    
    private fun registerBasicCards() {
        registerComponent(
            ComponentMetadata(
                id = "info_card",
                name = "Information Card",
                category = ComponentCategory.CARD,
                description = "Basic card để hiển thị thông tin với optional actions",
                templatePath = "templates/cards/info_card.kt.template",
                dependencies = listOf("androidx.compose.material3:material3"),
                customizationOptions = listOf(
                    CustomizationOption(
                        key = "HAS_IMAGE",
                        displayName = "Include Image",
                        type = OptionType.BOOLEAN,
                        defaultValue = false,
                        description = "Add image support to card"
                    ),
                    CustomizationOption(
                        key = "HAS_ACTIONS",
                        displayName = "Action Buttons",
                        type = OptionType.BOOLEAN,
                        defaultValue = true,
                        description = "Include action buttons"
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
                tags = listOf("card", "info", "display", "material3")
            )
        )
    }
    
    private fun registerBasicLayouts() {
        registerComponent(
            ComponentMetadata(
                id = "responsive_grid",
                name = "Responsive Grid Layout",
                category = ComponentCategory.LAYOUT,
                description = "Auto-adjusting grid layout responsive với screen size",
                templatePath = "templates/layouts/responsive_grid.kt.template",
                dependencies = listOf(
                    "androidx.compose.foundation:foundation",
                    "androidx.compose.foundation:foundation-layout"
                ),
                customizationOptions = listOf(
                    CustomizationOption(
                        key = "MIN_ITEM_WIDTH",
                        displayName = "Minimum Item Width",
                        type = OptionType.DIMENSION,
                        defaultValue = "150.dp",
                        description = "Minimum width for grid items"
                    ),
                    CustomizationOption(
                        key = "SPACING",
                        displayName = "Grid Spacing",
                        type = OptionType.DIMENSION,
                        defaultValue = "8.dp",
                        description = "Spacing between grid items"
                    ),
                    CustomizationOption(
                        key = "HAS_HEADER",
                        displayName = "Include Header",
                        type = OptionType.BOOLEAN,
                        defaultValue = false,
                        description = "Add header section to grid"
                    )
                ),
                complexity = ComplexityLevel.MEDIUM,
                tags = listOf("layout", "grid", "responsive", "adaptive")
            )
        )
    }
}
```

## 📝 Phase 2: Template Files Creation

### 2.1 Button Templates

#### Primary Button Template
```kotlin
// File: app/src/main/assets/templates/buttons/primary_button.kt.template
package {{PACKAGE_NAME}}

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * {{COMPONENT_NAME}} - Material 3 Primary Button
 * 
 * Generated by Lego Component System
 * Customizations applied:
{{#each CUSTOMIZATIONS}}
 * - {{this}}
{{/each}}
 */
@Composable
fun {{COMPONENT_NAME}}(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
{{#if HAS_ICON}}
    icon: ImageVector? = null,
{{/if}}
{{#if HAS_LOADING}}
    isLoading: Boolean = false,
{{/if}}
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
{{#if HAS_LOADING}}
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        colors = colors
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = colors.contentColor,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
{{#if HAS_ICON}}
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
{{/if}}
                Text(text = text)
            }
        }
    }
{{else}}
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
{{#if HAS_ICON}}
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
{{/if}}
            Text(text = text)
        }
    }
{{/if}}
}

@Preview(showBackground = true)
@Composable
fun {{COMPONENT_NAME}}Preview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            {{COMPONENT_NAME}}(
                text = "Primary Button",
                onClick = {}
            )
            
{{#if HAS_ICON}}
            {{COMPONENT_NAME}}(
                text = "With Icon",
                icon = Icons.Default.Add,
                onClick = {}
            )
{{/if}}
            
{{#if HAS_LOADING}}
            {{COMPONENT_NAME}}(
                text = "Loading",
                isLoading = true,
                onClick = {}
            )
{{/if}}
            
            {{COMPONENT_NAME}}(
                text = "Disabled",
                enabled = false,
                onClick = {}
            )
        }
    }
}
```

### 2.2 Input Templates

#### Text Input Template
```kotlin
// File: app/src/main/assets/templates/inputs/text_input.kt.template
package {{PACKAGE_NAME}}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * {{COMPONENT_NAME}} - Material 3 Text Input Field
 * 
 * Generated by Lego Component System
 * Input Type: {{INPUT_TYPE}}
{{#if HAS_VALIDATION}}
 * Includes validation support
{{/if}}
{{#if HAS_COUNTER}}
 * Includes character counter
{{/if}}
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun {{COMPONENT_NAME}}(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
{{#if HAS_VALIDATION}}
    isError: Boolean = false,
    errorMessage: String? = null,
    validator: ((String) -> String?)? = null,
{{/if}}
{{#if HAS_COUNTER}}
    maxLength: Int? = null,
{{/if}}
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = {{#if INPUT_TYPE}}KeyboardType.{{INPUT_TYPE}}{{else}}KeyboardType.Text{{/if}},
        imeAction = ImeAction.Next
    )
) {
    val focusManager = LocalFocusManager.current
{{#if HAS_VALIDATION}}
    var validationError by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(value) {
        validationError = validator?.invoke(value)
    }
    
    val hasError = isError || validationError != null
    val displayErrorMessage = errorMessage ?: validationError
{{/if}}

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
{{#if HAS_COUNTER}}
                if (maxLength == null || newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
{{else}}
                onValueChange(newValue)
{{/if}}
            },
            label = { Text(label) },
            placeholder = placeholder?.let { { Text(it) } },
            enabled = enabled,
            readOnly = readOnly,
{{#if HAS_VALIDATION}}
            isError = hasError,
{{/if}}
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
{{#if HAS_COUNTER}}
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
{{#if HAS_VALIDATION}}
                    if (hasError && displayErrorMessage != null) {
                        Text(
                            text = displayErrorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
{{else}}
                    Spacer(modifier = Modifier.weight(1f))
{{/if}}
                    maxLength?.let {
                        Text(
                            text = "${value.length}/$it",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (value.length > it * 0.9) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            },
{{else}}
{{#if HAS_VALIDATION}}
            supportingText = if (hasError && displayErrorMessage != null) {
                {
                    Text(
                        text = displayErrorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else null,
{{/if}}
{{/if}}
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun {{COMPONENT_NAME}}Preview() {
    var text by remember { mutableStateOf("") }
    
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            {{COMPONENT_NAME}}(
                value = text,
                onValueChange = { text = it },
                label = "{{INPUT_TYPE}} Input",
                placeholder = "Enter your {{INPUT_TYPE}}..."
{{#if HAS_VALIDATION}}
                ,
                validator = { value ->
                    when {
{{#if INPUT_TYPE}}
{{#if (eq INPUT_TYPE "EMAIL")}}
                        value.isNotEmpty() && !value.contains("@") -> "Invalid email format"
{{/if}}
{{#if (eq INPUT_TYPE "PHONE")}}
                        value.isNotEmpty() && value.length < 10 -> "Phone number too short"
{{/if}}
{{/if}}
                        value.isEmpty() -> "This field is required"
                        else -> null
                    }
                }
{{/if}}
{{#if HAS_COUNTER}}
                ,
                maxLength = 50
{{/if}}
            )
        }
    }
}
```

## 🤖 Phase 3: AI Integration

### 3.1 Component Selector AI

```kotlin
// File: app/src/main/java/com/xiaomi/base/templates/ai/ComponentSelector.kt
package com.xiaomi.base.templates.ai

import com.xiaomi.base.templates.registry.ComponentCategory
import com.xiaomi.base.templates.registry.ComponentMetadata
import com.xiaomi.base.templates.registry.ComponentRegistry
import com.xiaomi.base.templates.registry.ComplexityLevel

class ComponentSelector {
    
    fun analyzeRequirement(requirement: String): RequirementAnalysis {
        val normalizedReq = requirement.lowercase().trim()
        
        // Extract intent từ requirement
        val intent = extractIntent(normalizedReq)
        
        // Extract entities (UI elements mentioned)
        val entities = extractEntities(normalizedReq)
        
        // Determine complexity
        val complexity = determineComplexity(normalizedReq, entities)
        
        // Extract context clues
        val context = extractContext(normalizedReq)
        
        return RequirementAnalysis(
            originalRequirement = requirement,
            intent = intent,
            entities = entities,
            complexity = complexity,
            context = context,
            confidence = calculateConfidence(intent, entities)
        )
    }
    
    fun selectComponents(analysis: RequirementAnalysis): List<ComponentRecommendation> {
        val recommendations = mutableListOf<ComponentRecommendation>()
        
        // Map entities to component categories
        analysis.entities.forEach { entity ->
            val category = mapEntityToCategory(entity)
            val components = ComponentRegistry.findComponents(category = category)
            
            components.forEach { component ->
                val relevance = calculateRelevance(component, entity, analysis)
                if (relevance > 0.3) { // Threshold for relevance
                    recommendations.add(
                        ComponentRecommendation(
                            component = component,
                            relevanceScore = relevance,
                            reasoningExplanation = generateReasoning(component, entity, analysis),
                            suggestedCustomizations = suggestCustomizations(component, analysis)
                        )
                    )
                }
            }
        }
        
        // Sort by relevance and return top recommendations
        return recommendations
            .sortedByDescending { it.relevanceScore }
            .take(10)
    }
    
    private fun extractIntent(requirement: String): Intent {
        return when {
            requirement.contains("login") || requirement.contains("sign in") -> Intent.AUTHENTICATION
            requirement.contains("list") || requirement.contains("display") -> Intent.DISPLAY_DATA
            requirement.contains("form") || requirement.contains("input") -> Intent.DATA_INPUT
            requirement.contains("navigation") || requirement.contains("menu") -> Intent.NAVIGATION
            requirement.contains("payment") || requirement.contains("checkout") -> Intent.PAYMENT
            requirement.contains("camera") || requirement.contains("photo") -> Intent.MEDIA_CAPTURE
            requirement.contains("search") -> Intent.SEARCH
            requirement.contains("profile") || requirement.contains("settings") -> Intent.USER_MANAGEMENT
            else -> Intent.GENERAL_UI
        }
    }
    
    private fun extractEntities(requirement: String): List<UIEntity> {
        val entities = mutableListOf<UIEntity>()
        val words = requirement.lowercase().split(" ", ",", ".", ";", ":")
        
        // UI element keywords mapping
        val entityMap = mapOf(
            listOf("button", "btn", "click", "tap") to UIEntity.BUTTON,
            listOf("input", "field", "textbox", "text") to UIEntity.INPUT_FIELD,
            listOf("card", "item", "tile") to UIEntity.CARD,
            listOf("list", "grid", "collection") to UIEntity.LIST,
            listOf("dialog", "popup", "modal") to UIEntity.DIALOG,
            listOf("navigation", "nav", "menu", "drawer") to UIEntity.NAVIGATION,
            listOf("image", "photo", "picture") to UIEntity.IMAGE,
            listOf("chart", "graph", "visualization") to UIEntity.CHART,
            listOf("form", "registration", "signup") to UIEntity.FORM
        )
        
        entityMap.forEach { (keywords, entity) ->
            if (keywords.any { keyword -> words.contains(keyword) }) {
                entities.add(entity)
            }
        }
        
        return entities.distinct()
    }
    
    private fun mapEntityToCategory(entity: UIEntity): ComponentCategory? {
        return when (entity) {
            UIEntity.BUTTON -> ComponentCategory.BUTTON
            UIEntity.INPUT_FIELD -> ComponentCategory.INPUT
            UIEntity.CARD -> ComponentCategory.CARD
            UIEntity.LIST -> ComponentCategory.LIST
            UIEntity.DIALOG -> ComponentCategory.DIALOG
            UIEntity.NAVIGATION -> ComponentCategory.NAVIGATION
            UIEntity.IMAGE -> ComponentCategory.MEDIA
            UIEntity.CHART -> ComponentCategory.CHART
            UIEntity.FORM -> ComponentCategory.FORM
            else -> null
        }
    }
    
    private fun calculateRelevance(
        component: ComponentMetadata,
        entity: UIEntity,
        analysis: RequirementAnalysis
    ): Float {
        var score = 0f
        
        // Base score for category match
        if (mapEntityToCategory(entity) == component.category) {
            score += 0.5f
        }
        
        // Tag matching
        val reqWords = analysis.originalRequirement.lowercase().split(" ")
        val tagMatches = component.tags.count { tag ->
            reqWords.any { word -> word.contains(tag) || tag.contains(word) }
        }
        score += (tagMatches * 0.1f)
        
        // Complexity matching
        when (analysis.complexity) {
            ComplexityLevel.SIMPLE -> if (component.complexity == ComplexityLevel.SIMPLE) score += 0.2f
            ComplexityLevel.MEDIUM -> if (component.complexity <= ComplexityLevel.MEDIUM) score += 0.2f
            ComplexityLevel.COMPLEX -> score += 0.1f // Any complexity is fine for complex requirements
        }
        
        // Intent matching
        score += calculateIntentMatch(component, analysis.intent)
        
        return score.coerceIn(0f, 1f)
    }
    
    private fun calculateIntentMatch(component: ComponentMetadata, intent: Intent): Float {
        return when (intent) {
            Intent.AUTHENTICATION -> {
                if (component.tags.any { it in listOf("login", "auth", "password", "signin") }) 0.3f else 0f
            }
            Intent.DATA_INPUT -> {
                if (component.category == ComponentCategory.INPUT || component.category == ComponentCategory.FORM) 0.3f else 0f
            }
            Intent.DISPLAY_DATA -> {
                if (component.category in listOf(ComponentCategory.CARD, ComponentCategory.LIST)) 0.3f else 0f
            }
            Intent.NAVIGATION -> {
                if (component.category == ComponentCategory.NAVIGATION) 0.3f else 0f
            }
            Intent.PAYMENT -> {
                if (component.tags.contains("payment")) 0.3f else 0f
            }
            else -> 0.1f
        }
    }
}

data class RequirementAnalysis(
    val originalRequirement: String,
    val intent: Intent,
    val entities: List<UIEntity>,
    val complexity: ComplexityLevel,
    val context: Map<String, String>,
    val confidence: Float
)

enum class Intent {
    AUTHENTICATION, DATA_INPUT, DISPLAY_DATA, NAVIGATION, 
    PAYMENT, MEDIA_CAPTURE, SEARCH, USER_MANAGEMENT, GENERAL_UI
}

enum class UIEntity {
    BUTTON, INPUT_FIELD, CARD, LIST, DIALOG, NAVIGATION, 
    IMAGE, CHART, FORM, LAYOUT, MEDIA
}

data class ComponentRecommendation(
    val component: ComponentMetadata,
    val relevanceScore: Float,
    val reasoningExplanation: String,
    val suggestedCustomizations: Map<String, Any>
)
```

## 🚀 Implementation Timeline

### Week 1: Foundation
- [ ] Setup package structure
- [ ] Implement ComponentMetadata và enums
- [ ] Create basic ComponentRegistry
- [ ] Setup template engine core

### Week 2: Basic Templates
- [ ] Create 3 button templates
- [ ] Create 2 input templates
- [ ] Create 2 card templates
- [ ] Test template generation

### Week 3: AI Integration
- [ ] Implement ComponentSelector
- [ ] Add requirement analysis
- [ ] Create recommendation system
- [ ] Test AI component selection

### Week 4: Advanced Features
- [ ] Add more complex templates
- [ ] Implement customization engine
- [ ] Add preview generation
- [ ] Create cleanup utilities

### Week 5: Testing & Polish
- [ ] Comprehensive testing
- [ ] Performance optimization
- [ ] Documentation
- [ ] Final integration

## 📊 Success Metrics

- **Template Coverage**: 80% of common UI patterns
- **AI Accuracy**: 85% correct component selection
- **Generation Speed**: <2 seconds per component
- **Code Quality**: 90% generated code requires no modification
- **Developer Satisfaction**: 4.5/5 rating

---

**Next Steps**: Bắt đầu với Phase 1 implementation và tạo foundation cho hệ thống Lego Component.