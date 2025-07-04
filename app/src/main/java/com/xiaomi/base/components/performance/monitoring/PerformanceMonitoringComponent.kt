package com.xiaomi.base.components.performance.monitoring

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

// Helper colors
val Orange = Color(0xFFFF9800)
val Purple = Color(0xFF9C27B0)

// Enums
enum class PerformanceMetricType {
    CPU_USAGE,
    MEMORY_USAGE,
    NETWORK_LATENCY,
    FRAME_RATE,
    BATTERY_USAGE,
    DISK_IO,
    RENDER_TIME,
    STARTUP_TIME,
    CRASH_RATE,
    ANR_RATE
}

enum class PerformanceLevel {
    EXCELLENT,
    GOOD,
    FAIR,
    POOR,
    CRITICAL
}

enum class MonitoringInterval {
    REAL_TIME,
    EVERY_SECOND,
    EVERY_MINUTE,
    EVERY_HOUR,
    DAILY
}

enum class AlertSeverity {
    INFO,
    WARNING,
    ERROR,
    CRITICAL
}

enum class OptimizationType {
    MEMORY_OPTIMIZATION,
    CPU_OPTIMIZATION,
    NETWORK_OPTIMIZATION,
    BATTERY_OPTIMIZATION,
    RENDER_OPTIMIZATION,
    STORAGE_OPTIMIZATION
}

enum class ProfilerType {
    CPU_PROFILER,
    MEMORY_PROFILER,
    NETWORK_PROFILER,
    ENERGY_PROFILER,
    GPU_PROFILER
}

// Data Classes
data class PerformanceMetric(
    val type: PerformanceMetricType,
    val value: Double,
    val unit: String,
    val timestamp: Long = System.currentTimeMillis(),
    val threshold: Double = 0.0,
    val level: PerformanceLevel = PerformanceLevel.GOOD,
    val trend: Double = 0.0
)

data class PerformanceAlert(
    val id: String,
    val title: String,
    val message: String,
    val severity: AlertSeverity,
    val metricType: PerformanceMetricType,
    val value: Double,
    val threshold: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val acknowledged: Boolean = false,
    val resolved: Boolean = false
)

data class PerformanceReport(
    val id: String,
    val title: String,
    val period: String,
    val metrics: List<PerformanceMetric>,
    val summary: String,
    val recommendations: List<String>,
    val generatedAt: Long = System.currentTimeMillis()
)

data class OptimizationSuggestion(
    val id: String,
    val type: OptimizationType,
    val title: String,
    val description: String,
    val impact: String,
    val effort: String,
    val priority: Int,
    val implemented: Boolean = false
)

data class ProfilerSession(
    val id: String,
    val type: ProfilerType,
    val name: String,
    val startTime: Long,
    val endTime: Long? = null,
    val duration: Long = 0,
    val status: String = "Running",
    val dataPoints: Int = 0
)

data class PerformanceMonitoringConfig(
    val enableRealTimeMonitoring: Boolean = true,
    val monitoringInterval: MonitoringInterval = MonitoringInterval.EVERY_SECOND,
    val enableAlerts: Boolean = true,
    val enableProfiling: Boolean = true,
    val enableOptimizations: Boolean = true,
    val retentionDays: Int = 30,
    val alertThresholds: Map<PerformanceMetricType, Double> = emptyMap()
)

// Main Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformanceMonitoringComponent(
    modifier: Modifier = Modifier,
    config: PerformanceMonitoringConfig = PerformanceMonitoringConfig()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Metrics", "Alerts", "Profiler", "Optimization", "Reports")
    
    // Sample data
    val metrics by remember { mutableStateOf<List<PerformanceMetric>>(generateSampleMetrics()) }
    val alerts by remember { mutableStateOf<List<PerformanceAlert>>(generateSampleAlerts()) }
    val profilerSessions by remember { mutableStateOf<List<ProfilerSession>>(generateSampleProfilerSessions()) }
    val optimizations by remember { mutableStateOf<List<OptimizationSuggestion>>(generateSampleOptimizations()) }
    val reports by remember { mutableStateOf<List<PerformanceReport>>(generateSampleReports()) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        PerformanceMonitoringHeader(config = config)
        
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Content
        when (selectedTab) {
            0 -> PerformanceOverviewTab(metrics = metrics, alerts = alerts)
            1 -> PerformanceMetricsTab(metrics = metrics)
            2 -> PerformanceAlertsTab(alerts = alerts, onAlertAction = { _, _ -> })
            3 -> PerformanceProfilerTab(sessions = profilerSessions)
            4 -> PerformanceOptimizationTab(optimizations = optimizations)
            5 -> PerformanceReportsTab(reports = reports)
        }
    }
}

