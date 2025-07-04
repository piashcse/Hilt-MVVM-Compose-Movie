package com.xiaomi.base.components.interaction.feedback

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Visual Feedback Types
 * Different types of visual feedback for various interactions
 */
enum class VisualFeedbackType {
    RIPPLE,
    SCALE,
    GLOW,
    BORDER_FLASH,
    COLOR_CHANGE,
    SHAKE,
    BOUNCE,
    PULSE,
    HIGHLIGHT,
    SHADOW_ELEVATION
}

/**
 * Visual Feedback Component
 * Provides visual feedback for user interactions
 * 
 * @param modifier Modifier to be applied to the component
 * @param feedbackType Type of visual feedback to provide
 * @param enabled Whether visual feedback is enabled
 * @param feedbackColor Color for the feedback effect
 * @param duration Duration of the feedback animation
 * @param onClick Callback when the component is clicked
 * @param content The composable content
 */
@Composable
fun VisualFeedbackComponent(
    modifier: Modifier = Modifier,
    feedbackType: VisualFeedbackType = VisualFeedbackType.RIPPLE,
    enabled: Boolean = true,
    feedbackColor: Color = MaterialTheme.colorScheme.primary,
    duration: Int = 300,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var isTriggered by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    
    val triggerFeedback = {
        if (enabled) {
            scope.launch {
                isTriggered = true
                delay(duration.toLong())
                isTriggered = false
            }
        }
    }
    
    Box(
        modifier = modifier.then(
            if (onClick != null) {
                Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button
                ) {
                    triggerFeedback()
                    onClick()
                }
            } else {
                Modifier
            }
        )
    ) {
        when (feedbackType) {
            VisualFeedbackType.RIPPLE -> {
                RippleFeedback(
                    isTriggered = isTriggered,
                    color = feedbackColor,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.SCALE -> {
                ScaleFeedback(
                    isTriggered = isTriggered,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.GLOW -> {
                GlowFeedback(
                    isTriggered = isTriggered,
                    color = feedbackColor,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.BORDER_FLASH -> {
                BorderFlashFeedback(
                    isTriggered = isTriggered,
                    color = feedbackColor,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.COLOR_CHANGE -> {
                ColorChangeFeedback(
                    isTriggered = isTriggered,
                    color = feedbackColor,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.SHAKE -> {
                ShakeFeedback(
                    isTriggered = isTriggered,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.BOUNCE -> {
                BounceFeedback(
                    isTriggered = isTriggered,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.PULSE -> {
                PulseFeedback(
                    isTriggered = isTriggered,
                    color = feedbackColor,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.HIGHLIGHT -> {
                HighlightFeedback(
                    isTriggered = isTriggered,
                    color = feedbackColor,
                    duration = duration,
                    content = content
                )
            }
            VisualFeedbackType.SHADOW_ELEVATION -> {
                ShadowElevationFeedback(
                    isTriggered = isTriggered,
                    duration = duration,
                    content = content
                )
            }
        }
    }
}

/**
 * Ripple Feedback
 * Creates a ripple effect on interaction
 */
@Composable
private fun RippleFeedback(
    isTriggered: Boolean,
    color: Color,
    duration: Int,
    content: @Composable () -> Unit
) {
    val animatedRadius by animateFloatAsState(
        targetValue = if (isTriggered) 100f else 0f,
        animationSpec = tween(duration, easing = EaseOutQuart),
        label = "ripple_radius"
    )
    
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isTriggered) 0f else 0.3f,
        animationSpec = tween(duration, easing = EaseOutQuart),
        label = "ripple_alpha"
    )
    
    Box {
        content()
        
        if (isTriggered) {
            Box(
                modifier = Modifier
                    .size((animatedRadius * 2).dp)
                    .alpha(animatedAlpha)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
                    .align(Alignment.Center)
            )
        }
    }
}

/**
 * Scale Feedback
 * Creates a scale effect on interaction
 */
@Composable
private fun ScaleFeedback(
    isTriggered: Boolean,
    duration: Int,
    content: @Composable () -> Unit
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isTriggered) 0.95f else 1f,
        animationSpec = tween(duration / 2, easing = EaseInOutQuart),
        label = "scale_feedback"
    )
    
    Box(
        modifier = Modifier.scale(animatedScale)
    ) {
        content()
    }
}

/**
 * Glow Feedback
 * Creates a glow effect on interaction
 */
@Composable
private fun GlowFeedback(
    isTriggered: Boolean,
    color: Color,
    duration: Int,
    content: @Composable () -> Unit
) {
    val animatedGlow by animateFloatAsState(
        targetValue = if (isTriggered) 1f else 0f,
        animationSpec = tween(duration, easing = EaseInOutQuart),
        label = "glow_feedback"
    )
    
    Box {
        content()
        
        if (animatedGlow > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(animatedGlow * 0.3f)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

/**
 * Border Flash Feedback
 * Creates a border flash effect on interaction
 */
@Composable
private fun BorderFlashFeedback(
    isTriggered: Boolean,
    color: Color,
    duration: Int,
    content: @Composable () -> Unit
) {
    val animatedBorderWidth by animateDpAsState(
        targetValue = if (isTriggered) 3.dp else 0.dp,
        animationSpec = tween(duration, easing = EaseInOutQuart),
        label = "border_flash"
    )
    
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isTriggered) 1f else 0f,
        animationSpec = tween(duration, easing = EaseInOutQuart),
        label = "border_alpha"
    )
    
    Box(
        modifier = Modifier
            .border(
                width = animatedBorderWidth,
                color = color.copy(alpha = animatedAlpha),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content()
    }
}

/**
 * Color Change Feedback
 * Creates a color change effect on interaction
 */
@Composable
private fun ColorChangeFeedback(
    isTriggered: Boolean,
    color: Color,
    duration: Int,
    content: @Composable () -> Unit
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isTriggered) 0.2f else 0f,
        animationSpec = tween(duration, easing = EaseInOutQuart),
        label = "color_change"
    )
    
    Box {
        content()
        
        if (animatedAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(animatedAlpha)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

/**
 * Shake Feedback
 * Creates a shake effect on interaction
 */
@Composable
private fun ShakeFeedback(
    isTriggered: Boolean,
    duration: Int,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shake_transition")
    
    val shakeOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isTriggered) 10f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake_offset"
    )
    
    Box(
        modifier = Modifier.offset(x = if (isTriggered) shakeOffset.dp else 0.dp)
    ) {
        content()
    }
}

/**
 * Bounce Feedback
 * Creates a bounce effect on interaction
 */
@Composable
private fun BounceFeedback(
    isTriggered: Boolean,
    duration: Int,
    content: @Composable () -> Unit
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isTriggered) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bounce_feedback"
    )
    
    Box(
        modifier = Modifier.scale(animatedScale)
    ) {
        content()
    }
}

/**
 * Pulse Feedback
 * Creates a pulse effect on interaction
 */
@Composable
private fun PulseFeedback(
    isTriggered: Boolean,
    color: Color,
    duration: Int,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isTriggered) 0.5f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration / 2, easing = EaseInOutQuart),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )
    
    Box {
        content()
        
        if (isTriggered && pulseAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(pulseAlpha)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

/**
 * Highlight Feedback
 * Creates a highlight effect on interaction
 */
@Composable
private fun HighlightFeedback(
    isTriggered: Boolean,
    color: Color,
    duration: Int,
    content: @Composable () -> Unit
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isTriggered) 0.15f else 0f,
        animationSpec = tween(duration, easing = EaseInOutQuart),
        label = "highlight_feedback"
    )
    
    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = animatedAlpha),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content()
    }
}

/**
 * Shadow Elevation Feedback
 * Creates a shadow elevation effect on interaction
 */
@Composable
private fun ShadowElevationFeedback(
    isTriggered: Boolean,
    duration: Int,
    content: @Composable () -> Unit
) {
    // Note: This would require custom shadow implementation
    // For now, we'll use a simple scale effect as placeholder
    val animatedScale by animateFloatAsState(
        targetValue = if (isTriggered) 1.02f else 1f,
        animationSpec = tween(duration, easing = EaseInOutQuart),
        label = "elevation_feedback"
    )
    
    Box(
        modifier = Modifier.scale(animatedScale)
    ) {
        content()
    }
}

/**
 * Predefined Visual Feedback Components
 */

@Composable
fun RippleClickComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    rippleColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    VisualFeedbackComponent(
        modifier = modifier,
        feedbackType = VisualFeedbackType.RIPPLE,
        enabled = enabled,
        feedbackColor = rippleColor,
        onClick = onClick,
        content = content
    )
}

@Composable
fun ScaleClickComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    VisualFeedbackComponent(
        modifier = modifier,
        feedbackType = VisualFeedbackType.SCALE,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@Composable
fun GlowClickComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    glowColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    VisualFeedbackComponent(
        modifier = modifier,
        feedbackType = VisualFeedbackType.GLOW,
        enabled = enabled,
        feedbackColor = glowColor,
        onClick = onClick,
        content = content
    )
}

@Composable
fun BounceClickComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    VisualFeedbackComponent(
        modifier = modifier,
        feedbackType = VisualFeedbackType.BOUNCE,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}