package com.xiaomi.base.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.min

// Basic shapes
fun DrawScope.drawGrid(color: Color, stepSize: Float = 50f, strokeWidth: Float = 1f) {
    val width = size.width
    val height = size.height
    
    // Draw vertical lines
    for (x in 0..width.toInt() step stepSize.toInt()) {
        drawLine(
            color = color.copy(alpha = 0.3f),
            start = Offset(x.toFloat(), 0f),
            end = Offset(x.toFloat(), height),
            strokeWidth = strokeWidth
        )
    }
    
    // Draw horizontal lines
    for (y in 0..height.toInt() step stepSize.toInt()) {
        drawLine(
            color = color.copy(alpha = 0.3f),
            start = Offset(0f, y.toFloat()),
            end = Offset(width, y.toFloat()),
            strokeWidth = strokeWidth
        )
    }
}

fun DrawScope.drawAxes(color: Color, strokeWidth: Float = 2f) {
    val width = size.width
    val height = size.height
    
    // X-axis
    drawLine(
        color = color,
        start = Offset(0f, height - 40f),
        end = Offset(width, height - 40f),
        strokeWidth = strokeWidth
    )
    
    // Y-axis
    drawLine(
        color = color,
        start = Offset(40f, 0f),
        end = Offset(40f, height),
        strokeWidth = strokeWidth
    )
}

// Chart drawing functions
fun DrawScope.drawLineSeries(
    data: List<Pair<Float, Float>>,
    color: Color,
    strokeWidth: Float = 3f,
    xRange: Pair<Float, Float> = Pair(0f, 1f),
    yRange: Pair<Float, Float> = Pair(0f, 1f),
    fillArea: Boolean = false
) {
    if (data.isEmpty()) return
    
    val width = size.width
    val height = size.height
    val padding = 40f
    
    val xMin = xRange.first
    val xMax = xRange.second
    val yMin = yRange.first
    val yMax = yRange.second
    
    val xScale = (width - 2 * padding) / (xMax - xMin)
    val yScale = (height - 2 * padding) / (yMax - yMin)
    
    val path = Path()
    var firstPoint = true
    
    data.forEach { (x, y) ->
        val scaledX = padding + (x - xMin) * xScale
        val scaledY = height - padding - (y - yMin) * yScale
        
        if (firstPoint) {
            path.moveTo(scaledX, scaledY)
            firstPoint = false
        } else {
            path.lineTo(scaledX, scaledY)
        }
    }
    
    // Draw the line
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth)
    )
    
    // Fill the area under the line if requested
    if (fillArea) {
        val fillPath = Path()
        fillPath.addPath(path)
        
        // Add points to close the path at the bottom
        val lastPoint = data.last()
        val lastX = padding + (lastPoint.first - xMin) * xScale
        fillPath.lineTo(lastX, height - padding)
        
        val firstPoint = data.first()
        val firstX = padding + (firstPoint.first - xMin) * xScale
        fillPath.lineTo(firstX, height - padding)
        
        fillPath.close()
        
        drawPath(
            path = fillPath,
            color = color.copy(alpha = 0.2f),
            style = Fill
        )
    }
}

fun DrawScope.drawBarSeries(
    data: List<Pair<Float, Float>>,
    color: Color,
    barWidth: Float = 30f,
    xRange: Pair<Float, Float> = Pair(0f, 1f),
    yRange: Pair<Float, Float> = Pair(0f, 1f)
) {
    if (data.isEmpty()) return
    
    val width = size.width
    val height = size.height
    val padding = 40f
    
    val xMin = xRange.first
    val xMax = xRange.second
    val yMin = yRange.first
    val yMax = yRange.second
    
    val xScale = (width - 2 * padding) / (xMax - xMin)
    val yScale = (height - 2 * padding) / (yMax - yMin)
    
    data.forEach { (x, y) ->
        val scaledX = padding + (x - xMin) * xScale
        val scaledY = height - padding - (y - yMin) * yScale
        
        drawRect(
            color = color,
            topLeft = Offset(scaledX - barWidth / 2, scaledY),
            size = Size(barWidth, height - padding - scaledY)
        )
    }
}

