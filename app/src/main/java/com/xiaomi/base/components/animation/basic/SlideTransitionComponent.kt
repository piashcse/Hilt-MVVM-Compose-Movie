package com.xiaomi.base.components.animation.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Easing
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

/**
 * Slide Direction enum for controlling slide animation direction
 */
enum class SlideDirection {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP
}

/**
 * Slide Transition Component
 * Provides smooth slide in and slide out animations for any composable content
 * 
 * @param visible Controls the visibility state of the content
 * @param direction Direction of the slide animation
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param slideDistance Distance to slide in pixels (default: full width/height)
 * @param content The composable content to animate
 */
@Composable
fun SlideTransitionComponent(
    visible: Boolean,
    direction: SlideDirection = SlideDirection.LEFT_TO_RIGHT,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    slideDistance: Int? = null,
    content: @Composable () -> Unit
) {
    val animationSpec = tween<IntOffset>(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = LinearEasing
    )
    
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = when (direction) {
            SlideDirection.LEFT_TO_RIGHT -> slideInHorizontally(
                animationSpec = animationSpec,
                initialOffsetX = { slideDistance ?: -it }
            )
            SlideDirection.RIGHT_TO_LEFT -> slideInHorizontally(
                animationSpec = animationSpec,
                initialOffsetX = { slideDistance ?: it }
            )
            SlideDirection.TOP_TO_BOTTOM -> slideInVertically(
                animationSpec = animationSpec,
                initialOffsetY = { slideDistance ?: -it }
            )
            SlideDirection.BOTTOM_TO_TOP -> slideInVertically(
                animationSpec = animationSpec,
                initialOffsetY = { slideDistance ?: it }
            )
        },
        exit = when (direction) {
            SlideDirection.LEFT_TO_RIGHT -> slideOutHorizontally(
                animationSpec = animationSpec,
                targetOffsetX = { slideDistance ?: it }
            )
            SlideDirection.RIGHT_TO_LEFT -> slideOutHorizontally(
                animationSpec = animationSpec,
                targetOffsetX = { slideDistance ?: -it }
            )
            SlideDirection.TOP_TO_BOTTOM -> slideOutVertically(
                animationSpec = animationSpec,
                targetOffsetY = { slideDistance ?: it }
            )
            SlideDirection.BOTTOM_TO_TOP -> slideOutVertically(
                animationSpec = animationSpec,
                targetOffsetY = { slideDistance ?: -it }
            )
        }
    ) {
        content()
    }
}

/**
 * Auto Slide Transition Component with state management
 * Automatically manages visibility state with toggle functionality
 * 
 * @param initialVisible Initial visibility state
 * @param direction Direction of the slide animation
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param slideDistance Distance to slide in pixels
 * @param onVisibilityChanged Callback when visibility changes
 * @param content The composable content to animate with toggle function
 */
@Composable
fun AutoSlideTransitionComponent(
    initialVisible: Boolean = true,
    direction: SlideDirection = SlideDirection.LEFT_TO_RIGHT,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    slideDistance: Int? = null,
    onVisibilityChanged: ((Boolean) -> Unit)? = null,
    content: @Composable (toggleVisibility: () -> Unit) -> Unit
) {
    var visible by remember { mutableStateOf(initialVisible) }
    
    val toggleVisibility: () -> Unit = {
        visible = !visible
        onVisibilityChanged?.invoke(visible)
        Unit
    }
    
    Box(modifier = modifier) {
        SlideTransitionComponent(
            visible = visible,
            direction = direction,
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            slideDistance = slideDistance
        ) {
            content(toggleVisibility)
        }
    }
}

/**
 * Custom Slide Transition Component with easing control
 * Provides more control over animation easing and timing
 * 
 * @param visible Controls the visibility state of the content
 * @param direction Direction of the slide animation
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param slideDistance Distance to slide in pixels
 * @param easing Custom easing function for the animation
 * @param content The composable content to animate
 */
@Composable
fun CustomSlideTransitionComponent(
    visible: Boolean,
    direction: SlideDirection = SlideDirection.LEFT_TO_RIGHT,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    slideDistance: Int? = null,
    easing: Easing = LinearEasing,
    content: @Composable () -> Unit
) {
    val animationSpec = tween<IntOffset>(
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        easing = easing
    )
    
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = when (direction) {
            SlideDirection.LEFT_TO_RIGHT -> slideInHorizontally(
                animationSpec = animationSpec,
                initialOffsetX = { slideDistance ?: -it }
            )
            SlideDirection.RIGHT_TO_LEFT -> slideInHorizontally(
                animationSpec = animationSpec,
                initialOffsetX = { slideDistance ?: it }
            )
            SlideDirection.TOP_TO_BOTTOM -> slideInVertically(
                animationSpec = animationSpec,
                initialOffsetY = { slideDistance ?: -it }
            )
            SlideDirection.BOTTOM_TO_TOP -> slideInVertically(
                animationSpec = animationSpec,
                initialOffsetY = { slideDistance ?: it }
            )
        },
        exit = when (direction) {
            SlideDirection.LEFT_TO_RIGHT -> slideOutHorizontally(
                animationSpec = animationSpec,
                targetOffsetX = { slideDistance ?: it }
            )
            SlideDirection.RIGHT_TO_LEFT -> slideOutHorizontally(
                animationSpec = animationSpec,
                targetOffsetX = { slideDistance ?: -it }
            )
            SlideDirection.TOP_TO_BOTTOM -> slideOutVertically(
                animationSpec = animationSpec,
                targetOffsetY = { slideDistance ?: it }
            )
            SlideDirection.BOTTOM_TO_TOP -> slideOutVertically(
                animationSpec = animationSpec,
                targetOffsetY = { slideDistance ?: -it }
            )
        }
    ) {
        content()
    }
}