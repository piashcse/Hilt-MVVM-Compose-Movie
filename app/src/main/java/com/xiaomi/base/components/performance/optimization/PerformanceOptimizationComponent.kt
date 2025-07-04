package com.xiaomi.base.components.performance.optimization

import android.content.Context
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Performance Metric Type
 */
enum class PerformanceMetricType {
    CPU_USAGE,
    MEMORY_USAGE,
    NETWORK_LATENCY,
    BATTERY_USAGE,
    FRAME_RATE,
    APP_STARTUP_TIME,
    SCREEN_LOAD_TIME,
    API_RESPONSE_TIME,
    DATABASE_QUERY_TIME,
    CACHE_HIT_RATE
}

/**
 * Performance Severity Level
 */
enum class PerformanceSeverity {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW,
    INFO
}

/**
 * Optimization Strategy
 */
enum class OptimizationStrategy {
    LAZY_LOADING,
    CACHING,
    COMPRESSION,
    MINIFICATION,
    CODE_SPLITTING,
    RESOURCE_BUNDLING,
    DATABASE_INDEXING,
    MEMORY_POOLING,
    BACKGROUND_PROCESSING,
    PREFETCHING
}

/**
 * Performance Metric
 */
data class PerformanceMetric(
    val id: String,
    val type: PerformanceMetricType,
    val name: String,
    val value: Double,
    val unit: String,
    val threshold: Double,
    val severity: PerformanceSeverity,
    val timestamp: Date,
    val source: String,
    val metadata: Map<String, String> = emptyMap()
)

/**
 * Performance Issue
 */
data class PerformanceIssue(
    val id: String,
    val title: String,
    val description: String,
    val severity: PerformanceSeverity,
    val category: PerformanceMetricType,
    val impact: String,
    val detectedAt: Date,
    val affectedComponents: List<String>,
    val suggestedOptimizations: List<OptimizationRecommendation>,
    val status: IssueStatus = IssueStatus.OPEN
)

/**
 * Issue Status
 */
enum class IssueStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    IGNORED
}

/**
 * Optimization Recommendation
 */
data class OptimizationRecommendation(
    val id: String,
    val strategy: OptimizationStrategy,
    val title: String,
    val description: String,
    val estimatedImpact: String,
    val implementationEffort: ImplementationEffort,
    val priority: Int, // 1-10
    val steps: List<String>
)

/**
 * Implementation Effort
 */
enum class ImplementationEffort {
    LOW,
    MEDIUM,
    HIGH,
    VERY_HIGH
}

/**
 * Performance Benchmark
 */
data class PerformanceBenchmark(
    val id: String,
    val name: String,
    val description: String,
    val metrics: List<PerformanceMetric>,
    val executedAt: Date,
    val duration: Long, // in milliseconds
    val environment: String,
    val deviceInfo: DeviceInfo,
    val score: Int // 0-100
)

/**
 * Device Information
 */
data class DeviceInfo(
    val model: String,
    val manufacturer: String,
    val osVersion: String,
    val apiLevel: Int,
    val totalMemory: Long,
    val availableMemory: Long,
    val cpuCores: Int,
    val screenDensity: Float
)

/**
 * Performance Configuration
 */
data class PerformanceConfig(
    val enableRealTimeMonitoring: Boolean = true,
    val enableAutomaticOptimization: Boolean = false,
    val enableBenchmarking: Boolean = true,
    val enableIssueDetection: Boolean = true,
    val monitoringInterval: Long = 5000, // milliseconds
    val alertThresholds: Map<PerformanceMetricType, Double> = emptyMap(),
    val enableDetailedProfiling: Boolean = false,
    val maxHistorySize: Int = 1000
)

/**
 * Performance Optimization Component
 * Comprehensive performance monitoring and optimization
 * 
 * @param config Performance configuration
 * @param onIssueDetected Callback when performance issue is detected
 * @param onOptimizationApplied Callback when optimization is applied
 */