@Composable
fun PerformanceMonitoringHeader(
    config: PerformanceMonitoringConfig
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Performance Monitoring",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Real-time application performance tracking",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quick stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickStatCard(title = "CPU", value = "45%", color = Color.Blue)
                QuickStatCard(title = "Memory", value = "2.1GB", color = Color.Green)
                QuickStatCard(title = "FPS", value = "58", color = Orange)
                QuickStatCard(title = "Latency", value = "120ms", color = Purple)
            }
        }
    }
}

@Composable
fun QuickStatCard(
    title: String,
    value: String,
    color: Color
) {
    Card(
        modifier = Modifier.size(80.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Tab Components
@Composable
fun PerformanceOverviewTab(
    metrics: List<PerformanceMetric>,
    alerts: List<PerformanceAlert>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "System Health: ${calculateHealthScore(metrics).toInt()}%",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Active Alerts",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    alerts.filter { !it.resolved }.take(3).forEach { alert ->
                        AlertRow(alert = alert)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
        
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recent Metrics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    metrics.take(5).forEach { metric ->
                        MetricRow(metric = metric)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PerformanceMetricsTab(metrics: List<PerformanceMetric>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(metrics) { metric ->
            MetricCard(metric = metric)
        }
    }
}

@Composable
fun PerformanceAlertsTab(
    alerts: List<PerformanceAlert>,
    onAlertAction: (PerformanceAlert, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(alerts) { alert ->
            AlertCard(alert = alert, onAction = { action -> onAlertAction(alert, action) })
        }
    }
}

@Composable
fun PerformanceProfilerTab(sessions: List<ProfilerSession>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sessions) { session ->
            ProfilerSessionCard(session = session)
        }
    }
}

@Composable
fun PerformanceOptimizationTab(optimizations: List<OptimizationSuggestion>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(optimizations) { optimization ->
            OptimizationCard(optimization = optimization)
        }
    }
}

@Composable
fun PerformanceReportsTab(reports: List<PerformanceReport>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(reports) { report ->
            ReportCard(report = report)
        }
    }
}

// Card Components
@Composable
fun MetricCard(metric: PerformanceMetric) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getPerformanceLevelColor(metric.level).copy(alpha = 0.1f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = metric.type.name.replace("_", " "),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${metric.value.toInt()}${metric.unit}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = getPerformanceLevelColor(metric.level)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = (metric.value / metric.threshold).coerceIn(0.0, 1.0).toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = getPerformanceLevelColor(metric.level)
            )
        }
    }
}

@Composable
fun AlertCard(
    alert: PerformanceAlert,
    onAction: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getAlertColor(alert.severity).copy(alpha = 0.1f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = getAlertIcon(alert.severity),
                        contentDescription = null,
                        tint = getAlertColor(alert.severity),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = alert.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Text(
                    text = formatTimestamp(alert.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = alert.message,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row {
                Button(
                    onClick = { onAction("acknowledge") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Acknowledge")
                }
                
                OutlinedButton(
                    onClick = { onAction("resolve") }
                ) {
                    Text("Resolve")
                }
            }
        }
    }
}

@Composable
fun ProfilerSessionCard(session: ProfilerSession) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = session.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Type: ${session.type.name.replace("_", " ")}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Status: ${session.status}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (session.status == "Running") Color.Green else Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Data Points: ${session.dataPoints}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun OptimizationCard(optimization: OptimizationSuggestion) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = optimization.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = optimization.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Text(
                    text = "Priority: ${optimization.priority}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row {
                AssistChip(
                    onClick = { },
                    label = { Text("Impact: ${optimization.impact}") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                AssistChip(
                    onClick = { },
                    label = { Text("Effort: ${optimization.effort}") }
                )
            }
        }
    }
}

@Composable
fun ReportCard(report: PerformanceReport) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = report.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Period: ${report.period}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = report.summary,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Generated: ${formatTimestamp(report.generatedAt)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Row Components
@Composable
fun MetricRow(metric: PerformanceMetric) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = metric.type.name.replace("_", " "),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${metric.value.toInt()}${metric.unit}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = getPerformanceLevelColor(metric.level)
        )
    }
}

