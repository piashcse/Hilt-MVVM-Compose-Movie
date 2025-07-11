package com.xiaomi.base.ui.kit.components.specialized.performance

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.ui.kit.foundation.XiaomiTheme
import kotlin.math.*
import kotlin.random.Random

/**
 * Xiaomi Performance Components - Performance Monitoring UI
 * 
 * This file contains performance monitoring and system metrics components following 
 * Material Design 3 principles with Xiaomi's design language. These components provide
 * visual representations of system performance, resource usage, and optimization tools.
 */

/**
 * Performance Metric Types
 */
enum class PerformanceMetricType {
    CPU,
    MEMORY,
    BATTERY,
    NETWORK,
    STORAGE,
    GPU,
    TEMPERATURE
}

/**
 * Performance Status Levels
 */
enum class PerformanceStatus {
    EXCELLENT,
    GOOD,
    FAIR,
    POOR,
    CRITICAL
}

/**
 * Data classes for performance metrics
 */
data class PerformanceMetric(
    val type: PerformanceMetricType,
    val value: Float, // 0.0 to 1.0
    val label: String,
    val unit: String = "%",
    val status: PerformanceStatus = PerformanceStatus.GOOD
)

data class PerformanceDataPoint(
    val timestamp: Long,
    val value: Float
)

data class SystemInfo(
    val deviceName: String,
    val osVersion: String,
    val totalMemory: String,
    val availableStorage: String,
    val cpuCores: Int,
    val batteryLevel: Int
)

/**
 * Xiaomi Performance Dashboard
 * Main performance monitoring dashboard
 */
@Composable
fun XiaomiPerformanceDashboard(
    metrics: List<PerformanceMetric>,
    systemInfo: SystemInfo,
    modifier: Modifier = Modifier,
    onOptimize: () -> Unit = {},
    onDetails: (PerformanceMetricType) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Overall Performance Score
        item {
            XiaomiPerformanceScore(
                metrics = metrics,
                onOptimize = onOptimize
            )
        }
        
        // System Information
        item {
            XiaomiSystemInfoCard(systemInfo = systemInfo)
        }
        
        // Performance Metrics Grid
        items(metrics.chunked(2)) { metricPair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                metricPair.forEach { metric ->
                    XiaomiPerformanceMetricCard(
                        metric = metric,
                        onClick = { onDetails(metric.type) },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Fill remaining space if odd number of metrics
                if (metricPair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Xiaomi Performance Score
 * Overall performance score with optimization button
 */
@Composable
fun XiaomiPerformanceScore(
    metrics: List<PerformanceMetric>,
    modifier: Modifier = Modifier,
    onOptimize: () -> Unit = {}
) {
    val averageScore = metrics.map { 1f - it.value }.average().toFloat()
    val scorePercentage = (averageScore * 100).toInt()
    
    val status = when {
        scorePercentage >= 90 -> PerformanceStatus.EXCELLENT
        scorePercentage >= 75 -> PerformanceStatus.GOOD
        scorePercentage >= 60 -> PerformanceStatus.FAIR
        scorePercentage >= 40 -> PerformanceStatus.POOR
        else -> PerformanceStatus.CRITICAL
    }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Performance Score",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Circular Progress Score
            Box(
                contentAlignment = Alignment.Center
            ) {
                XiaomiCircularProgress(
                    progress = averageScore,
                    size = 120.dp,
                    strokeWidth = 12.dp,
                    color = getStatusColor(status)
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$scorePercentage",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = status.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(
                onClick = onOptimize,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Speed,
                    contentDescription = "Optimize"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Optimize Performance")
            }
        }
    }
}

/**
 * Xiaomi Performance Metric Card
 * Individual metric display card
 */
@Composable
fun XiaomiPerformanceMetricCard(
    metric: PerformanceMetric,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val icon = when (metric.type) {
        PerformanceMetricType.CPU -> Icons.Default.Memory
        PerformanceMetricType.MEMORY -> Icons.Default.Storage
        PerformanceMetricType.BATTERY -> Icons.Default.BatteryFull
        PerformanceMetricType.NETWORK -> Icons.Default.Wifi
        PerformanceMetricType.STORAGE -> Icons.Default.Folder
                    PerformanceMetricType.GPU -> Icons.Default.Computer
        PerformanceMetricType.TEMPERATURE -> Icons.Default.Thermostat
    }
    
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = metric.label,
                    tint = getStatusColor(metric.status),
                    modifier = Modifier.size(24.dp)
                )
                
                Text(
                    text = "${(metric.value * 100).toInt()}${metric.unit}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = getStatusColor(metric.status)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = metric.label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = metric.value,
                modifier = Modifier.fillMaxWidth(),
                color = getStatusColor(metric.status),
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

/**
 * Xiaomi System Info Card
 * Display system information
 */
@Composable
fun XiaomiSystemInfoCard(
    systemInfo: SystemInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = "Device Info",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "System Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                XiaomiInfoRow("Device", systemInfo.deviceName)
                XiaomiInfoRow("OS Version", systemInfo.osVersion)
                XiaomiInfoRow("Total Memory", systemInfo.totalMemory)
                XiaomiInfoRow("Available Storage", systemInfo.availableStorage)
                XiaomiInfoRow("CPU Cores", "${systemInfo.cpuCores} cores")
                XiaomiInfoRow("Battery Level", "${systemInfo.batteryLevel}%")
            }
        }
    }
}

/**
 * Xiaomi Performance Chart
 * Real-time performance chart
 */
@Composable
fun XiaomiPerformanceChart(
    dataPoints: List<PerformanceDataPoint>,
    metricType: PerformanceMetricType,
    modifier: Modifier = Modifier
) {
    val chartColor = MaterialTheme.colorScheme.primary
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = metricType.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Last 24h",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                drawPerformanceChart(
                    dataPoints = dataPoints,
                    color = chartColor
                )
            }
        }
    }
}

