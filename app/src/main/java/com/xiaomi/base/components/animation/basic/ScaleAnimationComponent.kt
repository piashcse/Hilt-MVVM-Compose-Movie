package com.xiaomi.base.components.animation.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin

/**
 * Scale Animation Component
 * Provides smooth scale in and scale out animations for any composable content
 * 
 * @param visible Controls the visibility state of the content
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param initialScale Initial scale value for scale in animation (0.0 to 1.0)
 * @param targetScale Target scale value for scale out animation (0.0 to 1.0)
 * @param transformOrigin Origin point for the scale transformation
 * @param content The composable content to animate
 */
@Composable
fun ScaleAnimationComponent(
    visible: Boolean,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    initialScale: Float = 0.0f,
    targetScale: Float = 0.0f,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = LinearEasing
            ),
            initialScale = initialScale,
            transformOrigin = transformOrigin
        ),
        exit = scaleOut(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = LinearEasing
            ),
            targetScale = targetScale,
            transformOrigin = transformOrigin
        )
    ) {
        content()
    }
}

/**
 * Spring Scale Animation Component
 * Uses spring animation for more natural scale effects
 * 
 * @param visible Controls the visibility state of the content
 * @param modifier Modifier to be applied to the component
 * @param dampingRatio Damping ratio for the spring animation
 * @param stiffness Stiffness for the spring animation
 * @param initialScale Initial scale value for scale in animation
 * @param targetScale Target scale value for scale out animation
 * @param transformOrigin Origin point for the scale transformation
 * @param content The composable content to animate
 */
@Composable
fun SpringScaleAnimationComponent(
    visible: Boolean,
    modifier: Modifier = Modifier,
    dampingRatio: Float = Spring.DampingRatioMediumBouncy,
    stiffness: Float = Spring.StiffnessMedium,
    initialScale: Float = 0.0f,
    targetScale: Float = 0.0f,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = dampingRatio,
                stiffness = stiffness
            ),
            initialScale = initialScale,
            transformOrigin = transformOrigin
        ),
        exit = scaleOut(
            animationSpec = spring(
                dampingRatio = dampingRatio,
                stiffness = stiffness
            ),
            targetScale = targetScale,
            transformOrigin = transformOrigin
        )
    ) {
        content()
    }
}

/**
 * Auto Scale Animation Component with state management
 * Automatically manages visibility state with toggle functionality
 * 
 * @param initialVisible Initial visibility state
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param initialScale Initial scale value for scale in animation
 * @param targetScale Target scale value for scale out animation
 * @param transformOrigin Origin point for the scale transformation
 * @param onVisibilityChanged Callback when visibility changes
 * @param content The composable content to animate with toggle function
 */
@Composable
fun AutoScaleAnimationComponent(
    initialVisible: Boolean = true,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    initialScale: Float = 0.0f,
    targetScale: Float = 0.0f,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
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
        ScaleAnimationComponent(
            visible = visible,
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            initialScale = initialScale,
            targetScale = targetScale,
            transformOrigin = transformOrigin
        ) {
            content(toggleVisibility)
        }
    }
}

/**
 * Custom Scale Animation Component with easing control
 * Provides more control over animation easing and timing
 * 
 * @param visible Controls the visibility state of the content
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param initialScale Initial scale value for scale in animation
 * @param targetScale Target scale value for scale out animation
 * @param transformOrigin Origin point for the scale transformation
 * @param easing Custom easing function for the animation
 * @param content The composable content to animate
 */
@Composable
fun CustomScaleAnimationComponent(
    visible: Boolean,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    initialScale: Float = 0.0f,
    targetScale: Float = 0.0f,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    easing: Easing = LinearEasing,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing
            ),
            initialScale = initialScale,
            transformOrigin = transformOrigin
        ),
        exit = scaleOut(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing
            ),
            targetScale = targetScale,
            transformOrigin = transformOrigin
        )
    ) {
        content()
    }
}

/**
 * Bounce Scale Animation Component
 * Creates a bouncy scale effect using predefined spring settings
 * 
 * @param visible Controls the visibility state of the content
 * @param modifier Modifier to be applied to the component
 * @param bounceIntensity Intensity of the bounce effect (Low, Medium, High)
 * @param initialScale Initial scale value for scale in animation
 * @param targetScale Target scale value for scale out animation
 * @param transformOrigin Origin point for the scale transformation
 * @param content The composable content to animate
 */
@Composable
fun BounceScaleAnimationComponent(
    visible: Boolean,
    modifier: Modifier = Modifier,
    bounceIntensity: BounceIntensity = BounceIntensity.Medium,
    initialScale: Float = 0.0f,
    targetScale: Float = 0.0f,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    content: @Composable () -> Unit
) {
    val (dampingRatio, stiffness) = when (bounceIntensity) {
        BounceIntensity.Low -> Spring.DampingRatioLowBouncy to Spring.StiffnessLow
        BounceIntensity.Medium -> Spring.DampingRatioMediumBouncy to Spring.StiffnessMedium
        BounceIntensity.High -> Spring.DampingRatioHighBouncy to Spring.StiffnessHigh
    }
    
    SpringScaleAnimationComponent(
        visible = visible,
        modifier = modifier,
        dampingRatio = dampingRatio,
        stiffness = stiffness,
        initialScale = initialScale,
        targetScale = targetScale,
        transformOrigin = transformOrigin,
        content = content
    )
}

/**
 * Bounce Intensity enum for controlling bounce effect strength
 */
enum class BounceIntensity {
    Low,
    Medium,
    High
}