@Composable
fun PerformanceOptimizationComponent(
    config: PerformanceConfig = PerformanceConfig(),
    onIssueDetected: (PerformanceIssue) -> Unit = {},
    onOptimizationApplied: (OptimizationRecommendation) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var performanceMetrics by remember { mutableStateOf<List<PerformanceMetric>>(emptyList()) }
    var performanceIssues by remember { mutableStateOf<List<PerformanceIssue>>(emptyList()) }
    var optimizationRecommendations by remember { mutableStateOf<List<OptimizationRecommendation>>(emptyList()) }
    var benchmarkResults by remember { mutableStateOf<List<PerformanceBenchmark>>(emptyList()) }
    var isMonitoring by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    LaunchedEffect(config) {
        if (config.enableRealTimeMonitoring) {
            isMonitoring = true
            while (isMonitoring) {
                val newMetrics = collectPerformanceMetrics(context)
                performanceMetrics = (performanceMetrics + newMetrics).takeLast(config.maxHistorySize)
                
                // Detect issues
                if (config.enableIssueDetection) {
                    val issues = detectPerformanceIssues(newMetrics, config.alertThresholds)
                    issues.forEach { onIssueDetected(it) }
                    performanceIssues = (performanceIssues + issues).distinctBy { it.id }
                }
                
                // Generate recommendations
                optimizationRecommendations = generateOptimizationRecommendations(performanceIssues)
                
                delay(config.monitoringInterval)
            }
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            isMonitoring = false
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Performance status header
        PerformanceStatusHeader(
            isMonitoring = isMonitoring,
            onToggleMonitoring = { isMonitoring = !isMonitoring },
            metrics = performanceMetrics.takeLast(10)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tab navigation
        PerformanceTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tab content
        when (selectedTab) {
            0 -> MetricsTab(
                metrics = performanceMetrics
            )
            1 -> IssuesTab(
                issues = performanceIssues,
                onIssueAction = { issue, action ->
                    // Handle issue actions
                }
            )
            2 -> OptimizationsTab(
                recommendations = optimizationRecommendations,
                onApplyOptimization = onOptimizationApplied
            )
            3 -> BenchmarkTab(
                benchmarks = benchmarkResults,
                onRunBenchmark = {
                    // Run new benchmark
                }
            )
        }
    }
}

/**
 * Real-time Performance Monitor Component
 * Displays live performance metrics
 * 
 * @param metrics List of performance metrics
 * @param refreshInterval Refresh interval in milliseconds
 */
@Composable
fun RealTimePerformanceMonitorComponent(
    metrics: List<PerformanceMetric>,
    refreshInterval: Long = 1000
) {
    var currentMetrics by remember { mutableStateOf<List<PerformanceMetric>>(emptyList()) }
    val context = LocalContext.current
    
    LaunchedEffect(refreshInterval) {
        while (true) {
            currentMetrics = collectPerformanceMetrics(context)
            delay(refreshInterval)
        }
    }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Real-time Performance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(currentMetrics) { metric ->
                    RealTimeMetricCard(metric = metric)
                }
            }
        }
    }
}

/**
 * Performance Benchmark Component
 * Run and display performance benchmarks
 * 
 * @param onBenchmarkComplete Callback when benchmark completes
 */
@Composable
fun PerformanceBenchmarkComponent(
    onBenchmarkComplete: (PerformanceBenchmark) -> Unit = {}
) {
    var isRunning by remember { mutableStateOf(false) }
    var currentBenchmark by remember { mutableStateOf<PerformanceBenchmark?>(null) }
    var progress by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    
    LaunchedEffect(isRunning) {
        if (isRunning) {
            val benchmark = runPerformanceBenchmark(context) { progressValue ->
                progress = progressValue
            }
            currentBenchmark = benchmark
            onBenchmarkComplete(benchmark)
            isRunning = false
            progress = 0f
        }
    }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Performance Benchmark",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (isRunning) {
                Column {
                    Text(
                        text = "Running benchmark...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Text(
                        text = "${(progress * 100).roundToInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            } else {
                Button(
                    onClick = { isRunning = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = "Run Benchmark"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Run Benchmark")
                }
            }
            
            currentBenchmark?.let { benchmark ->
                Spacer(modifier = Modifier.height(16.dp))
                BenchmarkResultCard(benchmark = benchmark)
            }
        }
    }
}