@Composable
fun AlertRow(alert: PerformanceAlert) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = getAlertIcon(alert.severity),
            contentDescription = null,
            tint = getAlertColor(alert.severity),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = alert.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formatTimestamp(alert.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Helper Functions
fun getPerformanceLevelColor(level: PerformanceLevel): Color {
    return when (level) {
        PerformanceLevel.EXCELLENT -> Color.Green
        PerformanceLevel.GOOD -> Color(0xFF8BC34A)
        PerformanceLevel.FAIR -> Color.Yellow
        PerformanceLevel.POOR -> Orange
        PerformanceLevel.CRITICAL -> Color.Red
    }
}

fun getAlertColor(severity: AlertSeverity): Color {
    return when (severity) {
        AlertSeverity.INFO -> Color.Blue
        AlertSeverity.WARNING -> Orange
        AlertSeverity.ERROR -> Color.Red
        AlertSeverity.CRITICAL -> Color(0xFF8B0000)
    }
}

fun getAlertIcon(severity: AlertSeverity): androidx.compose.ui.graphics.vector.ImageVector {
    return when (severity) {
        AlertSeverity.INFO -> Icons.Default.Info
        AlertSeverity.WARNING -> Icons.Default.Warning
        AlertSeverity.ERROR -> Icons.Default.Error
        AlertSeverity.CRITICAL -> Icons.Default.ErrorOutline
    }
}

fun calculateHealthScore(metrics: List<PerformanceMetric>): Double {
    if (metrics.isEmpty()) return 0.0
    
    val scores = metrics.map { metric ->
        when (metric.level) {
            PerformanceLevel.EXCELLENT -> 100.0
            PerformanceLevel.GOOD -> 80.0
            PerformanceLevel.FAIR -> 60.0
            PerformanceLevel.POOR -> 40.0
            PerformanceLevel.CRITICAL -> 20.0
        }
    }
    
    return scores.average()
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        else -> "${diff / 86400000}d ago"
    }
}

// Sample Data Generators
fun generateSampleMetrics(): List<PerformanceMetric> {
    return listOf(
        PerformanceMetric(
            type = PerformanceMetricType.CPU_USAGE,
            value = 45.0 + Random.nextDouble() * 20,
            unit = "%",
            threshold = 80.0,
            level = PerformanceLevel.GOOD,
            trend = Random.nextDouble() * 0.2 - 0.1
        ),
        PerformanceMetric(
            type = PerformanceMetricType.MEMORY_USAGE,
            value = 2.1 + Random.nextDouble() * 1.0,
            unit = "GB",
            threshold = 4.0,
            level = PerformanceLevel.GOOD,
            trend = Random.nextDouble() * 0.2 - 0.1
        ),
        PerformanceMetric(
            type = PerformanceMetricType.FRAME_RATE,
            value = 55.0 + Random.nextDouble() * 10,
            unit = "fps",
            threshold = 30.0,
            level = PerformanceLevel.EXCELLENT,
            trend = Random.nextDouble() * 0.2 - 0.1
        ),
        PerformanceMetric(
            type = PerformanceMetricType.NETWORK_LATENCY,
            value = 100.0 + Random.nextDouble() * 50,
            unit = "ms",
            threshold = 200.0,
            level = PerformanceLevel.GOOD,
            trend = Random.nextDouble() * 0.2 - 0.1
        ),
        PerformanceMetric(
            type = PerformanceMetricType.BATTERY_USAGE,
            value = 15.0 + Random.nextDouble() * 10,
            unit = "%/h",
            threshold = 30.0,
            level = PerformanceLevel.GOOD,
            trend = Random.nextDouble() * 0.2 - 0.1
        )
    )
}

