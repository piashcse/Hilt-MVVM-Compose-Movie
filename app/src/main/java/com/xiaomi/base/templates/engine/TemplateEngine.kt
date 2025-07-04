package com.xiaomi.base.templates.engine

import com.xiaomi.base.templates.registry.ComponentMetadata
import com.xiaomi.base.templates.registry.ProjectContext
import java.io.InputStream

/**
 * Core template engine để process và generate components từ templates
 * Hỗ trợ placeholder replacement, conditional blocks, và loops
 */
class TemplateEngine {
    
    companion object {
        private const val PLACEHOLDER_REGEX = "\\{\\{([^}]+)\\}\\}"
        private const val CONDITIONAL_REGEX = "\\{\\{#if\\s+(\\w+)\\}\\}([\\s\\S]*?)\\{\\{/if\\}\\}"
        private const val LOOP_REGEX = "\\{\\{#each\\s+(\\w+)\\}\\}([\\s\\S]*?)\\{\\{/each\\}\\}"
        private const val UNLESS_REGEX = "\\{\\{#unless\\s+(\\w+)\\}\\}([\\s\\S]*?)\\{\\{/unless\\}\\}"
    }
    
    /**
     * Generate component từ metadata và customizations
     */
    fun generateComponent(
        metadata: ComponentMetadata,
        customizations: Map<String, Any>,
        projectContext: ProjectContext
    ): GeneratedComponent {
        try {
            val templateContent = loadTemplate(metadata.templatePath)
            val processedContent = processTemplate(templateContent, customizations, projectContext, metadata)
            
            return GeneratedComponent(
                fileName = generateFileName(metadata, customizations),
                packageName = projectContext.generateComponentPackage(metadata.category),
                sourceCode = processedContent,
                dependencies = resolveDependencies(metadata, customizations),
                imports = generateImports(metadata, customizations, projectContext),
                documentation = generateDocumentation(metadata, customizations),
                previewCode = generatePreviewCode(metadata, customizations, projectContext)
            )
        } catch (e: Exception) {
            throw TemplateProcessingException("Failed to generate component ${metadata.id}", e)
        }
    }
    
    /**
     * Load template từ assets hoặc resources
     */
    private fun loadTemplate(templatePath: String): String {
        return try {
            // Try to load from assets first
            val inputStream: InputStream? = javaClass.classLoader?.getResourceAsStream("assets/$templatePath")
                ?: javaClass.classLoader?.getResourceAsStream(templatePath)
            
            inputStream?.bufferedReader()?.use { it.readText() }
                ?: throw TemplateNotFoundException("Template not found: $templatePath")
        } catch (e: Exception) {
            // Fallback to default template if specific template not found
            getDefaultTemplate(templatePath)
        }
    }
    
    /**
     * Process template với customizations
     */
    private fun processTemplate(
        template: String,
        customizations: Map<String, Any>,
        context: ProjectContext,
        metadata: ComponentMetadata
    ): String {
        var processed = template
        
        // Replace basic project placeholders
        processed = replaceProjectPlaceholders(processed, context)
        
        // Replace component-specific placeholders
        processed = replaceComponentPlaceholders(processed, metadata, customizations)
        
        // Process conditional blocks
        processed = processConditionalBlocks(processed, customizations)
        
        // Process unless blocks
        processed = processUnlessBlocks(processed, customizations)
        
        // Process loops
        processed = processLoops(processed, customizations)
        
        // Replace remaining customization placeholders
        processed = replaceCustomizationPlaceholders(processed, customizations)
        
        // Clean up any remaining placeholders
        processed = cleanupPlaceholders(processed)
        
        return processed
    }
    
    /**
     * Replace project-level placeholders
     */
    private fun replaceProjectPlaceholders(template: String, context: ProjectContext): String {
        return template
            .replace("{{PACKAGE_NAME}}", context.basePackage)
            .replace("{{PROJECT_NAME}}", context.projectName)
            .replace("{{MIN_SDK}}", context.minSdk.toString())
            .replace("{{TARGET_SDK}}", context.targetSdk.toString())
            .replace("{{COMPOSE_VERSION}}", context.composeVersion)
            .replace("{{MATERIAL_VERSION}}", context.materialVersion)
    }
    
    /**
     * Replace component-specific placeholders
     */
    private fun replaceComponentPlaceholders(
        template: String,
        metadata: ComponentMetadata,
        customizations: Map<String, Any>
    ): String {
        val componentName = customizations["COMPONENT_NAME"] as? String 
            ?: generateComponentName(metadata.name)
        
        return template
            .replace("{{COMPONENT_NAME}}", componentName)
            .replace("{{COMPONENT_ID}}", metadata.id)
            .replace("{{COMPONENT_DESCRIPTION}}", metadata.description)
            .replace("{{COMPONENT_VERSION}}", metadata.version)
    }
    
    /**
     * Process conditional blocks {{#if condition}}...{{/if}}
     */
    private fun processConditionalBlocks(template: String, customizations: Map<String, Any>): String {
        val conditionalRegex = CONDITIONAL_REGEX.toRegex()
        
        return conditionalRegex.replace(template) { matchResult ->
            val condition = matchResult.groupValues[1]
            val content = matchResult.groupValues[2]
            
            if (evaluateCondition(condition, customizations)) {
                content
            } else {
                ""
            }
        }
    }
    