/**
 * Xiaomi Circular Progress
 * Circular progress indicator for scores
 */
@Composable
fun XiaomiCircularProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    strokeWidth: Dp = 8.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "progress"
    )
    
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val radius = (size.toPx() - strokeWidthPx) / 2
        val center = Offset(size.toPx() / 2, size.toPx() / 2)
        
        // Background circle
        drawCircle(
            color = backgroundColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )
        
        // Progress arc
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = animatedProgress * 360f,
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
 * Helper function to draw performance chart
 */
fun DrawScope.drawPerformanceChart(
    dataPoints: List<PerformanceDataPoint>,
    color: Color
) {
    if (dataPoints.size < 2) return
    
    val maxValue = dataPoints.maxOfOrNull { it.value } ?: 1f
    val minValue = dataPoints.minOfOrNull { it.value } ?: 0f
    val valueRange = maxValue - minValue
    
    val path = Path()
    val stepX = size.width / (dataPoints.size - 1)
    
    dataPoints.forEachIndexed { index, point ->
        val x = index * stepX
        val normalizedValue = if (valueRange > 0) (point.value - minValue) / valueRange else 0.5f
        val y = size.height - (normalizedValue * size.height)
        
        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
    )
    
    // Draw data points
    dataPoints.forEachIndexed { index, point ->
        val x = index * stepX
        val normalizedValue = if (valueRange > 0) (point.value - minValue) / valueRange else 0.5f
        val y = size.height - (normalizedValue * size.height)
        
        drawCircle(
            color = color,
            radius = 4.dp.toPx(),
            center = Offset(x, y)
        )
    }
}

/**
 * Helper composable for info rows
 */
@Composable
fun XiaomiInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Helper function to get status color
 */
fun getStatusColor(status: PerformanceStatus): Color {
    return when (status) {
        PerformanceStatus.EXCELLENT -> Color(0xFF4CAF50)
        PerformanceStatus.GOOD -> Color(0xFF8BC34A)
        PerformanceStatus.FAIR -> Color(0xFFFF9800)
        PerformanceStatus.POOR -> Color(0xFFFF5722)
        PerformanceStatus.CRITICAL -> Color(0xFFF44336)
    }
}

// Preview Functions
@Preview(name = "Performance Dashboard - Light")
@Composable
fun XiaomiPerformanceDashboardPreview() {
    XiaomiTheme {
        Surface {
            val sampleMetrics = listOf(
                PerformanceMetric(PerformanceMetricType.CPU, 0.45f, "CPU Usage", "%", PerformanceStatus.GOOD),
                PerformanceMetric(PerformanceMetricType.MEMORY, 0.67f, "Memory Usage", "%", PerformanceStatus.FAIR),
                PerformanceMetric(PerformanceMetricType.BATTERY, 0.23f, "Battery Usage", "%", PerformanceStatus.EXCELLENT),
                PerformanceMetric(PerformanceMetricType.STORAGE, 0.78f, "Storage Usage", "%", PerformanceStatus.POOR)
            )
            
            val sampleSystemInfo = SystemInfo(
                deviceName = "Xiaomi 13 Pro",
                osVersion = "Android 14",
                totalMemory = "12 GB",
                availableStorage = "128 GB",
                cpuCores = 8,
                batteryLevel = 85
            )
            
            XiaomiPerformanceDashboard(
                metrics = sampleMetrics,
                systemInfo = sampleSystemInfo
            )
        }
    }
}

@Preview(name = "Performance Chart - Light")
@Composable
fun XiaomiPerformanceChartPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val sampleData = (0..20).map { index ->
                    PerformanceDataPoint(
                        timestamp = System.currentTimeMillis() - (20 - index) * 60000,
                        value = 0.3f + Random.nextFloat() * 0.4f
                    )
                }
                
                XiaomiPerformanceChart(
                    dataPoints = sampleData,
                    metricType = PerformanceMetricType.CPU
                )
                
                XiaomiPerformanceChart(
                    dataPoints = sampleData.map { it.copy(value = it.value * 1.2f) },
                    metricType = PerformanceMetricType.MEMORY
                )
            }
        }
    }
}

@Preview(name = "Performance Metrics - Light")
@Composable
fun XiaomiPerformanceMetricsPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Performance Metrics")
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    XiaomiPerformanceMetricCard(
                        metric = PerformanceMetric(
                            PerformanceMetricType.CPU,
                            0.45f,
                            "CPU Usage",
                            "%",
                            PerformanceStatus.GOOD
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    
                    XiaomiPerformanceMetricCard(
                        metric = PerformanceMetric(
                            PerformanceMetricType.MEMORY,
                            0.78f,
                            "Memory Usage",
                            "%",
                            PerformanceStatus.POOR
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
                
                XiaomiCircularProgress(
                    progress = 0.75f,
                    size = 100.dp
                )
            }
        }
    }
}

@Preview(name = "Performance - Dark")
@Composable
fun XiaomiPerformanceDarkPreview() {
    XiaomiTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Dark Theme Performance")
                
                val sampleSystemInfo = SystemInfo(
                    deviceName = "Xiaomi 13 Pro",
                    osVersion = "Android 14",
                    totalMemory = "12 GB",
                    availableStorage = "128 GB",
                    cpuCores = 8,
                    batteryLevel = 85
                )
                
                XiaomiSystemInfoCard(systemInfo = sampleSystemInfo)
                
                XiaomiCircularProgress(
                    progress = 0.85f,
                    size = 80.dp
                )
            }
        }
    }
}