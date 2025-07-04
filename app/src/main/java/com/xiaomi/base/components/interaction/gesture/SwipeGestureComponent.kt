package com.xiaomi.base.components.interaction.gesture

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Swipe Direction
 * Represents the direction of a swipe gesture
 */
enum class SwipeDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    NONE
}

/**
 * Swipe Gesture Configuration
 * Configuration for swipe gesture detection
 */
data class SwipeGestureConfig(
    val threshold: Float = 100f, // Minimum distance to trigger swipe
    val velocityThreshold: Float = 300f, // Minimum velocity to trigger swipe
    val enableHorizontal: Boolean = true,
    val enableVertical: Boolean = true,
    val enableDrag: Boolean = false, // Allow dragging during swipe
    val resetOnRelease: Boolean = true // Reset position when gesture ends
)

/**
 * Swipe Gesture State
 * Holds the current state of swipe gesture
 */
data class SwipeGestureState(
    val offset: Offset = Offset.Zero,
    val direction: SwipeDirection = SwipeDirection.NONE,
    val isActive: Boolean = false,
    val progress: Float = 0f // Progress from 0 to 1
)

/**
 * Swipe Gesture Component
 * Detects swipe gestures and provides callbacks
 * 
 * @param modifier Modifier to be applied to the component
 * @param config Configuration for swipe gesture detection
 * @param onSwipe Callback when a swipe is detected
 * @param onSwipeStart Callback when swipe starts
 * @param onSwipeEnd Callback when swipe ends
 * @param onDrag Callback during drag (if enabled)
 * @param content The composable content
 */
@Composable
fun SwipeGestureComponent(
    modifier: Modifier = Modifier,
    config: SwipeGestureConfig = SwipeGestureConfig(),
    onSwipe: ((SwipeDirection, Float) -> Unit)? = null,
    onSwipeStart: ((Offset) -> Unit)? = null,
    onSwipeEnd: ((SwipeDirection, Float) -> Unit)? = null,
    onDrag: ((Offset) -> Unit)? = null,
    content: @Composable (SwipeGestureState) -> Unit
) {
    var gestureState by remember { mutableStateOf(SwipeGestureState()) }
    var startPosition by remember { mutableStateOf(Offset.Zero) }
    
    val density = LocalDensity.current
    val thresholdPx = with(density) { config.threshold.dp.toPx() }
    
    Box(
        modifier = modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { offset ->
                    startPosition = offset
                    gestureState = gestureState.copy(
                        isActive = true,
                        offset = Offset.Zero
                    )
                    onSwipeStart?.invoke(offset)
                },
                onDragEnd = {
                    val finalOffset = gestureState.offset
                    val direction = determineSwipeDirection(
                        finalOffset,
                        thresholdPx,
                        config
                    )
                    val distance = calculateSwipeDistance(finalOffset)
                    
                    if (direction != SwipeDirection.NONE && distance >= thresholdPx) {
                        onSwipe?.invoke(direction, distance)
                    }
                    
                    onSwipeEnd?.invoke(direction, distance)
                    
                    gestureState = if (config.resetOnRelease) {
                        SwipeGestureState()
                    } else {
                        gestureState.copy(isActive = false)
                    }
                },
                onDrag = { change, dragAmount ->
                    val newOffset = gestureState.offset + dragAmount
                    val direction = determineSwipeDirection(
                        newOffset,
                        thresholdPx,
                        config
                    )
                    val distance = calculateSwipeDistance(newOffset)
                    val progress = (distance / thresholdPx).coerceIn(0f, 1f)
                    
                    gestureState = gestureState.copy(
                        offset = newOffset,
                        direction = direction,
                        progress = progress
                    )
                    
                    if (config.enableDrag) {
                        onDrag?.invoke(newOffset)
                    }
                }
            )
        }
    ) {
        content(gestureState)
    }
}

/**
 * Swipe to Dismiss Component
 * Component that can be swiped to dismiss
 * 
 * @param modifier Modifier to be applied to the component
 * @param dismissThreshold Threshold for dismissal (0.0 to 1.0)
 * @param directions Allowed swipe directions for dismissal
 * @param onDismiss Callback when item is dismissed
 * @param content The composable content
 */
@Composable
fun SwipeToDismissComponent(
    modifier: Modifier = Modifier,
    dismissThreshold: Float = 0.3f,
    directions: Set<SwipeDirection> = setOf(SwipeDirection.LEFT, SwipeDirection.RIGHT),
    onDismiss: (SwipeDirection) -> Unit,
    content: @Composable (SwipeGestureState) -> Unit
) {
    SwipeGestureComponent(
        modifier = modifier,
        config = SwipeGestureConfig(
            threshold = 150f,
            enableDrag = true,
            resetOnRelease = false
        ),
        onSwipeEnd = { direction, distance ->
            if (direction in directions) {
                val progress = distance / 300f // Adjust based on screen width
                if (progress >= dismissThreshold) {
                    onDismiss(direction)
                }
            }
        },
        content = content
    )
}

/**
 * Swipe to Action Component
 * Component that reveals actions when swiped
 * 
 * @param modifier Modifier to be applied to the component
 * @param leftAction Action revealed when swiping right
 * @param rightAction Action revealed when swiping left
 * @param actionThreshold Threshold to trigger action
 * @param content The main content
 */
