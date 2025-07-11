package com.xiaomi.base.ui.kit.components.containment.dividers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.SemanticSpacing

/**
 * Xiaomi Base UI Kit Horizontal Divider
 * 
 * A Material Design 3 horizontal divider component with Xiaomi design tokens.
 * Used to separate content horizontally.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param thickness The thickness of the divider line
 * @param color The color of the divider
 */
@Composable
fun XiaomiHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}

/**
 * Xiaomi Base UI Kit Vertical Divider
 * 
 * A Material Design 3 vertical divider component with Xiaomi design tokens.
 * Used to separate content vertically.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param thickness The thickness of the divider line
 * @param color The color of the divider
 */
@Composable
fun XiaomiVerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant
) {
    VerticalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}

/**
 * Xiaomi Thick Divider
 * 
 * A thicker divider for stronger visual separation.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param thickness The thickness of the divider line
 * @param color The color of the divider
 */
@Composable
fun XiaomiThickDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 8.dp,
    color: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    XiaomiHorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}

/**
 * Xiaomi Section Divider
 * 
 * A divider with padding for section separation.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param thickness The thickness of the divider line
 * @param color The color of the divider
 * @param paddingHorizontal Horizontal padding around the divider
 */
@Composable
fun XiaomiSectionDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
    paddingHorizontal: Dp = SemanticSpacing.ContentPaddingHorizontal
) {
    XiaomiHorizontalDivider(
        modifier = modifier.padding(horizontal = paddingHorizontal),
        thickness = thickness,
        color = color
    )
}

/**
 * Xiaomi Inset Divider
 * 
 * A divider with left inset, commonly used in lists.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param inset The left inset amount
 * @param thickness The thickness of the divider line
 * @param color The color of the divider
 */
@Composable
fun XiaomiInsetDivider(
    modifier: Modifier = Modifier,
    inset: Dp = 56.dp,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant
) {
    XiaomiHorizontalDivider(
        modifier = modifier.padding(start = inset),
        thickness = thickness,
        color = color
    )
}

/**
 * Xiaomi Middle Divider
 * 
 * A divider with equal padding on both sides.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param padding The padding on both sides
 * @param thickness The thickness of the divider line
 * @param color The color of the divider
 */
@Composable
fun XiaomiMiddleDivider(
    modifier: Modifier = Modifier,
    padding: Dp = 24.dp,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant
) {
    XiaomiHorizontalDivider(
        modifier = modifier.padding(horizontal = padding),
        thickness = thickness,
        color = color
    )
}

/**
 * Xiaomi Gradient Divider
 * 
 * A divider with gradient effect using background.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param height The height of the gradient divider
 * @param startColor The starting color of the gradient
 * @param endColor The ending color of the gradient
 */
@Composable
fun XiaomiGradientDivider(
    modifier: Modifier = Modifier,
    height: Dp = 1.dp,
    startColor: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0f),
    endColor: Color = MaterialTheme.colorScheme.outlineVariant
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                    colors = listOf(startColor, endColor, startColor)
                )
            )
    )
}

/**
 * Xiaomi Rounded Divider
 * 
 * A divider with rounded corners.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param thickness The thickness of the divider line
 * @param color The color of the divider
 * @param shape The shape of the divider
 */
@Composable
fun XiaomiRoundedDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 2.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(thickness / 2)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .clip(shape)
            .background(color)
    )
}

/**
 * Xiaomi Dotted Divider
 * 
 * A divider with dotted pattern effect.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param dotSize The size of each dot
 * @param spacing The spacing between dots
 * @param color The color of the dots
 */
@Composable
fun XiaomiDottedDivider(
    modifier: Modifier = Modifier,
    dotSize: Dp = 2.dp,
    spacing: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally)
    ) {
        repeat(20) {
            Box(
                modifier = Modifier
                    .width(dotSize)
                    .height(dotSize)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(color)
            )
        }
    }
}

/**
 * Xiaomi Text Divider
 * 
 * A divider with text in the center.
 * 
 * @param text The text to display in the center
 * @param modifier Modifier to be applied to the divider
 * @param textColor The color of the text
 * @param dividerColor The color of the divider lines
 * @param thickness The thickness of the divider lines
 */
@Composable
fun XiaomiTextDivider(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    dividerColor: Color = MaterialTheme.colorScheme.outlineVariant,
    thickness: Dp = 1.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        XiaomiHorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = thickness,
            color = dividerColor
        )
        
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
        
        XiaomiHorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = thickness,
            color = dividerColor
        )
    }
}

/**
 * Xiaomi Icon Divider
 * 
 * A divider with an icon in the center.
 * 
 * @param icon The icon composable to display in the center
 * @param modifier Modifier to be applied to the divider
 * @param dividerColor The color of the divider lines
 * @param thickness The thickness of the divider lines
 */
@Composable
fun XiaomiIconDivider(
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dividerColor: Color = MaterialTheme.colorScheme.outlineVariant,
    thickness: Dp = 1.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        XiaomiHorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = thickness,
            color = dividerColor
        )
        
        icon()
        
        XiaomiHorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = thickness,
            color = dividerColor
        )
    }
}

/**
 * Xiaomi Space Divider
 * 
 * An invisible divider that provides spacing.
 * 
 * @param height The height of the space
 * @param modifier Modifier to be applied to the divider
 */
@Composable
fun XiaomiSpaceDivider(
    height: Dp,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Spacer(
        modifier = modifier.height(height)
    )
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Dividers - Light")
@Composable
fun XiaomiDividersPreview() {
    XiaomiPreviewTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Basic Dividers", style = MaterialTheme.typography.titleMedium)
            
            Text("Standard Horizontal Divider")
            XiaomiHorizontalDivider()
            
            Text("Thick Divider")
            XiaomiThickDivider()
            
            Text("Section Divider")
            XiaomiSectionDivider()
            
            Text("Inset Divider")
            XiaomiInsetDivider()
            
            Text("Middle Divider")
            XiaomiMiddleDivider()
            
            Text("Rounded Divider")
            XiaomiRoundedDivider()
            
            Text("Gradient Divider")
            XiaomiGradientDivider()
            
            Text("Dotted Divider")
            XiaomiDottedDivider()
            
            Text("Text Divider")
            XiaomiTextDivider(text = "OR")
            
            Text("Icon Divider")
            XiaomiIconDivider(
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            )
        }
    }
}

@Preview(name = "Xiaomi Dividers - Vertical")
@Composable
fun XiaomiVerticalDividersPreview() {
    XiaomiPreviewTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Vertical Dividers", style = MaterialTheme.typography.titleMedium)
            
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.height(100.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Section A", textAlign = TextAlign.Center)
                }
                
                XiaomiVerticalDivider()
                
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Section B", textAlign = TextAlign.Center)
                }
                
                XiaomiVerticalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Section C", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Dividers - Dark")
@Composable
fun XiaomiDividersDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Content Above")
            XiaomiHorizontalDivider()
            
            Text("Content Below")
            XiaomiTextDivider(text = "SECTION")
            
            Text("More Content")
            XiaomiRoundedDivider(
                color = MaterialTheme.colorScheme.secondary
            )
            
            Text("Final Content")
        }
    }
}