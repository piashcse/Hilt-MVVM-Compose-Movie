# Lego Component System - Demo Usage

## 🎯 Mục Đích Demo

Demo này minh họa cách AI sẽ sử dụng Lego Component System để tự động generate code từ natural language requirements.

## 🤖 AI Workflow Demo

### Scenario 1: Login Screen Request

**User Input:**
```
"Tôi cần tạo một login screen với email input, password input, login button có loading state, và forgot password link"
```

**AI Analysis Process:**

#### Step 1: Requirement Analysis
```kotlin
val analysis = ComponentSelector().analyzeRequirement(
    "Tôi cần tạo một login screen với email input, password input, login button có loading state, và forgot password link"
)

// Result:
RequirementAnalysis(
    originalRequirement = "Tôi cần tạo một login screen với email input, password input, login button có loading state, và forgot password link",
    intent = Intent.AUTHENTICATION,
    entities = listOf(
        UIEntity.INPUT_FIELD, // email input
        UIEntity.INPUT_FIELD, // password input  
        UIEntity.BUTTON,      // login button
        UIEntity.BUTTON       // forgot password link
    ),
    complexity = ComplexityLevel.MEDIUM,
    context = mapOf(
        "screen_type" to "login",
        "has_loading" to "true",
        "input_types" to "email,password"
    ),
    confidence = 0.92f
)
```

#### Step 2: Component Selection
```kotlin
val recommendations = ComponentSelector().selectComponents(analysis)

// Top Recommendations:
[
    ComponentRecommendation(
        component = ComponentRegistry.getComponent("text_input")!!,
        relevanceScore = 0.95f,
        reasoningExplanation = "Perfect match for email/password input with validation",
        suggestedCustomizations = mapOf(
            "INPUT_TYPE" to "EMAIL",
            "HAS_VALIDATION" to true,
            "HAS_COUNTER" to false
        )
    ),
    ComponentRecommendation(
        component = ComponentRegistry.getComponent("primary_button")!!,
        relevanceScore = 0.88f,
        reasoningExplanation = "Primary button with loading state for login action",
        suggestedCustomizations = mapOf(
            "HAS_LOADING" to true,
            "HAS_ICON" to false,
            "BUTTON_SIZE" to "LARGE"
        )
    )
]
```

#### Step 3: Code Generation
```kotlin
val templateEngine = TemplateEngine()
val projectContext = ProjectContext(
    projectName = "LoginApp",
    basePackage = "com.xiaomi.loginapp",
    minSdk = 24,
    targetSdk = 34,
    composeVersion = "1.5.4",
    materialVersion = "1.1.2",
    colorScheme = mapOf(
        "primary" to "#1976D2",
        "secondary" to "#424242"
    ),
    typography = mapOf(
        "h1" to "32.sp",
        "body1" to "16.sp"
    )
)

// Generate Email Input
val emailInput = templateEngine.generateComponent(
    metadata = ComponentRegistry.getComponent("text_input")!!,
    customizations = mapOf(
        "COMPONENT_NAME" to "EmailInput",
        "INPUT_TYPE" to "EMAIL",
        "HAS_VALIDATION" to true,
        "HAS_COUNTER" to false,
        "CUSTOMIZATIONS" to listOf(
            "Email input type",
            "Validation enabled",
            "No character counter"
        )
    ),
    projectContext = projectContext
)

// Generate Password Input
val passwordInput = templateEngine.generateComponent(
    metadata = ComponentRegistry.getComponent("text_input")!!,
    customizations = mapOf(
        "COMPONENT_NAME" to "PasswordInput",
        "INPUT_TYPE" to "PASSWORD",
        "HAS_VALIDATION" to true,
        "HAS_COUNTER" to false,
        "CUSTOMIZATIONS" to listOf(
            "Password input type",
            "Validation enabled",
            "No character counter"
        )
    ),
    projectContext = projectContext
)

// Generate Login Button
val loginButton = templateEngine.generateComponent(
    metadata = ComponentRegistry.getComponent("primary_button")!!,
    customizations = mapOf(
        "COMPONENT_NAME" to "LoginButton",
        "HAS_LOADING" to true,
        "HAS_ICON" to false,
        "BUTTON_SIZE" to "LARGE",
        "CUSTOMIZATIONS" to listOf(
            "Loading state enabled",
            "Large size",
            "No icon"
        )
    ),
    projectContext = projectContext
)
```

