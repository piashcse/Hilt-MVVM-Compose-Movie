package com.xiaomi.base.ui.kit.components.specialized.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiTheme
import kotlinx.coroutines.delay
import kotlin.math.sin

/**
 * Xiaomi Animation Components - Animated UI Components
 * 
 * This file contains animation-related components following Material Design 3 principles
 * with Xiaomi's design language. These components provide smooth and engaging animations
 * for better user experience.
 */

/**
 * Xiaomi Loading Spinner
 * Customizable loading spinner with Xiaomi branding
 */
@Composable
fun XiaomiLoadingSpinner(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    strokeWidth: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Box(
        modifier = modifier
            .size(size)
            .rotate(rotation),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            strokeWidth = strokeWidth,
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

/**
 * Xiaomi Pulse Animation
 * Pulsing animation for highlighting elements
 */
@Composable
fun XiaomiPulseAnimation(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = 1500,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration / 2, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration / 2, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .graphicsLayer(alpha = alpha)
    ) {
        content()
    }
}

/**
 * Xiaomi Shimmer Effect
 * Shimmer loading effect for content placeholders
 */
@Composable
fun XiaomiShimmerEffect(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(8.dp),
    animationDuration: Int = 1500
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "translate"
    )
    
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    )
    
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = androidx.compose.ui.geometry.Offset(translateAnim - 200f, translateAnim - 200f),
        end = androidx.compose.ui.geometry.Offset(translateAnim, translateAnim)
    )
    
    Box(
        modifier = modifier
            .clip(shape)
            .background(brush)
    )
}

/**
 * Xiaomi Bounce Animation
 * Bounce effect for interactive elements
 */
@Composable
fun XiaomiBounceAnimation(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bounce"
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            ) {
                isPressed = true
                onClick?.invoke()
            }
    ) {
        content()
    }
    
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

/**
 * Xiaomi Floating Action Button with Animation
 * Enhanced FAB with entrance and interaction animations
 */
@Composable
fun XiaomiAnimatedFAB(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    text: String? = null,
    expanded: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val density = LocalDensity.current
    
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically {
            with(density) { 100.dp.roundToPx() }
        } + fadeIn(),
        exit = slideOutVertically {
            with(density) { 100.dp.roundToPx() }
        } + fadeOut()
    ) {
        XiaomiBounceAnimation(
            onClick = onClick
        ) {
            if (text != null && expanded) {
                ExtendedFloatingActionButton(
                    onClick = onClick,
                    modifier = modifier,
                    containerColor = containerColor,
                    contentColor = contentColor
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = text
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = text)
                }
            } else {
                FloatingActionButton(
                    onClick = onClick,
                    modifier = modifier,
                    containerColor = containerColor,
                    contentColor = contentColor
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = text
                    )
                }
            }
        }
    }
}

/**
 * Xiaomi Wave Animation
 * Wave effect for loading or emphasis
 */
@Composable
fun XiaomiWaveAnimation(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    waveCount: Int = 3,
    animationDuration: Int = 2000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        repeat(waveCount) { index ->
            val animationDelay = (animationDuration / waveCount) * index
            
            val scale by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDuration,
                        delayMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                label = "wave_scale_$index"
            )
            
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.7f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDuration,
                        delayMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                label = "wave_alpha_$index"
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scale)
                    .border(
                        width = 2.dp,
                        color = color.copy(alpha = alpha),
                        shape = CircleShape
                    )
            )
        }
    }
}

/**
 * Xiaomi Typing Dots Animation
 * Three dots animation for typing indicators
 */
@Composable
fun XiaomiTypingDots(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    dotSize: Dp = 6.dp,
    animationDuration: Int = 1400
) {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val animationDelay = (animationDuration / 6) * index
            
            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -10f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDuration / 2,
                        delayMillis = animationDelay,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dot_offset_$index"
            )
            
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .offset(y = offsetY.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

/**
 * Xiaomi Progress Wave
 * Animated progress indicator with wave effect
 */
@Composable
fun XiaomiProgressWave(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    animationDuration: Int = 1000
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        ),
        label = "progress"
    )
    
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_offset"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            color,
                            color.copy(alpha = 0.8f),
                            color
                        ),
                        startX = waveOffset * 200f,
                        endX = waveOffset * 200f + 100f
                    )
                )
        )
    }
}

/**
 * Xiaomi Card Flip Animation
 * Card with flip animation between front and back content
 */