/**
 * Optimization Suggestions Component
 * Display and apply optimization recommendations
 * 
 * @param recommendations List of optimization recommendations
 * @param onApplyOptimization Callback when optimization is applied
 */
@Composable
fun OptimizationSuggestionsComponent(
    recommendations: List<OptimizationRecommendation>,
    onApplyOptimization: (OptimizationRecommendation) -> Unit = {}
) {
    val sortedRecommendations = recommendations.sortedByDescending { it.priority }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Optimization Suggestions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (sortedRecommendations.isEmpty()) {
                Text(
                    text = "No optimization suggestions available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(sortedRecommendations) { recommendation ->
                        OptimizationRecommendationCard(
                            recommendation = recommendation,
                            onApply = { onApplyOptimization(recommendation) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Performance Status Header
 */
@Composable
private fun PerformanceStatusHeader(
    isMonitoring: Boolean,
    onToggleMonitoring: () -> Unit,
    metrics: List<PerformanceMetric>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isMonitoring) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Performance Monitoring",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = if (isMonitoring) "Active" else "Inactive",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (metrics.isNotEmpty()) {
                    val avgCpuUsage = metrics
                        .filter { it.type == PerformanceMetricType.CPU_USAGE }
                        .map { it.value }
                        .average()
                        .takeIf { !it.isNaN() } ?: 0.0
                    
                    Text(
                        text = "CPU: ${avgCpuUsage.roundToInt()}%",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Switch(
                    checked = isMonitoring,
                    onCheckedChange = { onToggleMonitoring() }
                )
            }
        }
    }
}

/**
 * Performance Tab Row
 */
@Composable
private fun PerformanceTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        "Metrics",
        "Issues",
        "Optimizations",
        "Benchmarks"
    )
    
    TabRow(
        selectedTabIndex = selectedTab
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = { Text(title) }
            )
        }
    }
}

/**
 * Metrics Tab
 */
@Composable
private fun MetricsTab(
    metrics: List<PerformanceMetric>
) {
    val groupedMetrics = metrics.groupBy { it.type }
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            MetricsOverviewCard(metrics = metrics)
        }
        
        items(groupedMetrics.entries.toList()) { (type, typeMetrics) ->
            MetricTypeCard(
                type = type,
                metrics = typeMetrics.takeLast(10)
            )
        }
    }
}

/**
 * Issues Tab
 */
@Composable
private fun IssuesTab(
    issues: List<PerformanceIssue>,
    onIssueAction: (PerformanceIssue, String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            IssuesOverviewCard(issues = issues)
        }
        
        items(issues.sortedByDescending { it.severity.ordinal }) { issue ->
            PerformanceIssueCard(
                issue = issue,
                onAction = { action -> onIssueAction(issue, action) }
            )
        }
    }
}

/**
 * Optimizations Tab
 */
@Composable
private fun OptimizationsTab(
    recommendations: List<OptimizationRecommendation>,
    onApplyOptimization: (OptimizationRecommendation) -> Unit
) {
    OptimizationSuggestionsComponent(
        recommendations = recommendations,
        onApplyOptimization = onApplyOptimization
    )
}

/**
 * Benchmark Tab
 */
@Composable
private fun BenchmarkTab(
    benchmarks: List<PerformanceBenchmark>,
    onRunBenchmark: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            PerformanceBenchmarkComponent()
        }
        
        items(benchmarks.sortedByDescending { it.executedAt }) { benchmark ->
            BenchmarkResultCard(benchmark = benchmark)
        }
    }
}

