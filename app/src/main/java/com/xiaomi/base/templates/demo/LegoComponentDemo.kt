package com.xiaomi.base.templates.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.templates.ai.ComponentSelector
import com.xiaomi.base.templates.ai.TemplateCustomizer
import com.xiaomi.base.templates.engine.TemplateEngine
import com.xiaomi.base.templates.registry.ComponentCategory
import com.xiaomi.base.templates.registry.ComponentMetadata
import com.xiaomi.base.templates.registry.ComponentRegistry
import com.xiaomi.base.templates.registry.ComplexityLevel
import com.xiaomi.base.templates.registry.CustomizationOption
import com.xiaomi.base.templates.registry.OptionType
import com.xiaomi.base.templates.registry.ProjectContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Demo application để showcase Lego Component System
 */
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun LegoComponentDemo() {
    val context = LocalContext.current
    
    // Initialize components
    val registry = ComponentRegistry
    val selector = remember { ComponentSelector(registry) }
    val customizer = remember { TemplateCustomizer() }
    val engine = remember { TemplateEngine() }
    
    // Demo state
    var userRequirement by remember { mutableStateOf("I want a blue submit button with icon") }
    var isProcessing by remember { mutableStateOf(false) }
    var suggestionResult by remember { mutableStateOf<String?>(null) }
    var generatedCode by remember { mutableStateOf<String?>(null) }
    
    // Initialize demo data
    LaunchedEffect(Unit) {
        initializeDemoData(registry)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lego Component System Demo") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "AI-Powered Component Generation",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "Describe what component you want to create, and our AI will suggest and generate it for you.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Input Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Describe Your Component",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    OutlinedTextField(
                        value = userRequirement,
                        onValueChange = { userRequirement = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Component Description") },
                        placeholder = { Text("e.g., 'I want a red login button with loading state'") },
                        maxLines = 3
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (userRequirement.isNotBlank()) {
                                    isProcessing = true
                                    // Simulate AI processing
                                    CoroutineScope(Dispatchers.Main).launch {
                                        processUserRequirement(
                                            requirement = userRequirement,
                                            selector = selector,
                                            customizer = customizer,
                                            engine = engine,
                                            onSuggestion = { suggestionResult = it },
                                            onGenerated = { generatedCode = it },
                                            onComplete = { isProcessing = false }
                                        )
                                    }
                                }
                            },
                            enabled = !isProcessing && userRequirement.isNotBlank(),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isProcessing) "Processing..." else "Generate Component")
                        }
                        
                        Button(
                            onClick = {
                                userRequirement = ""
                                suggestionResult = null
                                generatedCode = null
                            },
                            enabled = !isProcessing
                        ) {
                            Text("Clear")
                        }
                    }
                }
            }
            
            // Example Prompts
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Example Prompts",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    val examples = listOf(
                        "Create a primary button with icon and loading state",
                        "I need an email input field with validation",
                        "Make a password field with visibility toggle",
                        "Create a large red submit button",
                        "I want a multiline text area for comments"
                    )
                    
                    examples.forEach { example ->
                        Button(
                            onClick = { userRequirement = example },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = example,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            
            // AI Suggestion Result
            suggestionResult?.let { suggestion ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "AI Analysis Result",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Text(
                            text = suggestion,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            
            // Generated Code
            generatedCode?.let { code ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Generated Component Code",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Text(
                            text = code,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                }
            }
            
            // System Stats
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "System Statistics",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    val totalComponents = registry.findComponents().size
                    val categoriesCount = ComponentCategory.values().size
                    Text("Total Components: $totalComponents")
                    Text("Categories: $categoriesCount")
                    Text("Average Complexity: Medium")
                }
            }
        }
    }
}

/**
 * Initialize demo data
 */
