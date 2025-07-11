package com.xiaomi.base.ui.kit.components.navigation.steppers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Enum representing the state of a step
 */
enum class XiaomiStepState {
    PENDING,
    ACTIVE,
    COMPLETED,
    ERROR
}

/**
 * Data class representing a step item
 */
data class XiaomiStepItem(
    val id: String,
    val title: String,
    val description: String? = null,
    val icon: ImageVector? = null,
    val state: XiaomiStepState = XiaomiStepState.PENDING,
    val optional: Boolean = false
)

/**
 * Xiaomi Horizontal Stepper
 * 
 * A horizontal stepper component following Material Design 3 principles.
 * 
 * @param steps List of step items
 * @param currentStep Current active step index (0-based)
 * @param modifier Modifier to be applied to the stepper
 * @param showLabels Whether to show step labels
 * @param showDescription Whether to show step descriptions
 * @param connector Custom connector between steps
 */
@Composable
fun XiaomiHorizontalStepper(
    steps: List<XiaomiStepItem>,
    currentStep: Int,
    modifier: Modifier = Modifier,
    showLabels: Boolean = true,
    showDescription: Boolean = false,
    connector: @Composable () -> Unit = { XiaomiStepConnector() }
) {
    Column(modifier = modifier) {
        // Step indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { index, step ->
                val stepState = when {
                    index < currentStep -> XiaomiStepState.COMPLETED
                    index == currentStep -> XiaomiStepState.ACTIVE
                    else -> step.state
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    XiaomiStepIndicator(
                        step = step.copy(state = stepState),
                        stepNumber = index + 1
                    )
                    
                    if (index < steps.lastIndex) {
                        Box(modifier = Modifier.weight(1f)) {
                            connector()
                        }
                    }
                }
            }
        }
        
        // Step labels and descriptions
        if (showLabels) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                steps.forEachIndexed { index, step ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = step.title,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (index == currentStep) FontWeight.Medium else FontWeight.Normal,
                            color = if (index <= currentStep) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        if (showDescription && step.description != null) {
                            Text(
                                text = step.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                        
                        if (step.optional) {
                            Text(
                                text = "Optional",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Xiaomi Vertical Stepper
 * 
 * A vertical stepper component.
 * 
 * @param steps List of step items
 * @param currentStep Current active step index (0-based)
 * @param modifier Modifier to be applied to the stepper
 * @param showConnector Whether to show connector lines between steps
 * @param content Optional content for each step
 */
@Composable
fun XiaomiVerticalStepper(
    steps: List<XiaomiStepItem>,
    currentStep: Int,
    modifier: Modifier = Modifier,
    showConnector: Boolean = true,
    content: @Composable ((Int, XiaomiStepItem) -> Unit)? = null
) {
    Column(modifier = modifier) {
        steps.forEachIndexed { index, step ->
            val stepState = when {
                index < currentStep -> XiaomiStepState.COMPLETED
                index == currentStep -> XiaomiStepState.ACTIVE
                else -> step.state
            }
            
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Step indicator column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    XiaomiStepIndicator(
                        step = step.copy(state = stepState),
                        stepNumber = index + 1
                    )
                    
                    if (showConnector && index < steps.lastIndex) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(48.dp)
                                .background(
                                    color = if (index < currentStep) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.outline
                                    },
                                    shape = RoundedCornerShape(1.dp)
                                )
                        )
                    }
                }
                
                // Step content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = if (index == currentStep) FontWeight.Medium else FontWeight.Normal,
                        color = if (index <= currentStep) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                    
                    if (step.description != null) {
                        Text(
                            text = step.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    
                    if (step.optional) {
                        Text(
                            text = "Optional",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    
                    // Custom content for this step
                    content?.invoke(index, step)
                    
                    if (index < steps.lastIndex) {
                        Box(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

/**
 * Step indicator component
 */
@Composable
private fun XiaomiStepIndicator(
    step: XiaomiStepItem,
    stepNumber: Int,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, contentColor, borderColor) = when (step.state) {
        XiaomiStepState.COMPLETED -> Triple(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary
        )
        XiaomiStepState.ACTIVE -> Triple(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary
        )
        XiaomiStepState.ERROR -> Triple(
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.onError,
            MaterialTheme.colorScheme.error
        )
        XiaomiStepState.PENDING -> Triple(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.onSurfaceVariant,
            MaterialTheme.colorScheme.outline
        )
    }
    
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = if (step.state == XiaomiStepState.PENDING) 1.dp else 0.dp,
                color = borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        when {
            step.state == XiaomiStepState.COMPLETED -> {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            step.state == XiaomiStepState.ERROR -> {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Error",
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            step.icon != null -> {
                Icon(
                    imageVector = step.icon,
                    contentDescription = step.title,
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            else -> {
                Text(
                    text = stepNumber.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = contentColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Step connector component
 */
@Composable
private fun XiaomiStepConnector(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(1.dp)
            )
    )
}

/**
 * Xiaomi Simple Stepper
 * 
 * A simplified stepper with just step numbers and titles.
 * 
 * @param stepTitles List of step titles
 * @param currentStep Current active step index (0-based)
 * @param modifier Modifier to be applied to the stepper
 * @param orientation Stepper orientation (horizontal or vertical)
 */
@Composable
fun XiaomiSimpleStepper(
    stepTitles: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier,
    orientation: XiaomiStepperOrientation = XiaomiStepperOrientation.HORIZONTAL
) {
    val steps = stepTitles.mapIndexed { index, title ->
        XiaomiStepItem(
            id = index.toString(),
            title = title
        )
    }
    
    when (orientation) {
        XiaomiStepperOrientation.HORIZONTAL -> {
            XiaomiHorizontalStepper(
                steps = steps,
                currentStep = currentStep,
                modifier = modifier
            )
        }
        XiaomiStepperOrientation.VERTICAL -> {
            XiaomiVerticalStepper(
                steps = steps,
                currentStep = currentStep,
                modifier = modifier
            )
        }
    }
}

/**
 * Stepper orientation enum
 */
enum class XiaomiStepperOrientation {
    HORIZONTAL,
    VERTICAL
}

/**
 * Xiaomi Progress Stepper
 * 
 * A stepper with progress indication.
 * 
 * @param steps List of step items
 * @param currentStep Current active step index (0-based)
 * @param modifier Modifier to be applied to the stepper
 * @param showProgress Whether to show progress percentage
 */
@Composable
fun XiaomiProgressStepper(
    steps: List<XiaomiStepItem>,
    currentStep: Int,
    modifier: Modifier = Modifier,
    showProgress: Boolean = true
) {
    Column(modifier = modifier) {
        if (showProgress) {
            val progress = if (steps.isNotEmpty()) {
                (currentStep + 1).toFloat() / steps.size.toFloat()
            } else 0f
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progress",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
            
            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(2.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
        
        XiaomiHorizontalStepper(
            steps = steps,
            currentStep = currentStep,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Steppers - Light")
@Composable
fun XiaomiSteppersPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Stepper Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var currentStep by remember { mutableIntStateOf(1) }
                
                val steps = listOf(
                    XiaomiStepItem(
                        id = "1",
                        title = "Account",
                        description = "Create your account"
                    ),
                    XiaomiStepItem(
                        id = "2",
                        title = "Profile",
                        description = "Set up your profile"
                    ),
                    XiaomiStepItem(
                        id = "3",
                        title = "Verification",
                        description = "Verify your email",
                        optional = true
                    ),
                    XiaomiStepItem(
                        id = "4",
                        title = "Complete",
                        description = "Finish setup"
                    )
                )
                
                // Horizontal stepper
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Horizontal Stepper",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        XiaomiHorizontalStepper(
                            steps = steps,
                            currentStep = currentStep,
                            showLabels = true,
                            showDescription = true
                        )
                    }
                }
                
                // Progress stepper
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Progress Stepper",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        XiaomiProgressStepper(
                            steps = steps.take(3),
                            currentStep = currentStep.coerceAtMost(2)
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Vertical Stepper")
@Composable
fun XiaomiVerticalStepperPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    "Vertical Stepper",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                val steps = listOf(
                    XiaomiStepItem(
                        id = "1",
                        title = "Select campaign settings",
                        description = "Choose your target audience and budget",
                        icon = Icons.Default.Warning
                    ),
                    XiaomiStepItem(
                        id = "2",
                        title = "Create an ad group",
                        description = "Set up your ad group with keywords"
                    ),
                    XiaomiStepItem(
                        id = "3",
                        title = "Create an ad",
                        description = "Design your advertisement"
                    )
                )
                
                XiaomiVerticalStepper(
                    steps = steps,
                    currentStep = 1,
                    content = { index, step ->
                        if (index == 1) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Text(
                                    text = "This step is currently active",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Steppers - Dark")
@Composable
fun XiaomiSteppersDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Dark Theme Steppers",
                    style = MaterialTheme.typography.titleMedium
                )
                
                val steps = listOf(
                    XiaomiStepItem("1", "Step 1", "First step"),
                    XiaomiStepItem("2", "Step 2", "Second step"),
                    XiaomiStepItem("3", "Step 3", "Third step", state = XiaomiStepState.ERROR)
                )
                
                XiaomiHorizontalStepper(
                    steps = steps,
                    currentStep = 2,
                    showLabels = true
                )
                
                XiaomiSimpleStepper(
                    stepTitles = listOf("Start", "Process", "Complete"),
                    currentStep = 1
                )
            }
        }
    }
}