fun generateSampleAlerts(): List<PerformanceAlert> {
    return listOf(
        PerformanceAlert(
            id = "alert1",
            title = "High CPU Usage",
            message = "CPU usage has exceeded 80% for the last 5 minutes",
            severity = AlertSeverity.WARNING,
            metricType = PerformanceMetricType.CPU_USAGE,
            value = 85.0,
            threshold = 80.0,
            timestamp = System.currentTimeMillis() - 300000
        ),
        PerformanceAlert(
            id = "alert2",
            title = "Memory Leak Detected",
            message = "Memory usage is continuously increasing",
            severity = AlertSeverity.ERROR,
            metricType = PerformanceMetricType.MEMORY_USAGE,
            value = 3.8,
            threshold = 3.5,
            timestamp = System.currentTimeMillis() - 600000
        ),
        PerformanceAlert(
            id = "alert3",
            title = "Low Frame Rate",
            message = "Frame rate dropped below 30 fps",
            severity = AlertSeverity.WARNING,
            metricType = PerformanceMetricType.FRAME_RATE,
            value = 25.0,
            threshold = 30.0,
            timestamp = System.currentTimeMillis() - 120000
        )
    )
}

fun generateSampleProfilerSessions(): List<ProfilerSession> {
    return listOf(
        ProfilerSession(
            id = "session1",
            type = ProfilerType.CPU_PROFILER,
            name = "CPU Performance Analysis",
            startTime = System.currentTimeMillis() - 1800000,
            endTime = null,
            duration = 1800000,
            status = "Running",
            dataPoints = 1800
        ),
        ProfilerSession(
            id = "session2",
            type = ProfilerType.MEMORY_PROFILER,
            name = "Memory Leak Investigation",
            startTime = System.currentTimeMillis() - 3600000,
            endTime = System.currentTimeMillis() - 1800000,
            duration = 1800000,
            status = "Completed",
            dataPoints = 3600
        )
    )
}

fun generateSampleOptimizations(): List<OptimizationSuggestion> {
    return listOf(
        OptimizationSuggestion(
            id = "opt1",
            type = OptimizationType.MEMORY_OPTIMIZATION,
            title = "Implement Object Pooling",
            description = "Use object pooling for frequently created/destroyed objects",
            impact = "High",
            effort = "Medium",
            priority = 1
        ),
        OptimizationSuggestion(
            id = "opt2",
            type = OptimizationType.CPU_OPTIMIZATION,
            title = "Optimize Heavy Computations",
            description = "Move heavy computations to background threads",
            impact = "Medium",
            effort = "Low",
            priority = 2
        ),
        OptimizationSuggestion(
            id = "opt3",
            type = OptimizationType.RENDER_OPTIMIZATION,
            title = "Reduce Overdraw",
            description = "Optimize UI layouts to reduce overdraw",
            impact = "Medium",
            effort = "Medium",
            priority = 3
        )
    )
}

fun generateSampleReports(): List<PerformanceReport> {
    return listOf(
        PerformanceReport(
            id = "report1",
            title = "Weekly Performance Report",
            period = "March 1-7, 2024",
            metrics = generateSampleMetrics(),
            summary = "Overall performance is good with some areas for improvement",
            recommendations = listOf(
                "Optimize memory usage in data processing",
                "Implement caching for network requests",
                "Review and optimize database queries"
            ),
            generatedAt = System.currentTimeMillis() - 86400000
        ),
        PerformanceReport(
            id = "report2",
            title = "Monthly Performance Summary",
            period = "February 2024",
            metrics = generateSampleMetrics(),
            summary = "Significant improvements in CPU and memory usage",
            recommendations = listOf(
                "Continue monitoring frame rate",
                "Implement additional battery optimizations"
            ),
            generatedAt = System.currentTimeMillis() - 2592000000
        )
    )
}