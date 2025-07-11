package com.xiaomi.base.ui.kit.components.selection.radiobuttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Data class representing a radio button option
 */
data class XiaomiRadioOption(
    val id: String,
    val label: String,
    val description: String? = null,
    val enabled: Boolean = true
)

/**
 * Xiaomi Radio Button
 * 
 * A Material Design 3 radio button component with Xiaomi design tokens.
 * 
 * @param selected Whether the radio button is selected
 * @param onClick Callback when the radio button is clicked
 * @param modifier Modifier to be applied to the radio button
 * @param enabled Whether the radio button is enabled
 * @param colors Colors to be used for the radio button
 * @param interactionSource MutableInteractionSource for handling interactions
 */
@Composable
fun XiaomiRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: androidx.compose.material3.RadioButtonColors = RadioButtonDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Labeled Radio Button
 * 
 * A radio button with an associated label that can be clicked to select the radio button.
 * 
 * @param selected Whether the radio button is selected
 * @param onClick Callback when the radio button is clicked
 * @param label The label text for the radio button
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the radio button is enabled
 * @param description Optional description text
 * @param labelFirst Whether to show the label before the radio button
 */
@Composable
fun XiaomiLabeledRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    description: String? = null,
    labelFirst: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = rememberRipple()
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (labelFirst) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (enabled) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            
            XiaomiRadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled,
                interactionSource = interactionSource
            )
        } else {
            XiaomiRadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled,
                interactionSource = interactionSource
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (enabled) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

/**
 * Xiaomi Radio Button Group
 * 
 * A group of radio buttons where only one can be selected at a time.
 * 
 * @param options List of radio button options
 * @param selectedOptionId The ID of the currently selected option
 * @param onOptionSelected Callback when an option is selected
 * @param modifier Modifier to be applied to the group
 * @param title Optional title for the group
 * @param enabled Whether all radio buttons in the group are enabled
 */
@Composable
fun XiaomiRadioButtonGroup(
    options: List<XiaomiRadioOption>,
    selectedOptionId: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        options.forEach { option ->
            XiaomiLabeledRadioButton(
                selected = selectedOptionId == option.id,
                onClick = { onOptionSelected(option.id) },
                label = option.label,
                description = option.description,
                enabled = enabled && option.enabled,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Xiaomi Horizontal Radio Button Group
 * 
 * A horizontal layout of radio buttons.
 * 
 * @param options List of radio button options
 * @param selectedOptionId The ID of the currently selected option
 * @param onOptionSelected Callback when an option is selected
 * @param modifier Modifier to be applied to the group
 * @param enabled Whether all radio buttons in the group are enabled
 */
@Composable
fun XiaomiHorizontalRadioButtonGroup(
    options: List<XiaomiRadioOption>,
    selectedOptionId: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            XiaomiLabeledRadioButton(
                selected = selectedOptionId == option.id,
                onClick = { onOptionSelected(option.id) },
                label = option.label,
                enabled = enabled && option.enabled
            )
        }
    }
}

/**
 * Xiaomi Compact Radio Button
 * 
 * A smaller radio button for use in dense layouts.
 * 
 * @param selected Whether the radio button is selected
 * @param onClick Callback when the radio button is clicked
 * @param modifier Modifier to be applied to the radio button
 * @param enabled Whether the radio button is enabled
 */
@Composable
fun XiaomiCompactRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    XiaomiRadioButton(
        selected = selected,
        onClick = onClick,
        modifier = modifier.size(20.dp),
        enabled = enabled
    )
}

/**
 * Xiaomi Custom Color Radio Button
 * 
 * A radio button with custom colors.
 * 
 * @param selected Whether the radio button is selected
 * @param onClick Callback when the radio button is clicked
 * @param modifier Modifier to be applied to the radio button
 * @param enabled Whether the radio button is enabled
 * @param selectedColor Color when selected
 * @param unselectedColor Color when unselected
 */
@Composable
fun XiaomiCustomColorRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.outline
) {
    XiaomiRadioButton(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = RadioButtonDefaults.colors(
            selectedColor = selectedColor,
            unselectedColor = unselectedColor
        )
    )
}

/**
 * Xiaomi Card Radio Button
 * 
 * A radio button displayed as a selectable card.
 * 
 * @param selected Whether the radio button is selected
 * @param onClick Callback when the radio button is clicked
 * @param label The label text for the radio button
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the radio button is enabled
 * @param description Optional description text
 */
