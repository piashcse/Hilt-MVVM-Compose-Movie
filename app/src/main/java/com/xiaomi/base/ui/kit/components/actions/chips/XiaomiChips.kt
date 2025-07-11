package com.xiaomi.base.ui.kit.components.actions.chips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.shapes.ComponentShapes

/**
 * Xiaomi Base UI Kit Assist Chip Component
 * 
 * Assist chips represent smart or automated actions that can span multiple apps,
 * such as opening a calendar event from the home screen.
 * 
 * @param onClick Called when the chip is clicked
 * @param label The text label for this chip
 * @param modifier Modifier to be applied to the chip
 * @param enabled Controls the enabled state of the chip
 * @param leadingIcon Optional leading icon for the chip
 * @param trailingIcon Optional trailing icon for the chip
 * @param shape Defines the chip's shape and corner radius
 * @param colors ChipColors that will be used to resolve the colors used for this chip
 * @param elevation ChipElevation used to resolve the elevation for this chip
 * @param border Border to draw around the chip
 * @param interactionSource MutableInteractionSource representing the stream of Interactions
 */
@Composable
fun XiaomiAssistChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = ComponentShapes.ChipMedium,
    colors: ChipColors = AssistChipDefaults.assistChipColors(),
    elevation: ChipElevation? = AssistChipDefaults.assistChipElevation(),
    border: BorderStroke? = AssistChipDefaults.assistChipBorder,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    AssistChip(
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Filter Chip Component
 * 
 * Filter chips use tags or descriptive words to filter content.
 * They can be a good alternative to toggle buttons or checkboxes.
 * 
 * @param selected Whether this chip is selected
 * @param onClick Called when the chip is clicked
 * @param label The text label for this chip
 * @param modifier Modifier to be applied to the chip
 * @param enabled Controls the enabled state of the chip
 * @param leadingIcon Optional leading icon for the chip
 * @param trailingIcon Optional trailing icon for the chip
 * @param shape Defines the chip's shape and corner radius
 * @param colors SelectableChipColors that will be used to resolve the colors used for this chip
 * @param elevation SelectableChipElevation used to resolve the elevation for this chip
 * @param border Border to draw around the chip
 * @param interactionSource MutableInteractionSource representing the stream of Interactions
 */
@Composable
fun XiaomiFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = ComponentShapes.ChipMedium,
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(),
    elevation: SelectableChipElevation? = FilterChipDefaults.filterChipElevation(),
    border: BorderStroke? = FilterChipDefaults.filterChipBorder,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Input Chip Component
 * 
 * Input chips represent discrete pieces of information entered by a user.
 * 
 * @param selected Whether this chip is selected
 * @param onClick Called when the chip is clicked
 * @param label The text label for this chip
 * @param modifier Modifier to be applied to the chip
 * @param enabled Controls the enabled state of the chip
 * @param leadingIcon Optional leading icon for the chip
 * @param avatar Optional avatar for the chip
 * @param trailingIcon Optional trailing icon for the chip
 * @param shape Defines the chip's shape and corner radius
 * @param colors SelectableChipColors that will be used to resolve the colors used for this chip
 * @param elevation SelectableChipElevation used to resolve the elevation for this chip
 * @param border Border to draw around the chip
 * @param interactionSource MutableInteractionSource representing the stream of Interactions
 */
@Composable
fun XiaomiInputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    avatar: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = ComponentShapes.ChipMedium,
    colors: SelectableChipColors = InputChipDefaults.inputChipColors(),
    elevation: SelectableChipElevation? = InputChipDefaults.inputChipElevation(),
    border: BorderStroke? = InputChipDefaults.inputChipBorder,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    InputChip(
        selected = selected,
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        avatar = avatar,
        trailingIcon = trailingIcon,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Suggestion Chip Component
 * 
 * Suggestion chips help narrow a user's intent by presenting dynamically generated suggestions,
 * such as possible responses or search filters.
 * 
 * @param onClick Called when the chip is clicked
 * @param label The text label for this chip
 * @param modifier Modifier to be applied to the chip
 * @param enabled Controls the enabled state of the chip
 * @param icon Optional icon for the chip
 * @param shape Defines the chip's shape and corner radius
 * @param colors ChipColors that will be used to resolve the colors used for this chip
 * @param elevation ChipElevation used to resolve the elevation for this chip
 * @param border Border to draw around the chip
 * @param interactionSource MutableInteractionSource representing the stream of Interactions
 */
@Composable
fun XiaomiSuggestionChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    shape: Shape = ComponentShapes.ChipMedium,
    colors: ChipColors = SuggestionChipDefaults.suggestionChipColors(),
    elevation: ChipElevation? = SuggestionChipDefaults.suggestionChipElevation(),
    border: BorderStroke? = SuggestionChipDefaults.suggestionChipBorder,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    SuggestionChip(
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    )
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Chips - Light")
@Composable
fun XiaomiChipsPreview() {
    XiaomiPreviewTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            // Assist Chip
            XiaomiAssistChip(
                onClick = { },
                label = { Text("Assist Chip") },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_dialog_info),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
            
            // Filter Chips
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                XiaomiFilterChip(
                    selected = true,
                    onClick = { },
                    label = { Text("Selected") }
                )
                
                XiaomiFilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("Unselected") }
                )
            }
            
            // Input Chip
            XiaomiInputChip(
                selected = true,
                onClick = { },
                label = { Text("Input Chip") },
                trailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "Remove",
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
            
            // Suggestion Chip
            XiaomiSuggestionChip(
                onClick = { },
                label = { Text("Suggestion") },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_search_category_default),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
        }
    }
}

@Preview(name = "Xiaomi Chips - Dark")
@Composable
fun XiaomiChipsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            XiaomiAssistChip(
                onClick = { },
                label = { Text("Assist Chip") }
            )
            
            XiaomiFilterChip(
                selected = true,
                onClick = { },
                label = { Text("Filter Chip") }
            )
            
            XiaomiSuggestionChip(
                onClick = { },
                label = { Text("Suggestion") }
            )
        }
    }
}