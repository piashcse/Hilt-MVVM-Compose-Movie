package com.xiaomi.base.utils

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*
import kotlin.random.Random

// Time formatting utilities
fun formatTimestamp(timestamp: Long, pattern: String = "MMM dd, yyyy HH:mm"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(Date(timestamp))
}

fun formatDuration(durationMs: Long): String {
    val seconds = durationMs / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    
    return when {
        days > 0 -> "${days}d ${hours % 24}h"
        hours > 0 -> "${hours}h ${minutes % 60}m"
        minutes > 0 -> "${minutes}m ${seconds % 60}s"
        else -> "${seconds}s"
    }
}

fun formatRelativeTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    
    return when {
        days > 0 -> "${days} day${if (days > 1) "s" else ""} ago"
        hours > 0 -> "${hours} hour${if (hours > 1) "s" else ""} ago"
        minutes > 0 -> "${minutes} minute${if (minutes > 1) "s" else ""} ago"
        else -> "Just now"
    }
}

// Performance calculation utilities
fun calculateHealthScore(metrics: Map<String, Double>): Double {
    if (metrics.isEmpty()) return 0.0
    
    val weights = mapOf(
        "cpu" to 0.3,
        "memory" to 0.3,
        "battery" to 0.2,
        "network" to 0.1,
        "storage" to 0.1
    )
    
    var totalScore = 0.0
    var totalWeight = 0.0
    
    metrics.forEach { (key, value) ->
        val weight = weights[key] ?: 0.1
        totalScore += value * weight
        totalWeight += weight
    }
    
    return if (totalWeight > 0) totalScore / totalWeight else 0.0
}

fun calculateTrend(values: List<Double>): String {
    if (values.size < 2) return "stable"
    
    val recent = values.takeLast(min(5, values.size))
    val older = values.dropLast(min(5, values.size)).takeLast(min(5, values.size))
    
    if (older.isEmpty()) return "stable"
    
    val recentAvg = recent.average()
    val olderAvg = older.average()
    
    val change = (recentAvg - olderAvg) / olderAvg
    
    return when {
        change > 0.1 -> "increasing"
        change < -0.1 -> "decreasing"
        else -> "stable"
    }
}

fun calculateCorrelation(x: List<Double>, y: List<Double>): Double {
    if (x.size != y.size || x.isEmpty()) return 0.0
    
    val n = x.size
    val meanX = x.average()
    val meanY = y.average()
    
    var numerator = 0.0
    var sumXSquared = 0.0
    var sumYSquared = 0.0
    
    for (i in x.indices) {
        val deltaX = x[i] - meanX
        val deltaY = y[i] - meanY
        numerator += deltaX * deltaY
        sumXSquared += deltaX * deltaX
        sumYSquared += deltaY * deltaY
    }
    
    val denominator = sqrt(sumXSquared * sumYSquared)
    return if (denominator != 0.0) numerator / denominator else 0.0
}

// Data generation utilities
fun generateSampleAccessibilitySettings(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "feature" to "SCREEN_READER",
            "enabled" to true,
            "level" to "ENHANCED",
            "description" to "Screen reader support for visually impaired users"
        ),
        mapOf(
            "feature" to "HIGH_CONTRAST",
            "enabled" to false,
            "level" to "BASIC",
            "description" to "High contrast mode for better visibility"
        ),
        mapOf(
            "feature" to "LARGE_TEXT",
            "enabled" to true,
            "level" to "FULL",
            "description" to "Large text support for better readability"
        )
    )
}

fun generateSampleMetrics(): Map<String, Double> {
    return mapOf(
        "cpu" to Random.nextDouble(0.1, 1.0),
        "memory" to Random.nextDouble(0.2, 0.9),
        "battery" to Random.nextDouble(0.3, 1.0),
        "network" to Random.nextDouble(0.5, 1.0),
        "storage" to Random.nextDouble(0.4, 0.95)
    )
}

fun generateSampleAlerts(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "id" to "alert1",
            "type" to "performance",
            "severity" to "high",
            "message" to "High CPU usage detected",
            "timestamp" to System.currentTimeMillis()
        ),
        mapOf(
            "id" to "alert2",
            "type" to "memory",
            "severity" to "medium",
            "message" to "Memory usage above threshold",
            "timestamp" to System.currentTimeMillis() - 300000
        )
    )
}