fun DrawScope.drawAreaSeries(
    data: List<Pair<Float, Float>>,
    color: Color,
    xRange: Pair<Float, Float> = Pair(0f, 1f),
    yRange: Pair<Float, Float> = Pair(0f, 1f)
) {
    if (data.isEmpty()) return
    
    val width = size.width
    val height = size.height
    val padding = 40f
    
    val xMin = xRange.first
    val xMax = xRange.second
    val yMin = yRange.first
    val yMax = yRange.second
    
    val xScale = (width - 2 * padding) / (xMax - xMin)
    val yScale = (height - 2 * padding) / (yMax - yMin)
    
    val path = Path()
    
    // Start at the bottom left
    val firstPoint = data.first()
    val firstX = padding + (firstPoint.first - xMin) * xScale
    path.moveTo(firstX, height - padding)
    
    // Draw up to the first point
    path.lineTo(firstX, height - padding - (firstPoint.second - yMin) * yScale)
    
    // Draw through all points
    data.forEach { (x, y) ->
        val scaledX = padding + (x - xMin) * xScale
        val scaledY = height - padding - (y - yMin) * yScale
        path.lineTo(scaledX, scaledY)
    }
    
    // Draw down to the bottom right
    val lastPoint = data.last()
    val lastX = padding + (lastPoint.first - xMin) * xScale
    path.lineTo(lastX, height - padding)
    
    // Close the path
    path.close()
    
    // Fill the area
    drawPath(
        path = path,
        color = color.copy(alpha = 0.3f),
        style = Fill
    )
    
    // Draw the top line
    val linePath = Path()
    var firstLinePath = true
    
    data.forEach { (x, y) ->
        val scaledX = padding + (x - xMin) * xScale
        val scaledY = height - padding - (y - yMin) * yScale
        
        if (firstLinePath) {
            linePath.moveTo(scaledX, scaledY)
            firstLinePath = false
        } else {
            linePath.lineTo(scaledX, scaledY)
        }
    }
    
    drawPath(
        path = linePath,
        color = color,
        style = Stroke(width = 2f)
    )
}

fun DrawScope.drawScatterSeries(
    data: List<Pair<Float, Float>>,
    color: Color,
    pointSize: Float = 8f,
    xRange: Pair<Float, Float> = Pair(0f, 1f),
    yRange: Pair<Float, Float> = Pair(0f, 1f)
) {
    if (data.isEmpty()) return
    
    val width = size.width
    val height = size.height
    val padding = 40f
    
    val xMin = xRange.first
    val xMax = xRange.second
    val yMin = yRange.first
    val yMax = yRange.second
    
    val xScale = (width - 2 * padding) / (xMax - xMin)
    val yScale = (height - 2 * padding) / (yMax - yMin)
    
    data.forEach { (x, y) ->
        val scaledX = padding + (x - xMin) * xScale
        val scaledY = height - padding - (y - yMin) * yScale
        
        drawCircle(
            color = color,
            radius = pointSize,
            center = Offset(scaledX, scaledY)
        )
    }
}

fun DrawScope.drawLegend(
    items: List<Pair<String, Color>>,
    textSize: Float = 12f,
    padding: Float = 10f
) {
    val itemHeight = textSize * 2
    val totalHeight = items.size * itemHeight + (items.size - 1) * padding
    
    var yOffset = (size.height - totalHeight) / 2
    
    items.forEach { (label, color) ->
        // Draw color box
        drawRect(
            color = color,
            topLeft = Offset(size.width - 100f, yOffset),
            size = Size(textSize, textSize)
        )
        
        // Draw label (simplified - in real implementation you'd use drawText)
        // This is a placeholder since drawText requires a Paint object
        drawRect(
            color = Color.Black.copy(alpha = 0.1f),
            topLeft = Offset(size.width - 80f, yOffset),
            size = Size(70f, textSize)
        )
        
        yOffset += itemHeight + padding
    }
}

// Specialized chart components
fun DrawScope.drawMetricLine(
    value: Float,
    maxValue: Float,
    color: Color,
    height: Float = 8f
) {
    val width = size.width
    val filledWidth = (value / maxValue) * width
    
    // Background
    drawRect(
        color = color.copy(alpha = 0.2f),
        topLeft = Offset(0f, 0f),
        size = Size(width, height)
    )
    
    // Filled portion
    drawRect(
        color = color,
        topLeft = Offset(0f, 0f),
        size = Size(filledWidth, height)
    )
}

fun DrawScope.drawMetricMiniChart(
    data: List<Float>,
    color: Color,
    strokeWidth: Float = 2f
) {
    if (data.isEmpty()) return
    
    val width = size.width
    val height = size.height
    
    val maxValue = data.maxOrNull() ?: 1f
    val minValue = data.minOrNull() ?: 0f
    val range = maxOf(maxValue - minValue, 0.01f) // Avoid division by zero
    
    val path = Path()
    val stepX = width / (data.size - 1).coerceAtLeast(1)
    
    data.forEachIndexed { index, value ->
        val x = index * stepX
        val y = height - ((value - minValue) / range) * height
        
        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth)
    )
}