@Composable
fun SwipeToActionComponent(
    modifier: Modifier = Modifier,
    leftAction: (@Composable () -> Unit)? = null,
    rightAction: (@Composable () -> Unit)? = null,
    actionThreshold: Float = 0.25f,
    onLeftAction: (() -> Unit)? = null,
    onRightAction: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    SwipeGestureComponent(
        modifier = modifier,
        config = SwipeGestureConfig(
            threshold = 80f,
            enableDrag = true,
            enableVertical = false
        ),
        onSwipeEnd = { direction, distance ->
            val progress = distance / 200f
            if (progress >= actionThreshold) {
                when (direction) {
                    SwipeDirection.RIGHT -> onLeftAction?.invoke()
                    SwipeDirection.LEFT -> onRightAction?.invoke()
                    else -> {}
                }
            }
        }
    ) { state ->
        Box {
            // Background actions
            if (state.offset.x > 0 && leftAction != null) {
                leftAction()
            } else if (state.offset.x < 0 && rightAction != null) {
                rightAction()
            }
            
            // Main content with offset
            Box(
                modifier = Modifier.offset {
                    IntOffset(
                        x = if (state.isActive) state.offset.x.roundToInt() else 0,
                        y = 0
                    )
                }
            ) {
                content()
            }
        }
    }
}

/**
 * Pull to Refresh Component
 * Component that implements pull-to-refresh pattern
 * 
 * @param modifier Modifier to be applied to the component
 * @param isRefreshing Whether refresh is currently active
 * @param onRefresh Callback when refresh is triggered
 * @param refreshThreshold Threshold to trigger refresh
 * @param content The scrollable content
 */
@Composable
fun PullToRefreshComponent(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit,
    refreshThreshold: Float = 100f,
    refreshIndicator: @Composable (Float) -> Unit = { progress ->
        // Default refresh indicator
        Box(
            modifier = Modifier.offset(y = (progress * 50).dp)
        ) {
            // Add your refresh indicator here
        }
    },
    content: @Composable () -> Unit
) {
    SwipeGestureComponent(
        modifier = modifier,
        config = SwipeGestureConfig(
            threshold = refreshThreshold,
            enableHorizontal = false,
            enableDrag = true
        ),
        onSwipeEnd = { direction, distance ->
            if (direction == SwipeDirection.DOWN && distance >= refreshThreshold) {
                onRefresh()
            }
        }
    ) { state ->
        Box {
            // Refresh indicator
            if (state.direction == SwipeDirection.DOWN && state.progress > 0) {
                refreshIndicator(state.progress)
            }
            
            // Main content
            content()
        }
    }
}

/**
 * Directional Swipe Component
 * Component that handles specific directional swipes
 * 
 * @param modifier Modifier to be applied to the component
 * @param direction Specific direction to detect
 * @param threshold Distance threshold for detection
 * @param onSwipe Callback when swipe in specified direction is detected
 * @param content The composable content
 */
@Composable
fun DirectionalSwipeComponent(
    modifier: Modifier = Modifier,
    direction: SwipeDirection,
    threshold: Float = 100f,
    onSwipe: (Float) -> Unit,
    content: @Composable () -> Unit
) {
    SwipeGestureComponent(
        modifier = modifier,
        config = SwipeGestureConfig(
            threshold = threshold,
            enableHorizontal = direction in setOf(SwipeDirection.LEFT, SwipeDirection.RIGHT),
            enableVertical = direction in setOf(SwipeDirection.UP, SwipeDirection.DOWN)
        ),
        onSwipe = { detectedDirection, distance ->
            if (detectedDirection == direction) {
                onSwipe(distance)
            }
        }
    ) { _ ->
        content()
    }
}

/**
 * Helper Functions
 */
private fun determineSwipeDirection(
    offset: Offset,
    threshold: Float,
    config: SwipeGestureConfig
): SwipeDirection {
    val absX = abs(offset.x)
    val absY = abs(offset.y)
    
    return when {
        config.enableHorizontal && absX > absY && absX > threshold -> {
            if (offset.x > 0) SwipeDirection.RIGHT else SwipeDirection.LEFT
        }
        config.enableVertical && absY > absX && absY > threshold -> {
            if (offset.y > 0) SwipeDirection.DOWN else SwipeDirection.UP
        }
        else -> SwipeDirection.NONE
    }
}

private fun calculateSwipeDistance(offset: Offset): Float {
    return kotlin.math.sqrt(offset.x * offset.x + offset.y * offset.y)
}

/**
 * Swipe Gesture Utilities
 */
class SwipeGestureUtils {
    companion object {
        /**
         * Create a swipe gesture config for horizontal swipes only
         */
        fun horizontalSwipeConfig(
            threshold: Float = 100f,
            enableDrag: Boolean = false
        ) = SwipeGestureConfig(
            threshold = threshold,
            enableHorizontal = true,
            enableVertical = false,
            enableDrag = enableDrag
        )
        
        /**
         * Create a swipe gesture config for vertical swipes only
         */
        fun verticalSwipeConfig(
            threshold: Float = 100f,
            enableDrag: Boolean = false
        ) = SwipeGestureConfig(
            threshold = threshold,
            enableHorizontal = false,
            enableVertical = true,
            enableDrag = enableDrag
        )
        
        /**
         * Create a swipe gesture config for dismiss gestures
         */
        fun dismissSwipeConfig(
            threshold: Float = 150f
        ) = SwipeGestureConfig(
            threshold = threshold,
            enableDrag = true,
            resetOnRelease = false
        )
        
        /**
         * Calculate swipe velocity
         */
        fun calculateVelocity(
            startTime: Long,
            endTime: Long,
            distance: Float
        ): Float {
            val timeDiff = (endTime - startTime) / 1000f // Convert to seconds
            return if (timeDiff > 0) distance / timeDiff else 0f
        }
    }
}