#### Step 4: Generated Code Output

**EmailInput.kt:**
```kotlin
package com.xiaomi.loginapp.components.input

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
 * EmailInput - Material 3 Text Input Field
 * 
 * Generated by Lego Component System
 * Input Type: EMAIL
 * Includes validation support
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    validator: ((String) -> String?)? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
    )
) {
    val focusManager = LocalFocusManager.current
    var validationError by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(value) {
        validationError = validator?.invoke(value)
    }
    
    val hasError = isError || validationError != null
    val displayErrorMessage = errorMessage ?: validationError

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = placeholder?.let { { Text(it) } },
            enabled = enabled,
            readOnly = readOnly,
            isError = hasError,
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            supportingText = if (hasError && displayErrorMessage != null) {
                {
                    Text(
                        text = displayErrorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmailInputPreview() {
    var text by remember { mutableStateOf("") }
    
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EmailInput(
                value = text,
                onValueChange = { text = it },
                label = "Email Input",
                placeholder = "Enter your email...",
                validator = { value ->
                    when {
                        value.isNotEmpty() && !value.contains("@") -> "Invalid email format"
                        value.isEmpty() -> "This field is required"
                        else -> null
                    }
                }
            )
        }
    }
}
```

**LoginButton.kt:**
```kotlin
package com.xiaomi.loginapp.components.button

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
 * LoginButton - Material 3 Primary Button
 * 
 * Generated by Lego Component System
 * Customizations applied:
 * - Loading state enabled
 * - Large size
 * - No icon
 */
@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp), // Large size
        enabled = enabled && !isLoading,
        colors = colors
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = colors.contentColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginButtonPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LoginButton(
                text = "Login",
                onClick = {}
            )
            
            LoginButton(
                text = "Logging in...",
                isLoading = true,
                onClick = {}
            )
            
            LoginButton(
                text = "Disabled",
                enabled = false,
                onClick = {}
            )
        }
    }
}
```

#### Step 5: Complete Login Screen Assembly

**LoginScreen.kt:**
```kotlin
package com.xiaomi.loginapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.loginapp.components.input.EmailInput
import com.xiaomi.loginapp.components.button.LoginButton

@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Sign in to your account",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Email Input - Generated Component
        EmailInput(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Enter your email",
            validator = { value ->
                when {
                    value.isEmpty() -> "Email is required"
                    !value.contains("@") -> "Invalid email format"
                    else -> null
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Password Input - Generated Component (similar to EmailInput)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Login Button - Generated Component
        LoginButton(
            text = "Sign In",
            onClick = { onLoginClick(email, password) },
            isLoading = isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Forgot Password Link
        TextButton(
            onClick = onForgotPasswordClick
        ) {
            Text("Forgot Password?")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onLoginClick = { _, _ -> },
            onForgotPasswordClick = { },
            isLoading = false
        )
    }
}
```

---

## 🎨 Scenario 2: Product Card Grid Request

**User Input:**
```
"Tôi cần tạo một grid hiển thị danh sách sản phẩm, mỗi item là một card có hình ảnh, tên sản phẩm, giá, rating và nút add to cart"
```

**AI Generated Components:**

1. **ProductCard.kt** (từ info_card template)
2. **ProductGrid.kt** (từ responsive_grid template)
3. **AddToCartButton.kt** (từ primary_button template)
4. **RatingDisplay.kt** (custom component)

**Final Result:**
```kotlin
@Composable
fun ProductGridScreen(
    products: List<Product>,
    onAddToCart: (Product) -> Unit,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onAddToCart = { onAddToCart(product) },
                onClick = { onProductClick(product) }
            )
        }
    }
}
```

---

## 🧹 Cleanup Process

### Automatic Template Removal

