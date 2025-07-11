package com.xiaomi.base.ui.kit.components.actions.fab

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.shapes.ComponentShapes

/**
 * Xiaomi Base UI Kit Floating Action Button Component
 * 
 * A Material Design 3 floating action button implementation with Xiaomi design tokens.
 * FABs are used for the primary action in an application.
 * 
 * @param onClick Called when the FAB is clicked
 * @param modifier Modifier to be applied to the FAB
 * @param shape Defines the FAB's shape and corner radius
 * @param containerColor The color used for the background of this FAB
 * @param contentColor The preferred color for content inside this FAB
 * @param elevation FloatingActionButtonElevation used to resolve the elevation for this FAB
 * @param interactionSource MutableInteractionSource representing the stream of Interactions
 * @param content The FAB content, typically an Icon
 */
@Composable
fun XiaomiFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ComponentShapes.FABMedium,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content
    )
}

/**
 * Xiaomi Small Floating Action Button
 * 
 * A smaller variant of the FAB for secondary actions.
 */
@Composable
fun XiaomiSmallFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ComponentShapes.FABSmall,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    SmallFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content
    )
}

/**
 * Xiaomi Large Floating Action Button
 * 
 * A larger variant of the FAB for prominent primary actions.
 */
@Composable
fun XiaomiLargeFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ComponentShapes.FABLarge,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    LargeFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content
    )
}

/**
 * Xiaomi Extended Floating Action Button
 * 
 * An extended FAB that includes both an icon and text label.
 * 
 * @param text The text label to display
 * @param icon The icon to display
 * @param onClick Called when the FAB is clicked
 * @param modifier Modifier to be applied to the FAB
 * @param expanded Whether the FAB is expanded to show text
 * @param shape Defines the FAB's shape and corner radius
 * @param containerColor The color used for the background of this FAB
 * @param contentColor The preferred color for content inside this FAB
 * @param elevation FloatingActionButtonElevation used to resolve the elevation for this FAB
 * @param interactionSource MutableInteractionSource representing the stream of Interactions
 */
@Composable
fun XiaomiExtendedFAB(
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = true,
    shape: Shape = ComponentShapes.FABExtended,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    ExtendedFloatingActionButton(
        text = text,
        icon = icon,
        onClick = onClick,
        modifier = modifier,
        expanded = expanded,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource
    )
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi FABs - Light")
@Composable
fun XiaomiFABsPreview() {
    XiaomiPreviewTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            // Standard FAB
            XiaomiFAB(
                onClick = { },
                content = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_input_add),
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
            
            // Small FAB
            XiaomiSmallFAB(
                onClick = { },
                content = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_input_add),
                        contentDescription = "Add",
                        modifier = Modifier.size(16.dp)
                    )
                }
            )
            
            // Large FAB
            XiaomiLargeFAB(
                onClick = { },
                content = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_input_add),
                        contentDescription = "Add",
                        modifier = Modifier.size(36.dp)
                    )
                }
            )
            
            // Extended FAB
            XiaomiExtendedFAB(
                text = { Text("Create") },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_input_add),
                        contentDescription = null
                    )
                },
                onClick = { }
            )
        }
    }
}

@Preview(name = "Xiaomi FABs - Dark")
@Composable
fun XiaomiFABsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            XiaomiFAB(
                onClick = { },
                content = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_input_add),
                        contentDescription = "Add"
                    )
                }
            )
            
            XiaomiExtendedFAB(
                text = { Text("Create") },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(android.R.drawable.ic_input_add),
                        contentDescription = null
                    )
                },
                onClick = { }
            )
        }
    }
}