private fun initializeDemoData(registry: ComponentRegistry) {
    // Register Primary Button
    val primaryButtonMetadata = ComponentMetadata(
        id = "primary_button",
        name = "Primary Button",
        description = "A customizable primary button component",
        category = ComponentCategory.BUTTON,
        complexity = ComplexityLevel.SIMPLE,
        version = "1.0.0",
        templatePath = "templates/button/primary_button.kt.template",
        tags = listOf("button", "primary", "action", "clickable"),
        dependencies = listOf("androidx.compose.material3:material3"),
        customizationOptions = listOf(
            CustomizationOption(
                key = "TEXT",
                displayName = "Button Text",
                description = "The text displayed on the button",
                type = OptionType.TEXT,
                defaultValue = "Click Me",
                required = true
            ),
            CustomizationOption(
                key = "PRIMARY_COLOR",
                displayName = "Primary Color",
                description = "The primary color of the button",
                type = OptionType.COLOR,
                defaultValue = "#6200EE",
                required = false
            ),
            CustomizationOption(
                key = "HAS_ICON",
                displayName = "Show Icon",
                description = "Whether to display an icon",
                type = OptionType.BOOLEAN,
                defaultValue = false,
                required = false
            )
        )
    )
    
    // Register Text Field
    val textFieldMetadata = ComponentMetadata(
        id = "text_field",
        name = "Text Field",
        description = "A customizable text input field",
        category = ComponentCategory.INPUT,
        complexity = ComplexityLevel.MEDIUM,
        version = "1.0.0",
        templatePath = "templates/input/text_field.kt.template",
        tags = listOf("input", "textfield", "form", "validation"),
        dependencies = listOf("androidx.compose.material3:material3"),
        customizationOptions = listOf(
            CustomizationOption(
                key = "LABEL",
                displayName = "Field Label",
                description = "The label text for the input field",
                type = OptionType.TEXT,
                defaultValue = "Enter text",
                required = false
            ),
            CustomizationOption(
                key = "INPUT_TYPE",
                displayName = "Input Type",
                description = "The type of input",
                type = OptionType.ENUM,
                defaultValue = "text",
                possibleValues = listOf("text", "password", "email", "number"),
                required = false
            )
        )
    )
    
    registry.registerComponent(primaryButtonMetadata)
    registry.registerComponent(textFieldMetadata)
}

/**
 * Process user requirement và generate component
 */
private suspend fun processUserRequirement(
    requirement: String,
    selector: ComponentSelector,
    customizer: TemplateCustomizer,
    engine: TemplateEngine,
    onSuggestion: (String) -> Unit,
    onGenerated: (String) -> Unit,
    onComplete: () -> Unit
) {
    // Simulate async processing
    withContext(Dispatchers.IO) {
        try {
            delay(1000) // Simulate AI processing time
            
            val projectContext = ProjectContext(
                projectName = "Demo Project",
                basePackage = "com.example.demo",
                minSdk = 24,
                targetSdk = 34,
                composeVersion = "1.5.0",
                materialVersion = "1.1.0",
                colorScheme = mapOf(
                    "primary" to "#6200EE",
                    "secondary" to "#03DAC6"
                ),
                typography = mapOf(
                    "h1" to "32sp",
                    "body1" to "16sp"
                ),
                customTheme = false,
                supportedLanguages = listOf("en"),
                hasDataBinding = false,
                hasViewBinding = false,
                architecturePattern = "MVVM"
            )
            
            // Step 1: Analyze requirement
            val suggestion = selector.analyzeRequirement(requirement, projectContext)
            
            val suggestionText = buildString {
                appendLine("🎯 Detected Intent: ${suggestion.detectedIntent.displayName}")
                appendLine("🔍 Keywords: ${suggestion.keywords.joinToString(", ")}")
                appendLine("📊 Confidence: ${(suggestion.confidence * 100).toInt()}%")
                appendLine()
                
                if (suggestion.suggestedComponents.isNotEmpty()) {
                    appendLine("💡 Suggested Components:")
                    suggestion.suggestedComponents.take(3).forEach { match ->
                        appendLine("  • ${match.component.name} (${(match.matchScore * 100).toInt()}% match)")
                        match.matchReasons.forEach { reason ->
                            appendLine("    - $reason")
                        }
                    }
                } else {
                    appendLine("❌ No matching components found")
                }
            }
            
            withContext(Dispatchers.Main) {
                onSuggestion(suggestionText)
            }
            
            delay(500) // Simulate customization time
            
            // Step 2: Generate component if suggestion found
            if (suggestion.suggestedComponents.isNotEmpty()) {
                val bestMatch = suggestion.getBestSuggestion()!!
                val customizationResult = customizer.generateCustomizations(
                    bestMatch.component,
                    requirement,
                    projectContext
                )
                
                val generatedComponent = engine.generateComponent(
                    bestMatch.component,
                    customizationResult.customizations,
                    projectContext
                )
                
                val codePreview = buildString {
                    appendLine("// Generated Component: ${generatedComponent.fileName}")
                    appendLine("// Package: ${generatedComponent.packageName}")
                    appendLine("// Dependencies: ${generatedComponent.dependencies.joinToString(", ")}")
                    appendLine()
                    appendLine("// Customizations applied:")
                    customizationResult.customizations.forEach { (key, value) ->
                        appendLine("// $key = $value")
                    }
                    appendLine()
                    appendLine("// Preview of generated code:")
                    appendLine(generatedComponent.sourceCode.take(500) + "...")
                }
                
                withContext(Dispatchers.Main) {
                    onGenerated(codePreview)
                }
            }
            
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onSuggestion("❌ Error processing requirement: ${e.message}")
            }
        } finally {
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LegoComponentDemoPreview() {
    MaterialTheme {
        LegoComponentDemo()
    }
}