// UI indicators
fun getAlertIcon(severity: String): Int {
    // This would return a drawable resource ID in a real implementation
    // For now, we'll return a placeholder value
    return when (severity.lowercase()) {
        "critical" -> 1 // R.drawable.ic_alert_critical
        "high" -> 2     // R.drawable.ic_alert_high
        "medium" -> 3   // R.drawable.ic_alert_medium
        "low" -> 4      // R.drawable.ic_alert_low
        else -> 5        // R.drawable.ic_alert_info
    }
}

fun getAlertColor(severity: String): Color {
    return when (severity.lowercase()) {
        "critical" -> Color.Red
        "high" -> Color(0xFFFF5722) // Deep Orange
        "medium" -> Color(0xFFFFC107) // Amber
        "low" -> Color(0xFF8BC34A) // Light Green
        else -> Color(0xFF2196F3) // Blue
    }
}

fun getChartTypeIcon(type: String): Int {
    // This would return a drawable resource ID in a real implementation
    // For now, we'll return a placeholder value
    return when (type.lowercase()) {
        "line" -> 1     // R.drawable.ic_chart_line
        "bar" -> 2      // R.drawable.ic_chart_bar
        "pie" -> 3      // R.drawable.ic_chart_pie
        "scatter" -> 4  // R.drawable.ic_chart_scatter
        "area" -> 5     // R.drawable.ic_chart_area
        else -> 6        // R.drawable.ic_chart_default
    }
}

// Candlestick chart for financial data
data class Candlestick(
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val timestamp: Long
)

fun DrawScope.drawCandlestickSeries(
    data: List<Candlestick>,
    upColor: Color = Color.Green,
    downColor: Color = Color.Red,
    wickWidth: Float = 2f,
    bodyWidth: Float = 10f,
    xRange: Pair<Float, Float> = Pair(0f, data.size.toFloat()),
    yRange: Pair<Float, Float>? = null
) {
    if (data.isEmpty()) return
    
    val width = size.width
    val height = size.height
    val padding = 40f
    
    // Calculate y-range if not provided
    val yMin = yRange?.first ?: data.minOf { it.low }
    val yMax = yRange?.second ?: data.maxOf { it.high }
    
    val xMin = xRange.first
    val xMax = xRange.second
    
    val xScale = (width - 2 * padding) / (xMax - xMin)
    val yScale = (height - 2 * padding) / (yMax - yMin)
    
    data.forEachIndexed { index, candle ->
        val x = padding + index * xScale
        
        val highY = height - padding - (candle.high - yMin) * yScale
        val lowY = height - padding - (candle.low - yMin) * yScale
        val openY = height - padding - (candle.open - yMin) * yScale
        val closeY = height - padding - (candle.close - yMin) * yScale
        
        val color = if (candle.close >= candle.open) upColor else downColor
        
        // Draw the wick
        drawLine(
            color = color,
            start = Offset(x, highY),
            end = Offset(x, lowY),
            strokeWidth = wickWidth
        )
        
        // Draw the body
        val topY = minOf(openY, closeY)
        val bottomY = maxOf(openY, closeY)
        val bodyHeight = maxOf(bottomY - topY, 1f) // Ensure at least 1px height
        
        drawRect(
            color = color,
            topLeft = Offset(x - bodyWidth / 2, topY),
            size = Size(bodyWidth, bodyHeight)
        )
    }
}

// Helper extension functions for Rect
fun Rect.left(): Float = this.left
fun Rect.right(): Float = this.right
fun Rect.top(): Float = this.top
fun Rect.bottom(): Float = this.bottom
fun Rect.width(value: Float): Rect = Rect(left, top, left + value, bottom)
fun Rect.height(value: Float): Rect = Rect(left, top, right, top + value)

// Operator extensions for Offset
operator fun Offset.plus(other: Offset): Offset = Offset(this.x + other.x, this.y + other.y)
operator fun Offset.minus(other: Offset): Offset = Offset(this.x - other.x, this.y - other.y)
operator fun Offset.times(factor: Float): Offset = Offset(this.x * factor, this.y * factor)
operator fun Offset.div(factor: Float): Offset = Offset(this.x / factor, this.y / factor)