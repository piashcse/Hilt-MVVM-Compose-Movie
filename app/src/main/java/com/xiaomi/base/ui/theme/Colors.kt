package com.xiaomi.base.ui.theme

import androidx.compose.ui.graphics.Color

// Extended color palette
val Orange = Color(0xFFFF9800)
val Purple = Color(0xFF9C27B0)
val Green = Color(0xFF4CAF50)
val Red = Color(0xFFF44336)
val Blue = Color(0xFF2196F3)
val Yellow = Color(0xFFFFEB3B)
val Cyan = Color(0xFF00BCD4)
val Pink = Color(0xFFE91E63)
val Indigo = Color(0xFF3F51B5)
val Teal = Color(0xFF009688)
val Lime = Color(0xFFCDDC39)
val Amber = Color(0xFFFFC107)
val DeepOrange = Color(0xFFFF5722)
val DeepPurple = Color(0xFF673AB7)
val LightBlue = Color(0xFF03A9F4)
val LightGreen = Color(0xFF8BC34A)
val Brown = Color(0xFF795548)
val BlueGrey = Color(0xFF607D8B)
val Grey = Color(0xFF9E9E9E)

// Accessibility colors
val HighContrastBlack = Color(0xFF000000)
val HighContrastWhite = Color(0xFFFFFFFF)
val AccessibilityFocus = Color(0xFF1976D2)
val AccessibilityError = Color(0xFFD32F2F)
val AccessibilityWarning = Color(0xFFF57C00)
val AccessibilitySuccess = Color(0xFF388E3C)

// Status colors
val StatusSuccess = Color(0xFF4CAF50)
val StatusWarning = Color(0xFFFF9800)
val StatusError = Color(0xFFF44336)
val StatusInfo = Color(0xFF2196F3)

// Chart colors
val ChartPrimary = Color(0xFF1976D2)
val ChartSecondary = Color(0xFFFF9800)
val ChartTertiary = Color(0xFF4CAF50)
val ChartQuaternary = Color(0xFF9C27B0)
val ChartQuinary = Color(0xFFF44336)

// Gradient colors
val GradientStart = Color(0xFF6200EE)
val GradientEnd = Color(0xFF03DAC6)

// Semantic colors for different states
val ColorBlindSafe = listOf(
    Color(0xFF1f77b4), // Blue
    Color(0xFFff7f0e), // Orange
    Color(0xFF2ca02c), // Green
    Color(0xFFd62728), // Red
    Color(0xFF9467bd), // Purple
    Color(0xFF8c564b), // Brown
    Color(0xFFe377c2), // Pink
    Color(0xFF7f7f7f), // Gray
    Color(0xFFbcbd22), // Olive
    Color(0xFF17becf)  // Cyan
)

// Performance monitoring colors
val PerformanceExcellent = Color(0xFF4CAF50)
val PerformanceGood = Color(0xFF8BC34A)
val PerformanceFair = Color(0xFFFFEB3B)
val PerformancePoor = Color(0xFFFF9800)
val PerformanceCritical = Color(0xFFF44336)

// Localization status colors
val LocalizationComplete = Color(0xFF4CAF50)
val LocalizationInProgress = Color(0xFFFF9800)
val LocalizationNotStarted = Color(0xFF9E9E9E)
val LocalizationNeedsReview = Color(0xFF2196F3)

// Security colors
val SecurityHigh = Color(0xFFF44336)
val SecurityMedium = Color(0xFFFF9800)
val SecurityLow = Color(0xFFFFEB3B)
val SecuritySecure = Color(0xFF4CAF50)

// Data visualization colors
val DataVisualizationPalette = listOf(
    Color(0xFF1f77b4),
    Color(0xFFff7f0e),
    Color(0xFF2ca02c),
    Color(0xFFd62728),
    Color(0xFF9467bd),
    Color(0xFF8c564b),
    Color(0xFFe377c2),
    Color(0xFF7f7f7f),
    Color(0xFFbcbd22),
    Color(0xFF17becf)
)

// Helper functions for color selection
fun getStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "success", "passed", "completed" -> StatusSuccess
        "warning", "needs review" -> StatusWarning
        "error", "failed", "critical" -> StatusError
        "info", "in progress" -> StatusInfo
        else -> Grey
    }
}

fun getPerformanceColor(score: Double): Color {
    return when {
        score >= 0.9 -> PerformanceExcellent
        score >= 0.7 -> PerformanceGood
        score >= 0.5 -> PerformanceFair
        score >= 0.3 -> PerformancePoor
        else -> PerformanceCritical
    }
}

fun getLocalizationColor(completeness: Double): Color {
    return when {
        completeness >= 0.9 -> LocalizationComplete
        completeness >= 0.5 -> LocalizationInProgress
        completeness > 0.0 -> LocalizationNeedsReview
        else -> LocalizationNotStarted
    }
}

fun getSecurityColor(level: String): Color {
    return when (level.lowercase()) {
        "high", "critical" -> SecurityHigh
        "medium", "moderate" -> SecurityMedium
        "low", "minor" -> SecurityLow
        "secure", "safe" -> SecuritySecure
        else -> Grey
    }
}

fun getChartColor(index: Int): Color {
    return DataVisualizationPalette[index % DataVisualizationPalette.size]
}

fun getColorBlindSafeColor(index: Int): Color {
    return ColorBlindSafe[index % ColorBlindSafe.size]
}