/**
 * Metrics Overview Card
 */
@Composable
private fun MetricsOverviewCard(
    metrics: List<PerformanceMetric>
) {
    val recentMetrics = metrics.takeLast(50)
    val criticalIssues = recentMetrics.count { it.severity == PerformanceSeverity.CRITICAL }
    val highIssues = recentMetrics.count { it.severity == PerformanceSeverity.HIGH }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Performance Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OverviewMetric(
                    title = "Total Metrics",
                    value = recentMetrics.size.toString(),
                    icon = Icons.Default.Analytics
                )
                
                OverviewMetric(
                    title = "Critical Issues",
                    value = criticalIssues.toString(),
                    icon = Icons.Default.Error
                )
                
                OverviewMetric(
                    title = "High Issues",
                    value = highIssues.toString(),
                    icon = Icons.Default.Warning
                )
                
                OverviewMetric(
                    title = "Health Score",
                    value = calculateHealthScore(recentMetrics).toString(),
                    icon = Icons.Default.Favorite
                )
            }
        }
    }
}

/**
 * Overview Metric
 */
@Composable
private fun OverviewMetric(
    title: String,
    value: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Metric Type Card
 */
@Composable
private fun MetricTypeCard(
    type: PerformanceMetricType,
    metrics: List<PerformanceMetric>
) {
    val latestMetric = metrics.maxByOrNull { it.timestamp }
    val averageValue = metrics.map { it.value }.average().takeIf { !it.isNaN() } ?: 0.0
    
    Card(
        modifier = Modifier.fillMaxWidth()
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
                    text = type.name.replace("_", " "),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                latestMetric?.let { metric ->
                    Badge(
                        containerColor = getSeverityColor(metric.severity)
                    ) {
                        Text(
                            text = metric.severity.name,
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            latestMetric?.let { metric ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Current: ${metric.value.roundToInt()} ${metric.unit}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Text(
                        text = "Avg: ${averageValue.roundToInt()} ${metric.unit}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = (metric.value / metric.threshold).toFloat().coerceIn(0f, 1f),
                    modifier = Modifier.fillMaxWidth(),
                    color = getSeverityColor(metric.severity)
                )
            }
        }
    }
}

/**
 * Real-time Metric Card
 */
@Composable
private fun RealTimeMetricCard(
    metric: PerformanceMetric
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getSeverityColor(metric.severity).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = metric.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = metric.source,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${metric.value.roundToInt()} ${metric.unit}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = getSeverityColor(metric.severity)
                )
                
                Text(
                    text = metric.severity.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = getSeverityColor(metric.severity)
                )
            }
        }
    }
}

/**
 * Issues Overview Card
 */
@Composable
private fun IssuesOverviewCard(
    issues: List<PerformanceIssue>
) {
    val openIssues = issues.count { it.status == IssueStatus.OPEN }
    val criticalIssues = issues.count { it.severity == PerformanceSeverity.CRITICAL }
    val inProgressIssues = issues.count { it.status == IssueStatus.IN_PROGRESS }
    val resolvedIssues = issues.count { it.status == IssueStatus.RESOLVED }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Issues Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IssueMetric(
                    title = "Open",
                    value = openIssues.toString(),
                    icon = Icons.Default.ErrorOutline
                )
                
                IssueMetric(
                    title = "Critical",
                    value = criticalIssues.toString(),
                    icon = Icons.Default.Error
                )
                
                IssueMetric(
                    title = "In Progress",
                    value = inProgressIssues.toString(),
                    icon = Icons.Default.Pending
                )
                
                IssueMetric(
                    title = "Resolved",
                    value = resolvedIssues.toString(),
                    icon = Icons.Default.CheckCircle
                )
            }
        }
    }
}

/**
 * Issue Metric
 */
