package com.xiaomi.base.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Universal input components that can be used across different domains
 */

/**
 * Universal form input component that adapts to different input types
 */
@Composable
fun UniversalFormInput(
    inputType: InputType,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    validator: ((String) -> Boolean)? = null,
    isRequired: Boolean = false,
    enabled: Boolean = true,
    supportingText: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {
    val isValid = validator?.invoke(value) ?: true
    
    Column(modifier = modifier) {
        // Label with required indicator
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            if (isRequired) {
                Text(
                    text = " *",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Input field based on type
        when (inputType) {
            InputType.TEXT, InputType.EMAIL, InputType.PASSWORD, InputType.NUMBER, InputType.PHONE -> {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = placeholder?.let { { Text(it) } },
                    enabled = enabled,
                    isError = !isValid && value.isNotEmpty(),
                    visualTransformation = if (inputType == InputType.PASSWORD) 
                        PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = when (inputType) {
                            InputType.EMAIL -> KeyboardType.Email
                            InputType.PASSWORD -> KeyboardType.Password
                            InputType.NUMBER -> KeyboardType.Number
                            InputType.PHONE -> KeyboardType.Phone
                            else -> KeyboardType.Text
                        }
                    ),
                    leadingIcon = leadingIcon?.let { icon ->
                        { Icon(imageVector = icon, contentDescription = null) }
                    },
                    trailingIcon = trailingIcon?.let { icon ->
                        {
                            IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                                Icon(imageVector = icon, contentDescription = null)
                            }
                        }
                    },
                    supportingText = supportingText?.let { text ->
                        { Text(text) }
                    }
                )
            }
            
            InputType.MULTILINE -> {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    placeholder = placeholder?.let { { Text(it) } },
                    enabled = enabled,
                    isError = !isValid && value.isNotEmpty(),
                    maxLines = 5,
                    supportingText = supportingText?.let { text ->
                        { Text(text) }
                    }
                )
            }
            
            InputType.DROPDOWN -> {
                // Dropdown will be handled separately
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(4.dp)
                        )
                        .clickable { onTrailingIconClick?.invoke() },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = value.ifEmpty { placeholder ?: "Select option" },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = if (value.isEmpty()) 
                            MaterialTheme.colorScheme.onSurfaceVariant 
                        else MaterialTheme.colorScheme.onSurface
                    )
                    
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                    )
                }
            }
        }
        
        // Validation error message
        if (!isValid && value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Invalid ${label.lowercase()}",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * Universal progress component with flexible display options
 */
@Composable
fun GenericProgressCard(
    title: String,
    currentValue: Float,
    targetValue: Float,
    unit: String? = null,
    modifier: Modifier = Modifier,
    onUpdate: ((Float) -> Unit)? = null,
    showPercentage: Boolean = true,
    progressColor: Color = MaterialTheme.colorScheme.primary
) {
    val progress = if (targetValue > 0) (currentValue / targetValue).coerceIn(0f, 1f) else 0f
    val percentage = (progress * 100).toInt()
    
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                onUpdate?.let { updateAction ->
                    IconButton(onClick = { /* Show update dialog */ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Update progress"
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Progress bar
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = progressColor,
                trackColor = progressColor.copy(alpha = 0.2f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Progress text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildString {
                        append(currentValue.toInt())
                        if (unit != null) append(" $unit")
                        append(" / ")
                        append(targetValue.toInt())
                        if (unit != null) append(" $unit")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (showPercentage) {
                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = progressColor
                    )
                }
            }
        }
    }
}

/**
 * Universal rating input component
 */
@Composable
fun UniversalRatingInput(
    rating: Float,
    onRatingChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    step: Float = 1f,
    label: String? = null,
    showRatingText: Boolean = true
) {
    Column(modifier = modifier) {
        label?.let { labelText ->
            Text(
                text = labelText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Star rating
            repeat(maxRating) { index ->
                val starRating = index + 1
                Icon(
                    imageVector = if (starRating <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Rate $starRating stars",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onRatingChange(starRating.toFloat()) },
                    tint = if (starRating <= rating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (showRatingText && rating > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$rating / $maxRating",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Universal counter input component
 */
@Composable
fun UniversalCounterInput(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = 1,
    unit: String? = null
) {
    Column(modifier = modifier) {
        label?.let { labelText ->
            Text(
                text = labelText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Decrease button
            IconButton(
                onClick = { 
                    val newValue = (value - step).coerceAtLeast(minValue)
                    if (newValue != value) onValueChange(newValue)
                },
                enabled = value > minValue
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease"
                )
            }
            
            // Value display
            Card(
                modifier = Modifier.padding(horizontal = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = buildString {
                        append(value)
                        if (unit != null) append(" $unit")
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            
            // Increase button
            IconButton(
                onClick = { 
                    val newValue = (value + step).coerceAtMost(maxValue)
                    if (newValue != value) onValueChange(newValue)
                },
                enabled = value < maxValue
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase"
                )
            }
        }
    }
}

// ===== Data Classes and Enums =====

enum class InputType {
    TEXT,
    EMAIL,
    PASSWORD,
    NUMBER,
    PHONE,
    MULTILINE,
    DROPDOWN
} 