@Composable
fun XiaomiFlipCard(
    isFlipped: Boolean,
    modifier: Modifier = Modifier,
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    animationDuration: Int = 600
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        ),
        label = "flip"
    )
    
    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
    ) {
        if (rotation <= 90f) {
            frontContent()
        } else {
            Box(
                modifier = Modifier.graphicsLayer {
                    rotationY = 180f
                }
            ) {
                backContent()
            }
        }
    }
}

/**
 * Xiaomi Slide Reveal Animation
 * Content that slides in with reveal effect
 */
@Composable
fun XiaomiSlideReveal(
    visible: Boolean,
    modifier: Modifier = Modifier,
    direction: SlideDirection = SlideDirection.LEFT_TO_RIGHT,
    animationDuration: Int = 500,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = when (direction) {
            SlideDirection.LEFT_TO_RIGHT -> slideInHorizontally {
                with(density) { -100.dp.roundToPx() }
            } + fadeIn()
            SlideDirection.RIGHT_TO_LEFT -> slideInHorizontally {
                with(density) { 100.dp.roundToPx() }
            } + fadeIn()
            SlideDirection.TOP_TO_BOTTOM -> slideInVertically {
                with(density) { -100.dp.roundToPx() }
            } + fadeIn()
            SlideDirection.BOTTOM_TO_TOP -> slideInVertically {
                with(density) { 100.dp.roundToPx() }
            } + fadeIn()
        },
        exit = when (direction) {
            SlideDirection.LEFT_TO_RIGHT -> slideOutHorizontally {
                with(density) { 100.dp.roundToPx() }
            } + fadeOut()
            SlideDirection.RIGHT_TO_LEFT -> slideOutHorizontally {
                with(density) { -100.dp.roundToPx() }
            } + fadeOut()
            SlideDirection.TOP_TO_BOTTOM -> slideOutVertically {
                with(density) { 100.dp.roundToPx() }
            } + fadeOut()
            SlideDirection.BOTTOM_TO_TOP -> slideOutVertically {
                with(density) { -100.dp.roundToPx() }
            } + fadeOut()
        }
    ) {
        content()
    }
}

enum class SlideDirection {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP
}

// Preview Functions
@Preview(name = "Loading Spinner - Light")
@Composable
fun XiaomiLoadingSpinnerPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Loading Spinner")
                XiaomiLoadingSpinner()
                
                Text("Large Spinner")
                XiaomiLoadingSpinner(size = 60.dp, strokeWidth = 6.dp)
            }
        }
    }
}

@Preview(name = "Pulse Animation - Light")
@Composable
fun XiaomiPulseAnimationPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Pulse Animation")
                XiaomiPulseAnimation {
                    Card(
                        modifier = Modifier.size(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notification",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Shimmer Effect - Light")
@Composable
fun XiaomiShimmerEffectPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Shimmer Loading Effect")
                
                // Simulate loading cards
                repeat(3) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        XiaomiShimmerEffect(
                            modifier = Modifier.size(60.dp),
                            shape = CircleShape
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            XiaomiShimmerEffect(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(16.dp)
                            )
                            XiaomiShimmerEffect(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Wave Animation - Light")
@Composable
fun XiaomiWaveAnimationPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Wave Animation")
                XiaomiWaveAnimation(
                    modifier = Modifier.size(100.dp)
                )
                
                Text("Typing Dots")
                XiaomiTypingDots()
            }
        }
    }
}

@Preview(name = "Animated FAB - Light")
@Composable
fun XiaomiAnimatedFABPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Animated FAB")
                
                XiaomiAnimatedFAB(
                    onClick = {},
                    icon = Icons.Default.Add,
                    text = "Create",
                    expanded = true
                )
                
                XiaomiAnimatedFAB(
                    onClick = {},
                    icon = Icons.Default.Edit
                )
            }
        }
    }
}

@Preview(name = "Progress Wave - Light")
@Composable
fun XiaomiProgressWavePreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Progress Wave")
                
                XiaomiProgressWave(
                    progress = 0.3f,
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiProgressWave(
                    progress = 0.7f,
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiProgressWave(
                    progress = 1f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(name = "Animations - Dark")
@Composable
fun XiaomiAnimationsDarkPreview() {
    XiaomiTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Dark Theme Animations")
                
                XiaomiLoadingSpinner()
                
                XiaomiTypingDots()
                
                XiaomiProgressWave(
                    progress = 0.6f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}