package com.xiaomi.base.components.animation.loading

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Shimmer Loading Component
 * Creates a shimmer effect for loading states
 * 
 * @param isLoading Whether the shimmer effect should be active
 * @param modifier Modifier to be applied to the component
 * @param shimmerColors List of colors for the shimmer gradient
 * @param animationDuration Duration of one shimmer cycle in milliseconds
 * @param content The content to show when not loading, or shimmer placeholder when loading
 */
@Composable
fun ShimmerLoadingComponent(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    ),
    animationDuration: Int = 1000,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        Box(
            modifier = modifier.shimmerEffect(
                colors = shimmerColors,
                animationDuration = animationDuration
            )
        )
    } else {
        content()
    }
}

/**
 * Shimmer effect modifier
 * Applies shimmer animation to any composable
 */
fun Modifier.shimmerEffect(
    colors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    ),
    animationDuration: Int = 1000
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )
    
    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    )
}

/**
 * Predefined Shimmer Loading Shapes
 * Common shimmer placeholder shapes for different UI elements
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    width: Dp = 100.dp,
    height: Dp = 20.dp,
    shape: Shape = RoundedCornerShape(4.dp),
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
) {
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .clip(shape)
            .then(
                if (isLoading) {
                    Modifier.shimmerEffect(colors = shimmerColors)
                } else {
                    Modifier.background(Color.Transparent)
                }
            )
    )
}

/**
 * Shimmer Text Placeholder
 * Mimics text loading with multiple lines
 */
@Composable
fun ShimmerText(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    lines: Int = 3,
    lineHeight: Dp = 16.dp,
    lineSpacing: Dp = 4.dp,
    lastLineWidthRatio: Float = 0.7f,
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(lineSpacing)
    ) {
        repeat(lines) { index ->
            val isLastLine = index == lines - 1
            ShimmerBox(
                isLoading = isLoading,
                width = if (isLastLine) 200.dp * lastLineWidthRatio else 200.dp,
                height = lineHeight,
                shimmerColors = shimmerColors
            )
        }
    }
}

/**
 * Shimmer Card Placeholder
 * Common card loading placeholder
 */
@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    imageHeight: Dp = 120.dp,
    contentPadding: Dp = 16.dp,
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Image placeholder
        ShimmerBox(
            isLoading = isLoading,
            width = Dp.Unspecified,
            height = imageHeight,
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            shimmerColors = shimmerColors,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Content placeholder
        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Title
            ShimmerBox(
                isLoading = isLoading,
                width = 150.dp,
                height = 20.dp,
                shimmerColors = shimmerColors
            )
            
            // Description
            ShimmerText(
                isLoading = isLoading,
                lines = 2,
                shimmerColors = shimmerColors
            )
            
            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerBox(
                    isLoading = isLoading,
                    width = 80.dp,
                    height = 32.dp,
                    shape = RoundedCornerShape(16.dp),
                    shimmerColors = shimmerColors
                )
                ShimmerBox(
                    isLoading = isLoading,
                    width = 80.dp,
                    height = 32.dp,
                    shape = RoundedCornerShape(16.dp),
                    shimmerColors = shimmerColors
                )
            }
        }
    }
}

/**
 * Shimmer List Item Placeholder
 * Common list item loading placeholder
 */
@Composable
fun ShimmerListItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    hasAvatar: Boolean = true,
    hasSubtitle: Boolean = true,
    hasTrailingIcon: Boolean = true,
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar
        if (hasAvatar) {
            ShimmerBox(
                isLoading = isLoading,
                width = 40.dp,
                height = 40.dp,
                shape = RoundedCornerShape(20.dp),
                shimmerColors = shimmerColors
            )
        }
        
        // Content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Title
            ShimmerBox(
                isLoading = isLoading,
                width = 120.dp,
                height = 16.dp,
                shimmerColors = shimmerColors
            )
            
            // Subtitle
            if (hasSubtitle) {
                ShimmerBox(
                    isLoading = isLoading,
                    width = 80.dp,
                    height = 14.dp,
                    shimmerColors = shimmerColors
                )
            }
        }
        
        // Trailing icon
        if (hasTrailingIcon) {
            ShimmerBox(
                isLoading = isLoading,
                width = 24.dp,
                height = 24.dp,
                shape = RoundedCornerShape(4.dp),
                shimmerColors = shimmerColors
            )
        }
    }
}

/**
 * Shimmer Grid Item Placeholder
 * Common grid item loading placeholder
 */
@Composable
fun ShimmerGridItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    aspectRatio: Float = 1f,
    hasTitle: Boolean = true,
    hasSubtitle: Boolean = false,
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Image placeholder
        ShimmerBox(
            isLoading = isLoading,
            width = Dp.Unspecified,
            height = Dp.Unspecified,
            shape = RoundedCornerShape(8.dp),
            shimmerColors = shimmerColors,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
        )
        
        // Title
        if (hasTitle) {
            ShimmerBox(
                isLoading = isLoading,
                width = 80.dp,
                height = 14.dp,
                shimmerColors = shimmerColors
            )
        }
        
        // Subtitle
        if (hasSubtitle) {
            ShimmerBox(
                isLoading = isLoading,
                width = 60.dp,
                height = 12.dp,
                shimmerColors = shimmerColors
            )
        }
    }
}