    /**
     * Process unless blocks {{#unless condition}}...{{/unless}}
     */
    private fun processUnlessBlocks(template: String, customizations: Map<String, Any>): String {
        val unlessRegex = UNLESS_REGEX.toRegex()
        
        return unlessRegex.replace(template) { matchResult ->
            val condition = matchResult.groupValues[1]
            val content = matchResult.groupValues[2]
            
            if (!evaluateCondition(condition, customizations)) {
                content
            } else {
                ""
            }
        }
    }
    
    /**
     * Process loops {{#each items}}...{{/each}}
     */
    private fun processLoops(template: String, customizations: Map<String, Any>): String {
        val loopRegex = LOOP_REGEX.toRegex()
        
        return loopRegex.replace(template) { matchResult ->
            val listKey = matchResult.groupValues[1]
            val content = matchResult.groupValues[2]
            
            @Suppress("UNCHECKED_CAST")
            val list = customizations[listKey] as? List<Any> ?: emptyList()
            
            list.mapIndexed { index, item ->
                content
                    .replace("{{this}}", item.toString())
                    .replace("{{@index}}", index.toString())
                    .replace("{{@first}}", (index == 0).toString())
                    .replace("{{@last}}", (index == list.size - 1).toString())
            }.joinToString("\n")
        }
    }
    
    /**
     * Replace customization placeholders
     */
    private fun replaceCustomizationPlaceholders(
        template: String,
        customizations: Map<String, Any>
    ): String {
        var processed = template
        
        customizations.forEach { (key, value) ->
            processed = processed.replace("{{$key}}", value.toString())
        }
        
        return processed
    }
    
    /**
     * Evaluate condition cho conditional blocks
     */
    private fun evaluateCondition(condition: String, customizations: Map<String, Any>): Boolean {
        return when (val value = customizations[condition]) {
            is Boolean -> value
            is String -> value.isNotBlank() && value != "false"
            is Number -> value.toDouble() != 0.0
            null -> false
            else -> true
        }
    }
    
    /**
     * Clean up any remaining placeholders
     */
    private fun cleanupPlaceholders(template: String): String {
        // Remove any remaining placeholders that weren't replaced
        return template.replace(PLACEHOLDER_REGEX.toRegex()) { matchResult ->
            val placeholder = matchResult.groupValues[1]
            // Log warning about unused placeholder
            println("Warning: Unused placeholder {{$placeholder}}")
            ""
        }
    }
    
    /**
     * Generate file name cho component
     */
    private fun generateFileName(metadata: ComponentMetadata, customizations: Map<String, Any>): String {
        val componentName = customizations["COMPONENT_NAME"] as? String 
            ?: generateComponentName(metadata.name)
        return "$componentName.kt"
    }
    
    /**
     * Generate component name từ display name
     */
    private fun generateComponentName(displayName: String): String {
        return displayName
            .split(" ")
            .joinToString("") { word -> 
                word.replaceFirstChar { it.uppercase() }
            }
            .replace(Regex("[^a-zA-Z0-9]"), "")
    }
    
    /**
     * Resolve dependencies cho component
     */
    private fun resolveDependencies(
        metadata: ComponentMetadata,
        customizations: Map<String, Any>
    ): List<String> {
        val baseDependencies = metadata.dependencies.toMutableList()
        
        // Add conditional dependencies based on customizations
        if (customizations["HAS_ICON"] == true) {
            baseDependencies.add("androidx.compose.material:material-icons-extended")
        }
        
        if (customizations["HAS_ANIMATION"] == true) {
            baseDependencies.add("androidx.compose.animation:animation")
        }
        
        if (customizations["HAS_VALIDATION"] == true) {
            baseDependencies.add("androidx.compose.runtime:runtime")
        }
        
        return baseDependencies.distinct()
    }
    
    /**
     * Generate imports cho component
     */
    private fun generateImports(
        metadata: ComponentMetadata,
        customizations: Map<String, Any>,
        context: ProjectContext
    ): List<String> {
        val imports = mutableSetOf<String>()
        
        // Base Compose imports
        imports.add("androidx.compose.runtime.Composable")
        imports.add("androidx.compose.ui.Modifier")
        
        // Category-specific imports
        when (metadata.category) {
            com.xiaomi.base.templates.registry.ComponentCategory.BUTTON -> {
                imports.add("androidx.compose.material3.Button")
                imports.add("androidx.compose.material3.ButtonDefaults")
                if (customizations["HAS_ICON"] == true) {
                    imports.add("androidx.compose.material.icons.Icons")
                    imports.add("androidx.compose.material3.Icon")
                }
            }
            com.xiaomi.base.templates.registry.ComponentCategory.INPUT -> {
                imports.add("androidx.compose.material3.TextField")
                imports.add("androidx.compose.material3.OutlinedTextField")
                imports.add("androidx.compose.runtime.mutableStateOf")
                imports.add("androidx.compose.runtime.remember")
            }
            com.xiaomi.base.templates.registry.ComponentCategory.CARD -> {
                imports.add("androidx.compose.material3.Card")
                imports.add("androidx.compose.material3.CardDefaults")
            }
            else -> {}
        }
        
        // Conditional imports
        if (customizations["HAS_LOADING"] == true) {
            imports.add("androidx.compose.material3.CircularProgressIndicator")
        }
        
        if (customizations["HAS_VALIDATION"] == true) {
            imports.add("androidx.compose.runtime.getValue")
            imports.add("androidx.compose.runtime.setValue")
        }
        
        return imports.sorted()
    }
    