```kotlin
// File: app/src/main/java/com/xiaomi/base/templates/cleanup/TemplateCleanup.kt
package com.xiaomi.base.templates.cleanup

import java.io.File

object TemplateCleanup {
    
    fun removeTemplatePackage(projectRoot: String): CleanupResult {
        val templatePackagePath = "$projectRoot/app/src/main/java/com/xiaomi/base/templates"
        val templateAssetsPath = "$projectRoot/app/src/main/assets/templates"
        
        val removedFiles = mutableListOf<String>()
        val errors = mutableListOf<String>()
        
        try {
            // Remove template package
            File(templatePackagePath).deleteRecursively().also {
                if (it) removedFiles.add(templatePackagePath)
                else errors.add("Failed to remove: $templatePackagePath")
            }
            
            // Remove template assets
            File(templateAssetsPath).deleteRecursively().also {
                if (it) removedFiles.add(templateAssetsPath)
                else errors.add("Failed to remove: $templateAssetsPath")
            }
            
            // Remove template dependencies from build.gradle
            removeTemplateDependencies("$projectRoot/app/build.gradle")
            
        } catch (e: Exception) {
            errors.add("Cleanup error: ${e.message}")
        }
        
        return CleanupResult(
            success = errors.isEmpty(),
            removedFiles = removedFiles,
            errors = errors,
            spaceSaved = calculateSpaceSaved(removedFiles)
        )
    }
    
    private fun removeTemplateDependencies(buildGradlePath: String) {
        val buildFile = File(buildGradlePath)
        if (!buildFile.exists()) return
        
        val content = buildFile.readText()
        val updatedContent = content.lines().filterNot { line ->
            line.trim().startsWith("// Template dependency") ||
            line.contains("lego-component-system")
        }.joinToString("\n")
        
        buildFile.writeText(updatedContent)
    }
}

data class CleanupResult(
    val success: Boolean,
    val removedFiles: List<String>,
    val errors: List<String>,
    val spaceSaved: Long // in bytes
)
```

---

## 📊 Performance Metrics

### Generation Speed Test
```kotlin
fun benchmarkComponentGeneration() {
    val startTime = System.currentTimeMillis()
    
    // Generate 10 components
    repeat(10) {
        val component = templateEngine.generateComponent(
            metadata = ComponentRegistry.getComponent("primary_button")!!,
            customizations = mapOf("COMPONENT_NAME" to "TestButton$it"),
            projectContext = defaultProjectContext
        )
    }
    
    val endTime = System.currentTimeMillis()
    val averageTime = (endTime - startTime) / 10.0
    
    println("Average generation time: ${averageTime}ms per component")
    // Expected: < 200ms per component
}
```

### AI Accuracy Test
```kotlin
fun testAIAccuracy() {
    val testCases = listOf(
        "login form" to listOf("text_input", "primary_button"),
        "product card" to listOf("info_card", "primary_button"),
        "navigation menu" to listOf("navigation_drawer"),
        "search bar" to listOf("text_input", "icon_button")
    )
    
    var correctPredictions = 0
    
    testCases.forEach { (requirement, expectedComponents) ->
        val analysis = ComponentSelector().analyzeRequirement(requirement)
        val recommendations = ComponentSelector().selectComponents(analysis)
        
        val predictedComponents = recommendations.take(expectedComponents.size)
            .map { it.component.id }
        
        if (predictedComponents.containsAll(expectedComponents)) {
            correctPredictions++
        }
    }
    
    val accuracy = correctPredictions.toFloat() / testCases.size
    println("AI Accuracy: ${(accuracy * 100).toInt()}%")
    // Target: > 85%
}
```

---

## 🎯 Next Steps

1. **Implement Foundation** (Week 1)
   - Setup package structure
   - Create ComponentRegistry
   - Build TemplateEngine core

2. **Create Basic Templates** (Week 2)
   - Button templates
   - Input templates  
   - Card templates

3. **AI Integration** (Week 3)
   - ComponentSelector implementation
   - Requirement analysis
   - Recommendation system

4. **Testing & Optimization** (Week 4)
   - Performance benchmarks
   - Accuracy testing
   - Code quality validation

5. **Production Ready** (Week 5)
   - Cleanup utilities
   - Documentation
   - Final integration

**Ready to start implementation!** 🚀