fun generateSampleProfilerSessions(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "id" to "session1",
            "name" to "Main Activity Profiling",
            "duration" to 120000L,
            "status" to "completed",
            "timestamp" to System.currentTimeMillis() - 3600000
        ),
        mapOf(
            "id" to "session2",
            "name" to "Background Task Analysis",
            "duration" to 300000L,
            "status" to "running",
            "timestamp" to System.currentTimeMillis() - 1800000
        )
    )
}

fun generateSampleOptimizations(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "id" to "opt1",
            "type" to "memory",
            "title" to "Reduce Memory Allocations",
            "description" to "Optimize object creation in hot paths",
            "impact" to "high",
            "effort" to "medium"
        ),
        mapOf(
            "id" to "opt2",
            "type" to "cpu",
            "title" to "Optimize Rendering",
            "description" to "Reduce overdraw in complex layouts",
            "impact" to "medium",
            "effort" to "low"
        )
    )
}

fun generateSampleReports(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "id" to "report1",
            "name" to "Performance Analysis Report",
            "type" to "performance",
            "status" to "completed",
            "timestamp" to System.currentTimeMillis() - 86400000,
            "summary" to "Overall performance is good with some optimization opportunities"
        ),
        mapOf(
            "id" to "report2",
            "name" to "Memory Usage Report",
            "type" to "memory",
            "status" to "generating",
            "timestamp" to System.currentTimeMillis() - 3600000,
            "summary" to "Analyzing memory patterns and leak detection"
        )
    )
}

fun generateSampleDataSeries(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "name" to "CPU Usage",
            "data" to (1..20).map { Random.nextDouble(10.0, 80.0) },
            "color" to "#FF6B6B"
        ),
        mapOf(
            "name" to "Memory Usage",
            "data" to (1..20).map { Random.nextDouble(20.0, 90.0) },
            "color" to "#4ECDC4"
        ),
        mapOf(
            "name" to "Network Usage",
            "data" to (1..20).map { Random.nextDouble(5.0, 60.0) },
            "color" to "#45B7D1"
        )
    )
}

fun generateSampleChartConfigs(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "type" to "line",
            "title" to "Performance Metrics Over Time",
            "xAxis" to "Time",
            "yAxis" to "Percentage",
            "showLegend" to true,
            "showGrid" to true
        ),
        mapOf(
            "type" to "bar",
            "title" to "Resource Usage Comparison",
            "xAxis" to "Resource Type",
            "yAxis" to "Usage (%)",
            "showLegend" to false,
            "showGrid" to true
        )
    )
}

fun generateSampleData(): List<Map<String, Any>> {
    return listOf(
        mapOf(
            "timestamp" to System.currentTimeMillis(),
            "value" to Random.nextDouble(0.0, 100.0),
            "category" to "performance"
        ),
        mapOf(
            "timestamp" to System.currentTimeMillis() - 60000,
            "value" to Random.nextDouble(0.0, 100.0),
            "category" to "memory"
        ),
        mapOf(
            "timestamp" to System.currentTimeMillis() - 120000,
            "value" to Random.nextDouble(0.0, 100.0),
            "category" to "network"
        )
    )
}

// Color utilities
fun getAccessibilityColor(level: String): Color {
    return when (level.lowercase()) {
        "none" -> Color.Gray
        "basic" -> Color.Blue
        "enhanced" -> Color.Green
        "full" -> Color(0xFF4CAF50)
        else -> Color.Gray
    }
}

fun getPerformanceLevelColor(level: String): Color {
    return when (level.lowercase()) {
        "excellent" -> Color.Green
        "good" -> Color(0xFF8BC34A)
        "fair" -> Color(0xFFFFEB3B)
        "poor" -> Color(0xFFFF9800)
        "critical" -> Color.Red
        else -> Color.Gray
    }
}

// String utilities
fun truncateText(text: String, maxLength: Int): String {
    return if (text.length <= maxLength) text else "${text.take(maxLength - 3)}..."
}

fun capitalizeWords(text: String): String {
    return text.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

// Validation utilities
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidUrl(url: String): Boolean {
    return android.util.Patterns.WEB_URL.matcher(url).matches()
}

// Math utilities
fun clamp(value: Double, min: Double, max: Double): Double {
    return maxOf(min, minOf(max, value))
}

fun lerp(start: Double, end: Double, fraction: Double): Double {
    return start + fraction * (end - start)
}

fun smoothstep(edge0: Double, edge1: Double, x: Double): Double {
    val t = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0)
    return t * t * (3.0 - 2.0 * t)
}