@Composable
private fun IssueMetric(
    title: String,
    value: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Performance Issue Card
 */
@Composable
private fun PerformanceIssueCard(
    issue: PerformanceIssue,
    onAction: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getSeverityColor(issue.severity).copy(alpha = 0.1f)
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
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = issue.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = issue.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Badge(
                    containerColor = getSeverityColor(issue.severity)
                ) {
                    Text(
                        text = issue.severity.name,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Impact: ${issue.impact}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "Components: ${issue.affectedComponents.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onAction("view") },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "View Details",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                
                if (issue.status == IssueStatus.OPEN) {
                    Button(
                        onClick = { onAction("resolve") },
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "Start Fix",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

/**
 * Optimization Recommendation Card
 */
@Composable
private fun OptimizationRecommendationCard(
    recommendation: OptimizationRecommendation,
    onApply: () -> Unit
) {
    val effortColor = when (recommendation.implementationEffort) {
        ImplementationEffort.LOW -> Color(0xFF4CAF50)
        ImplementationEffort.MEDIUM -> Color(0xFFFF9800)
        ImplementationEffort.HIGH -> Color(0xFFFF5722)
        ImplementationEffort.VERY_HIGH -> Color(0xFFD32F2F)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = recommendation.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = recommendation.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Priority: ${recommendation.priority}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Badge(
                        containerColor = effortColor
                    ) {
                        Text(
                            text = recommendation.implementationEffort.name,
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Estimated Impact: ${recommendation.estimatedImpact}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "Strategy: ${recommendation.strategy.name.replace("_", " ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { /* Show implementation steps */ },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "View Steps",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                
                Button(
                    onClick = onApply,
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Apply",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

/**
 * Benchmark Result Card
 */
@Composable
private fun BenchmarkResultCard(
    benchmark: PerformanceBenchmark
) {
    val scoreColor = when {
        benchmark.score >= 80 -> Color(0xFF4CAF50)
        benchmark.score >= 60 -> Color(0xFFFF9800)
        else -> MaterialTheme.colorScheme.error
    }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = benchmark.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = benchmark.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = "${benchmark.score}/100",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = scoreColor
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Duration: ${benchmark.duration}ms",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "Environment: ${benchmark.environment}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = "Device: ${benchmark.deviceInfo.manufacturer} ${benchmark.deviceInfo.model}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Helper Functions
 */

/**
 * Get severity color
 */
private fun getSeverityColor(severity: PerformanceSeverity): Color {
    return when (severity) {
        PerformanceSeverity.CRITICAL -> Color(0xFFD32F2F)
        PerformanceSeverity.HIGH -> Color(0xFFFF5722)
        PerformanceSeverity.MEDIUM -> Color(0xFFFF9800)
        PerformanceSeverity.LOW -> Color(0xFF4CAF50)
        PerformanceSeverity.INFO -> Color(0xFF2196F3)
    }
}

/**
 * Calculate health score
 */
private fun calculateHealthScore(metrics: List<PerformanceMetric>): Int {
    if (metrics.isEmpty()) return 100
    
    val severityWeights = mapOf(
        PerformanceSeverity.CRITICAL to 0.0,
        PerformanceSeverity.HIGH to 0.3,
        PerformanceSeverity.MEDIUM to 0.6,
        PerformanceSeverity.LOW to 0.8,
        PerformanceSeverity.INFO to 1.0
    )
    
    val averageScore = metrics.map { metric ->
        severityWeights[metric.severity] ?: 0.5
    }.average()
    
    return (averageScore * 100).roundToInt()
}

/**
 * Collect performance metrics
 */
private suspend fun collectPerformanceMetrics(context: Context): List<PerformanceMetric> {
    // Simulate collecting real performance metrics
    return listOf(
        PerformanceMetric(
            id = "cpu-${System.currentTimeMillis()}",
            type = PerformanceMetricType.CPU_USAGE,
            name = "CPU Usage",
            value = (10..90).random().toDouble(),
            unit = "%",
            threshold = 80.0,
            severity = PerformanceSeverity.values().random(),
            timestamp = Date(),
            source = "System Monitor"
        ),
        PerformanceMetric(
            id = "memory-${System.currentTimeMillis()}",
            type = PerformanceMetricType.MEMORY_USAGE,
            name = "Memory Usage",
            value = (30..95).random().toDouble(),
            unit = "%",
            threshold = 85.0,
            severity = PerformanceSeverity.values().random(),
            timestamp = Date(),
            source = "Memory Monitor"
        )
    )
}

/**
 * Detect performance issues
 */
private fun detectPerformanceIssues(
    metrics: List<PerformanceMetric>,
    thresholds: Map<PerformanceMetricType, Double>
): List<PerformanceIssue> {
    return metrics.filter { metric ->
        val threshold = thresholds[metric.type] ?: metric.threshold
        metric.value > threshold
    }.map { metric ->
        PerformanceIssue(
            id = "issue-${metric.id}",
            title = "High ${metric.name}",
            description = "${metric.name} is above threshold (${metric.value} > ${metric.threshold})",
            severity = metric.severity,
            category = metric.type,
            impact = "Performance degradation",
            detectedAt = metric.timestamp,
            affectedComponents = listOf(metric.source),
            suggestedOptimizations = generateOptimizationRecommendations(listOf())
        )
    }
}

/**
 * Generate optimization recommendations
 */
private fun generateOptimizationRecommendations(
    issues: List<PerformanceIssue>
): List<OptimizationRecommendation> {
    return listOf(
        OptimizationRecommendation(
            id = "opt-1",
            strategy = OptimizationStrategy.LAZY_LOADING,
            title = "Implement Lazy Loading",
            description = "Load content only when needed to reduce initial load time",
            estimatedImpact = "20-30% improvement in startup time",
            implementationEffort = ImplementationEffort.MEDIUM,
            priority = 8,
            steps = listOf(
                "Identify heavy components",
                "Implement lazy loading",
                "Test performance impact"
            )
        ),
        OptimizationRecommendation(
            id = "opt-2",
            strategy = OptimizationStrategy.CACHING,
            title = "Optimize Caching Strategy",
            description = "Implement efficient caching to reduce redundant operations",
            estimatedImpact = "15-25% improvement in response time",
            implementationEffort = ImplementationEffort.LOW,
            priority = 7,
            steps = listOf(
                "Analyze cache usage",
                "Implement cache optimization",
                "Monitor cache hit rate"
            )
        )
    )
}

/**
 * Run performance benchmark
 */
private suspend fun runPerformanceBenchmark(
    context: Context,
    onProgress: (Float) -> Unit
): PerformanceBenchmark {
    val startTime = System.currentTimeMillis()
    val metrics = mutableListOf<PerformanceMetric>()
    
    // Simulate benchmark tests
    for (i in 1..10) {
        onProgress(i / 10f)
        delay(200)
        
        metrics.addAll(collectPerformanceMetrics(context))
    }
    
    val endTime = System.currentTimeMillis()
    val duration = endTime - startTime
    
    return PerformanceBenchmark(
        id = "benchmark-${System.currentTimeMillis()}",
        name = "Performance Benchmark",
        description = "Comprehensive performance test",
        metrics = metrics,
        executedAt = Date(),
        duration = duration,
        environment = "Development",
        deviceInfo = DeviceInfo(
            model = Build.MODEL,
            manufacturer = Build.MANUFACTURER,
            osVersion = Build.VERSION.RELEASE,
            apiLevel = Build.VERSION.SDK_INT,
            totalMemory = Runtime.getRuntime().totalMemory(),
            availableMemory = Runtime.getRuntime().freeMemory(),
            cpuCores = Runtime.getRuntime().availableProcessors(),
            screenDensity = context.resources.displayMetrics.density
        ),
        score = calculateHealthScore(metrics)
    )
}