    /**
     * Generate documentation cho component
     */
    private fun generateDocumentation(
        metadata: ComponentMetadata,
        customizations: Map<String, Any>
    ): String {
        val componentName = customizations["COMPONENT_NAME"] as? String 
            ?: generateComponentName(metadata.name)
        
        return buildString {
            appendLine("/**")
            appendLine(" * ${metadata.description}")
            appendLine(" *")
            appendLine(" * Generated from template: ${metadata.templatePath}")
            appendLine(" * Component ID: ${metadata.id}")
            appendLine(" * Category: ${metadata.category.displayName}")
            appendLine(" * Complexity: ${metadata.complexity.displayName}")
            appendLine(" *")
            
            if (customizations.isNotEmpty()) {
                appendLine(" * Customizations applied:")
                customizations.forEach { (key, value) ->
                    appendLine(" * - $key: $value")
                }
                appendLine(" *")
            }
            
            appendLine(" * @param modifier Modifier to be applied to the component")
            appendLine(" */")
        }
    }
    
    /**
     * Generate preview code cho component
     */
    private fun generatePreviewCode(
        metadata: ComponentMetadata,
        customizations: Map<String, Any>,
        context: ProjectContext
    ): String {
        val componentName = customizations["COMPONENT_NAME"] as? String 
            ?: generateComponentName(metadata.name)
        
        return buildString {
            appendLine("@Preview")
            appendLine("@Composable")
            appendLine("fun ${componentName}Preview() {")
            appendLine("    MaterialTheme {")
            appendLine("        $componentName(")
            appendLine("            // Add preview parameters here")
            appendLine("        )")
            appendLine("    }")
            appendLine("}")
        }
    }
    
    /**
     * Get default template nếu specific template không tìm thấy
     */
    private fun getDefaultTemplate(templatePath: String): String {
        return when {
            templatePath.contains("button") -> getDefaultButtonTemplate()
            templatePath.contains("input") -> getDefaultInputTemplate()
            templatePath.contains("card") -> getDefaultCardTemplate()
            else -> getGenericTemplate()
        }
    }
    
    private fun getDefaultButtonTemplate(): String {
        return """
@Composable
fun {{COMPONENT_NAME}}(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text = text)
    }
}
        """.trimIndent()
    }
    
    private fun getDefaultInputTemplate(): String {
        return """
@Composable
fun {{COMPONENT_NAME}}(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { Text(it) } }
    )
}
        """.trimIndent()
    }
    
    private fun getDefaultCardTemplate(): String {
        return """
@Composable
fun {{COMPONENT_NAME}}(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
    ) {
        content()
    }
}
        """.trimIndent()
    }
    
    private fun getGenericTemplate(): String {
        return """
@Composable
fun {{COMPONENT_NAME}}(
    modifier: Modifier = Modifier
) {
    // TODO: Implement {{COMPONENT_NAME}}
    Text(
        text = "{{COMPONENT_NAME}} - Implementation needed",
        modifier = modifier
    )
}
        """.trimIndent()
    }
}

/**
 * Generated component result
 */
data class GeneratedComponent(
    val fileName: String,
    val packageName: String,
    val sourceCode: String,
    val dependencies: List<String>,
    val imports: List<String>,
    val documentation: String,
    val previewCode: String? = null,
    val estimatedLines: Int = sourceCode.lines().size,
    val generatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Get full source code với imports và package declaration
     */
    fun getFullSourceCode(): String {
        return buildString {
            appendLine("package $packageName")
            appendLine()
            
            // Add imports
            imports.forEach { import ->
                appendLine("import $import")
            }
            appendLine()
            
            // Add documentation
            appendLine(documentation)
            
            // Add source code
            appendLine(sourceCode)
            
            // Add preview if available
            previewCode?.let {
                appendLine()
                appendLine(it)
            }
        }
    }
    
    /**
     * Validate generated code
     */
    fun validate(): List<String> {
        val issues = mutableListOf<String>()
        
        if (sourceCode.isBlank()) {
            issues.add("Source code is empty")
        }
        
        if (!sourceCode.contains("@Composable")) {
            issues.add("Missing @Composable annotation")
        }
        
        if (packageName.isBlank()) {
            issues.add("Package name is empty")
        }
        
        return issues
    }
}

/**
 * Exceptions cho template processing
 */
class TemplateProcessingException(message: String, cause: Throwable? = null) : Exception(message, cause)
class TemplateNotFoundException(message: String) : Exception(message)