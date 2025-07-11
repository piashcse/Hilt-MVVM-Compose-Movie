package com.xiaomi.base.ui.kit.components.selection.sliders

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing
import kotlin.math.roundToInt

/**
 * Xiaomi Slider
 * 
 * A Material Design 3 slider component with Xiaomi design tokens.
 * 
 * @param value Current value of the slider
 * @param onValueChange Callback when the slider value changes
 * @param modifier Modifier to be applied to the slider
 * @param enabled Whether the slider is enabled
 * @param valueRange Range of values for the slider
 * @param steps Number of discrete steps in the slider (0 for continuous)
 * @param onValueChangeFinished Callback when the user finishes changing the value
 * @param colors Colors to be used for the slider
 * @param interactionSource MutableInteractionSource for handling interactions
 */
@Composable
fun XiaomiSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: androidx.compose.material3.SliderColors = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
        colors = colors,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Range Slider
 * 
 * A slider that allows selecting a range of values.
 * 
 * @param value Current range value of the slider
 * @param onValueChange Callback when the slider range changes
 * @param modifier Modifier to be applied to the slider
 * @param enabled Whether the slider is enabled
 * @param valueRange Range of values for the slider
 * @param steps Number of discrete steps in the slider (0 for continuous)
 * @param onValueChangeFinished Callback when the user finishes changing the value
 * @param colors Colors to be used for the slider
 */
@Composable
fun XiaomiRangeSlider(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: androidx.compose.material3.SliderColors = SliderDefaults.colors()
) {
    RangeSlider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
        colors = colors
    )
}

/**
 * Xiaomi Labeled Slider
 * 
 * A slider with an associated label and value display.
 * 
 * @param value Current value of the slider
 * @param onValueChange Callback when the slider value changes
 * @param label The label text for the slider
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the slider is enabled
 * @param valueRange Range of values for the slider
 * @param steps Number of discrete steps in the slider
 * @param showValue Whether to show the current value
 * @param valueFormatter Function to format the displayed value
 * @param description Optional description text
 */
@Composable
fun XiaomiLabeledSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    showValue: Boolean = true,
    valueFormatter: (Float) -> String = { "%.1f".format(it) },
    description: String? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                }
            )
            
            if (showValue) {
                Text(
                    text = valueFormatter(value),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
            }
        }
        
        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                }
            )
        }
        
        XiaomiSlider(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Xiaomi Icon Slider
 * 
 * A slider with icons on both ends.
 * 
 * @param value Current value of the slider
 * @param onValueChange Callback when the slider value changes
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the slider is enabled
 * @param valueRange Range of values for the slider
 * @param steps Number of discrete steps in the slider
 * @param startIcon Icon to display at the start of the slider
 * @param endIcon Icon to display at the end of the slider
 */
@Composable
fun XiaomiIconSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    startIcon: ImageVector = Icons.Filled.VolumeDown,
    endIcon: ImageVector = Icons.Filled.VolumeUp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = startIcon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = if (enabled) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            }
        )
        
        XiaomiSlider(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.weight(1f)
        )
        
        Icon(
            imageVector = endIcon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = if (enabled) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            }
        )
    }
}

/**
 * Xiaomi Stepped Slider
 * 
 * A slider with visible step indicators.
 * 
 * @param value Current value of the slider
 * @param onValueChange Callback when the slider value changes
 * @param steps Number of steps in the slider
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the slider is enabled
 * @param valueRange Range of values for the slider
 * @param stepLabels Optional labels for each step
 */
@Composable
fun XiaomiSteppedSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    steps: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..steps.toFloat(),
    stepLabels: List<String>? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        XiaomiSlider(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            valueRange = valueRange,
            steps = steps - 1,
            modifier = Modifier.fillMaxWidth()
        )
        
        if (stepLabels != null && stepLabels.size == steps + 1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                stepLabels.forEach { label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (enabled) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Xiaomi Custom Color Slider
 * 
 * A slider with custom colors.
 * 
 * @param value Current value of the slider
 * @param onValueChange Callback when the slider value changes
 * @param modifier Modifier to be applied to the slider
 * @param enabled Whether the slider is enabled
 * @param valueRange Range of values for the slider
 * @param steps Number of discrete steps in the slider
 * @param thumbColor Color of the thumb
 * @param activeTrackColor Color of the active track
 * @param inactiveTrackColor Color of the inactive track
 */
@Composable
fun XiaomiCustomColorSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    thumbColor: Color = MaterialTheme.colorScheme.primary,
    activeTrackColor: Color = MaterialTheme.colorScheme.primary,
    inactiveTrackColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    XiaomiSlider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        colors = SliderDefaults.colors(
            thumbColor = thumbColor,
            activeTrackColor = activeTrackColor,
            inactiveTrackColor = inactiveTrackColor
        )
    )
}

