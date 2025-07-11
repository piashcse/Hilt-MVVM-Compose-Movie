package com.xiaomi.base.ui.kit.components.communication.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * Xiaomi Base UI Kit Linear Progress Indicator
 * 
 * A Material Design 3 linear progress indicator with Xiaomi design tokens.
 * Shows progress along a horizontal line.
 * 
 * @param progress The progress of this progress indicator, where 0.0 represents no progress and 1.0 represents full progress
 * @param modifier Modifier to be applied to the progress indicator
 * @param color The color of the progress indicator
 * @param trackColor The color of the track behind the indicator, visible when the progress has not reached the area of the overall indicator yet
 * @param strokeCap The stroke cap to use for the progress indicator
 */
@Composable
fun XiaomiLinearProgress(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier,
        color = color,
        trackColor = trackColor,
        strokeCap = strokeCap
    )
}

/**
 * Xiaomi Base UI Kit Indeterminate Linear Progress Indicator
 * 
 * An indeterminate linear progress indicator that shows continuous animation.
 */
@Composable
fun XiaomiLinearProgressIndeterminate(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap
) {
    LinearProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor,
        strokeCap = strokeCap
    )
}

/**
 * Xiaomi Base UI Kit Circular Progress Indicator
 * 
 * A Material Design 3 circular progress indicator with Xiaomi design tokens.
 * Shows progress in a circular form.
 * 
 * @param progress The progress of this progress indicator, where 0.0 represents no progress and 1.0 represents full progress
 * @param modifier Modifier to be applied to the progress indicator
 * @param color The color of the progress indicator
 * @param strokeWidth The stroke width for the progress indicator
 * @param trackColor The color of the track behind the indicator
 * @param strokeCap The stroke cap to use for the progress indicator
 */
@Composable
fun XiaomiCircularProgress(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
) {
    CircularProgressIndicator(
        progress = progress,
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth,
        trackColor = trackColor,
        strokeCap = strokeCap
    )
}

/**
 * Xiaomi Base UI Kit Indeterminate Circular Progress Indicator
 * 
 * An indeterminate circular progress indicator that shows continuous animation.
 */
@Composable
fun XiaomiCircularProgressIndeterminate(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth,
        strokeCap = strokeCap
    )
}

/**
 * Xiaomi Custom Circular Progress with Text
 * 
 * A custom circular progress indicator that displays percentage text in the center.
 * 
 * @param progress The progress value between 0.0 and 1.0
 * @param modifier Modifier to be applied to the progress indicator
 * @param size The size of the circular progress indicator
 * @param strokeWidth The width of the progress stroke
 * @param color The color of the progress indicator
 * @param trackColor The color of the track behind the indicator
 * @param showPercentage Whether to show percentage text in the center
 * @param animationDuration Duration of the progress animation in milliseconds
 */
@Composable
fun XiaomiCircularProgressWithText(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    strokeWidth: Dp = 8.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    showPercentage: Boolean = true,
    animationDuration: Int = 1000
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = animationDuration),
        label = "progress"
    )
    
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
                .progressSemantics(progress)
        ) {
            drawCircularProgress(
                progress = animatedProgress,
                strokeWidth = strokeWidth,
                color = color,
                trackColor = trackColor
            )
        }
        
        if (showPercentage) {
            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Helper function to draw circular progress
 */
private fun DrawScope.drawCircularProgress(
    progress: Float,
    strokeWidth: Dp,
    color: Color,
    trackColor: Color
) {
    val strokeWidthPx = strokeWidth.toPx()
    val diameter = size.minDimension
    val radius = (diameter - strokeWidthPx) / 2
    val center = Offset(size.width / 2, size.height / 2)
    
    // Draw track
    drawCircle(
        color = trackColor,
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
    )
    
    // Draw progress
    val sweepAngle = progress * 360f
    if (sweepAngle > 0) {
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(
                center.x - radius,
                center.y - radius
            ),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )
    }
}

/**
 * Xiaomi Step Progress Indicator
 * 
 * A custom step-based progress indicator showing discrete steps.
 * 
 * @param currentStep The current active step (0-based)
 * @param totalSteps The total number of steps
 * @param modifier Modifier to be applied to the progress indicator
 * @param activeColor Color for completed and current steps
 * @param inactiveColor Color for incomplete steps
 * @param stepSize Size of each step indicator
 */
@Composable
fun XiaomiStepProgress(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    stepSize: Dp = 12.dp
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { index ->
            val isActive = index <= currentStep
            val stepColor = if (isActive) activeColor else inactiveColor
            
            Canvas(
                modifier = Modifier.size(stepSize)
            ) {
                drawCircle(
                    color = stepColor,
                    radius = size.minDimension / 2
                )
            }
            
            // Draw connecting line (except for last step)
            if (index < totalSteps - 1) {
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                ) {
                    val lineColor = if (index < currentStep) activeColor else inactiveColor
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = size.height
                    )
                }
            }
        }
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Progress - Light")
@Composable
fun XiaomiProgressPreview() {
    XiaomiPreviewTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp)
        ) {
            // Linear Progress
            Text("Linear Progress", style = MaterialTheme.typography.titleMedium)
            XiaomiLinearProgress(
                progress = { 0.7f },
                modifier = Modifier.fillMaxWidth()
            )
            
            XiaomiLinearProgressIndeterminate(
                modifier = Modifier.fillMaxWidth()
            )
            
            // Circular Progress
            Text("Circular Progress", style = MaterialTheme.typography.titleMedium)
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                XiaomiCircularProgress(
                    progress = { 0.7f },
                    modifier = Modifier.size(48.dp)
                )
                
                XiaomiCircularProgressIndeterminate(
                    modifier = Modifier.size(48.dp)
                )
                
                XiaomiCircularProgressWithText(
                    progress = 0.75f,
                    size = 80.dp
                )
            }
            
            // Step Progress
            Text("Step Progress", style = MaterialTheme.typography.titleMedium)
            XiaomiStepProgress(
                currentStep = 2,
                totalSteps = 5,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "Xiaomi Progress - Dark")
@Composable
fun XiaomiProgressDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp)
        ) {
            XiaomiLinearProgress(
                progress = { 0.5f },
                modifier = Modifier.fillMaxWidth()
            )
            
            XiaomiCircularProgressWithText(
                progress = 0.6f,
                size = 100.dp
            )
            
            XiaomiStepProgress(
                currentStep = 1,
                totalSteps = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}