@Composable
fun XiaomiCardRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    description: String? = null
) {
    Surface(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick,
                role = Role.RadioButton
            ),
        shape = MaterialTheme.shapes.medium,
        color = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        border = androidx.compose.foundation.BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                    color = if (enabled) {
                        if (selected) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (enabled) {
                            if (selected) {
                                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            
            XiaomiRadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled
            )
        }
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Radio Buttons - Light")
@Composable
fun XiaomiRadioButtonsPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Radio Button Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                // Basic radio buttons
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Basic Radio Buttons",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        XiaomiRadioButton(
                            selected = false,
                            onClick = {}
                        )
                        XiaomiRadioButton(
                            selected = true,
                            onClick = {}
                        )
                        XiaomiRadioButton(
                            selected = false,
                            onClick = {},
                            enabled = false
                        )
                    }
                }
                
                // Labeled radio buttons
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Labeled Radio Buttons",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    var selectedOption by remember { mutableStateOf("option1") }
                    
                    XiaomiLabeledRadioButton(
                        selected = selectedOption == "option1",
                        onClick = { selectedOption = "option1" },
                        label = "Option 1",
                        description = "This is the first option with description"
                    )
                    
                    XiaomiLabeledRadioButton(
                        selected = selectedOption == "option2",
                        onClick = { selectedOption = "option2" },
                        label = "Option 2",
                        labelFirst = true
                    )
                    
                    XiaomiLabeledRadioButton(
                        selected = selectedOption == "option3",
                        onClick = { selectedOption = "option3" },
                        label = "Disabled option",
                        enabled = false
                    )
                }
                
                // Compact radio buttons
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Compact Radio Buttons",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        XiaomiCompactRadioButton(
                            selected = false,
                            onClick = {}
                        )
                        XiaomiCompactRadioButton(
                            selected = true,
                            onClick = {}
                        )
                        Text(
                            "Compact size for dense layouts",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Radio Button Group")
@Composable
fun XiaomiRadioButtonGroupPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                var selectedTheme by remember { mutableStateOf("light") }
                var selectedSize by remember { mutableStateOf("medium") }
                
                // Vertical group
                XiaomiRadioButtonGroup(
                    options = listOf(
                        XiaomiRadioOption(
                            id = "light",
                            label = "Light Theme",
                            description = "Use light colors for the interface"
                        ),
                        XiaomiRadioOption(
                            id = "dark",
                            label = "Dark Theme",
                            description = "Use dark colors for the interface"
                        ),
                        XiaomiRadioOption(
                            id = "auto",
                            label = "Auto Theme",
                            description = "Follow system theme settings"
                        )
                    ),
                    selectedOptionId = selectedTheme,
                    onOptionSelected = { selectedTheme = it },
                    title = "Theme Preference"
                )
                
                // Horizontal group
                Text(
                    "Text Size",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                
                XiaomiHorizontalRadioButtonGroup(
                    options = listOf(
                        XiaomiRadioOption(id = "small", label = "Small"),
                        XiaomiRadioOption(id = "medium", label = "Medium"),
                        XiaomiRadioOption(id = "large", label = "Large")
                    ),
                    selectedOptionId = selectedSize,
                    onOptionSelected = { selectedSize = it }
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Card Radio Buttons")
@Composable
fun XiaomiCardRadioButtonsPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Card Radio Buttons",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var selectedPlan by remember { mutableStateOf("basic") }
                
                XiaomiCardRadioButton(
                    selected = selectedPlan == "basic",
                    onClick = { selectedPlan = "basic" },
                    label = "Basic Plan",
                    description = "Perfect for individuals and small teams",
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiCardRadioButton(
                    selected = selectedPlan == "pro",
                    onClick = { selectedPlan = "pro" },
                    label = "Pro Plan",
                    description = "Advanced features for growing businesses",
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiCardRadioButton(
                    selected = selectedPlan == "enterprise",
                    onClick = { selectedPlan = "enterprise" },
                    label = "Enterprise Plan",
                    description = "Full-featured solution for large organizations",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Radio Buttons - Dark")
@Composable
fun XiaomiRadioButtonsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Dark Theme Radio Buttons",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var selectedOption by remember { mutableStateOf("option1") }
                
                XiaomiLabeledRadioButton(
                    selected = selectedOption == "option1",
                    onClick = { selectedOption = "option1" },
                    label = "Dark mode option",
                    description = "Optimized for low-light environments"
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    XiaomiCustomColorRadioButton(
                        selected = true,
                        onClick = {},
                        selectedColor = MaterialTheme.colorScheme.secondary
                    )
                    XiaomiCustomColorRadioButton(
                        selected = true,
                        onClick = {},
                        selectedColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}