/**
 * Xiaomi Vertical Slider
 * 
 * A vertically oriented slider.
 * 
 * @param value Current value of the slider
 * @param onValueChange Callback when the slider value changes
 * @param modifier Modifier to be applied to the slider
 * @param enabled Whether the slider is enabled
 * @param valueRange Range of values for the slider
 * @param steps Number of discrete steps in the slider
 */
@Composable
fun XiaomiVerticalSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0
) {
    // Note: Vertical slider would require custom implementation
    // This is a placeholder showing how it could be structured
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "%.1f".format(value),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        // Placeholder for vertical slider implementation
        XiaomiSlider(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Sliders - Light")
@Composable
fun XiaomiSlidersPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Slider Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var sliderValue1 by remember { mutableFloatStateOf(0.5f) }
                var sliderValue2 by remember { mutableFloatStateOf(0.3f) }
                var sliderValue3 by remember { mutableFloatStateOf(0.7f) }
                var rangeValue by remember { mutableStateOf(0.2f..0.8f) }
                
                // Basic sliders
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Basic Sliders",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiSlider(
                        value = 0.5f,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    XiaomiSlider(
                        value = 0.3f,
                        onValueChange = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Labeled slider
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Labeled Slider",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiLabeledSlider(
                        value = sliderValue1,
                        onValueChange = { sliderValue1 = it },
                        label = "Volume",
                        description = "Adjust the system volume level",
                        valueRange = 0f..100f,
                        valueFormatter = { "${it.roundToInt()}%" },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Icon slider
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Icon Slider",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiIconSlider(
                        value = sliderValue2,
                        onValueChange = { sliderValue2 = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Stepped slider
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Stepped Slider",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiSteppedSlider(
                        value = sliderValue3 * 4,
                        onValueChange = { sliderValue3 = it / 4 },
                        steps = 4,
                        stepLabels = listOf("Low", "Medium", "High", "Very High", "Max"),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Range slider
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Range Slider",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiRangeSlider(
                        value = rangeValue,
                        onValueChange = { rangeValue = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Text(
                        "Range: ${"%.1f".format(rangeValue.start)} - ${"%.1f".format(rangeValue.endInclusive)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Custom Sliders")
@Composable
fun XiaomiCustomSlidersPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Custom Color Sliders",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var redValue by remember { mutableFloatStateOf(0.5f) }
                var greenValue by remember { mutableFloatStateOf(0.3f) }
                var blueValue by remember { mutableFloatStateOf(0.7f) }
                
                // Red slider
                XiaomiLabeledSlider(
                    value = redValue * 255,
                    onValueChange = { redValue = it / 255 },
                    label = "Red",
                    valueRange = 0f..255f,
                    valueFormatter = { it.roundToInt().toString() },
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiCustomColorSlider(
                    value = redValue,
                    onValueChange = { redValue = it },
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Green slider
                XiaomiLabeledSlider(
                    value = greenValue * 255,
                    onValueChange = { greenValue = it / 255 },
                    label = "Green",
                    valueRange = 0f..255f,
                    valueFormatter = { it.roundToInt().toString() },
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiCustomColorSlider(
                    value = greenValue,
                    onValueChange = { greenValue = it },
                    thumbColor = Color.Green,
                    activeTrackColor = Color.Green,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Blue slider
                XiaomiLabeledSlider(
                    value = blueValue * 255,
                    onValueChange = { blueValue = it / 255 },
                    label = "Blue",
                    valueRange = 0f..255f,
                    valueFormatter = { it.roundToInt().toString() },
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiCustomColorSlider(
                    value = blueValue,
                    onValueChange = { blueValue = it },
                    thumbColor = Color.Blue,
                    activeTrackColor = Color.Blue,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Color preview
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(60.dp),
                    color = Color(redValue, greenValue, blueValue),
                    shape = MaterialTheme.shapes.medium
                ) {}
            }
        }
    }
}

@Preview(name = "Xiaomi Sliders - Dark")
@Composable
fun XiaomiSlidersDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Dark Theme Sliders",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var brightness by remember { mutableFloatStateOf(0.7f) }
                
                XiaomiLabeledSlider(
                    value = brightness * 100,
                    onValueChange = { brightness = it / 100 },
                    label = "Brightness",
                    description = "Adjust screen brightness for dark environments",
                    valueRange = 0f..100f,
                    valueFormatter = { "${it.roundToInt()}%" },
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiIconSlider(
                    value = brightness,
                    onValueChange = { brightness = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}