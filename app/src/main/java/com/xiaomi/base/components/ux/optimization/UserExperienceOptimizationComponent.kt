package com.xiaomi.base.components.ux.optimization

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import com.xiaomi.base.ui.theme.Orange

/**
 * User Experience Metric Type
 */
enum class UXMetricType {
    PAGE_LOAD_TIME,
    USER_ENGAGEMENT,
    TASK_COMPLETION_RATE,
    ERROR_RATE,
    USER_SATISFACTION,
    BOUNCE_RATE,
    SESSION_DURATION,
    CONVERSION_RATE,
    ACCESSIBILITY_SCORE,
    PERFORMANCE_SCORE
}

/**
 * User Journey Stage
 */
enum class UserJourneyStage {
    AWARENESS,
    INTEREST,
    CONSIDERATION,
    INTENT,
    EVALUATION,
    PURCHASE,
    RETENTION,
    ADVOCACY
}

/**
 * UX Issue Severity
 */
enum class UXIssueSeverity {
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
    A_B_TESTING,
    PERSONALIZATION,
    PERFORMANCE_OPTIMIZATION,
    ACCESSIBILITY_IMPROVEMENT,
    CONTENT_OPTIMIZATION,
    NAVIGATION_ENHANCEMENT,
    VISUAL_DESIGN_IMPROVEMENT,
    INTERACTION_OPTIMIZATION
}

/**
 * User Experience Metric
 */
data class UXMetric(
    val type: UXMetricType,
    val value: Double,
    val unit: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val target: Double? = null,
    val trend: Double = 0.0, // Percentage change
    val category: String = "",
    val metadata: Map<String, Any> = emptyMap()
)

/**
 * User Journey Event
 */
data class UserJourneyEvent(
    val id: String,
    val stage: UserJourneyStage,
    val action: String,
    val timestamp: LocalDateTime,
    val duration: Long, // milliseconds
    val success: Boolean,
    val metadata: Map<String, Any> = emptyMap()
)

/**
 * User Journey
 */
data class UserJourney(
    val userId: String,
    val sessionId: String,
    val events: List<UserJourneyEvent>,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val totalDuration: Long,
    val completionRate: Double,
    val satisfactionScore: Double?
)

/**
 * UX Issue
 */
data class UXIssue(
    val id: String,
    val title: String,
    val description: String,
    val severity: UXIssueSeverity,
    val category: String,
    val affectedUsers: Int,
    val impact: String,
    val recommendation: String,
    val estimatedEffort: String,
    val priority: Int,
    val detectedAt: LocalDateTime = LocalDateTime.now()
)

/**
 * Optimization Recommendation
 */
data class OptimizationRecommendation(
    val id: String,
    val title: String,
    val description: String,
    val strategy: OptimizationStrategy,
    val expectedImpact: String,
    val effort: String,
    val priority: Int,
    val metrics: List<UXMetricType>,
    val implementation: String,
    val estimatedROI: Double?
)

/**
 * A/B Test Configuration
 */
data class ABTestConfig(
    val id: String,
    val name: String,
    val description: String,
    val variants: List<String>,
    val trafficSplit: Map<String, Double>,
    val metrics: List<UXMetricType>,
    val duration: Int, // days
    val isActive: Boolean = false
)

/**
 * UX Optimization Configuration
 */
data class UXOptimizationConfig(
    val enableRealTimeTracking: Boolean = true,
    val enableABTesting: Boolean = true,
    val enablePersonalization: Boolean = true,
    val enablePerformanceMonitoring: Boolean = true,
    val enableAccessibilityTracking: Boolean = true,
    val enableUserFeedback: Boolean = true,
    val enableHeatmapTracking: Boolean = true,
    val enableJourneyAnalytics: Boolean = true,
    val samplingRate: Double = 1.0,
    val retentionPeriod: Int = 30 // days
)

/**
 * User Experience Optimization Component
 * Comprehensive UX optimization and user journey analytics
 * 
 * @param config UX optimization configuration
 * @param onMetricUpdate Callback when metrics are updated
 * @param onIssueDetected Callback when UX issue is detected
 * @param onRecommendationGenerated Callback when optimization recommendation is generated
 */
@Composable
fun UserExperienceOptimizationComponent(
    config: UXOptimizationConfig = UXOptimizationConfig(),
    onMetricUpdate: (UXMetric) -> Unit = {},
    onIssueDetected: (UXIssue) -> Unit = {},
    onRecommendationGenerated: (OptimizationRecommendation) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var metrics by remember { mutableStateOf<List<UXMetric>>(emptyList()) }
    var issues by remember { mutableStateOf<List<UXIssue>>(emptyList()) }
    var recommendations by remember { mutableStateOf<List<OptimizationRecommendation>>(emptyList()) }
    var journeys by remember { mutableStateOf<List<UserJourney>>(emptyList()) }
    var abTests by remember { mutableStateOf<List<ABTestConfig>>(emptyList()) }
    
    // Simulate real-time data updates
    LaunchedEffect(config.enableRealTimeTracking) {
        if (config.enableRealTimeTracking) {
            while (true) {
                delay(5000) // Update every 5 seconds
                
                // Generate new metrics
                val newMetrics = generateMockUXMetrics()
                metrics = newMetrics
                newMetrics.forEach { onMetricUpdate(it) }
                
                // Detect issues
                val detectedIssues = detectUXIssues(newMetrics)
                issues = detectedIssues
                detectedIssues.forEach { onIssueDetected(it) }
                
                // Generate recommendations
                val newRecommendations = generateOptimizationRecommendations(newMetrics, detectedIssues)
                recommendations = newRecommendations
                newRecommendations.forEach { onRecommendationGenerated(it) }
                
                // Update journeys
                journeys = generateMockUserJourneys()
                
                // Update A/B tests
                abTests = generateMockABTests()
            }
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        UXOptimizationHeader()
        
        // Tab Navigation
        TabRow(
            selectedTabIndex = selectedTab
        ) {
            val tabs = listOf(
                "Overview",
                "Metrics",
                "Journey",
                "Issues",
                "A/B Tests",
                "Recommendations"
            )
            
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Tab Content
        when (selectedTab) {
            0 -> UXOverviewTab(metrics, issues, recommendations)
            1 -> UXMetricsTab(metrics)
            2 -> UserJourneyTab(journeys)
            3 -> UXIssuesTab(issues)
            4 -> ABTestingTab(abTests)
            5 -> OptimizationRecommendationsTab(recommendations)
        }
    }
}

/**
 * Real-time UX Metrics Component
 * Monitor UX metrics in real-time
 * 
 * @param metrics List of UX metrics
 * @param onMetricClick Callback when metric is clicked
 */
@Composable
fun RealTimeUXMetricsComponent(
    metrics: List<UXMetric>,
    onMetricClick: (UXMetric) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(metrics) { metric ->
            UXMetricCard(
                metric = metric,
                onClick = { onMetricClick(metric) }
            )
        }
    }
}

/**
 * User Journey Analytics Component
 * Analyze user journeys and behavior
 * 
 * @param journeys List of user journeys
 * @param onJourneySelect Callback when journey is selected
 */
@Composable
fun UserJourneyAnalyticsComponent(
    journeys: List<UserJourney>,
    onJourneySelect: (UserJourney) -> Unit = {}
) {
    var selectedJourney by remember { mutableStateOf<UserJourney?>(null) }
    
    if (selectedJourney != null) {
        UserJourneyDetailView(
            journey = selectedJourney!!,
            onBack = { selectedJourney = null }
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(journeys) { journey ->
                UserJourneyCard(
                    journey = journey,
                    onClick = {
                        selectedJourney = journey
                        onJourneySelect(journey)
                    }
                )
            }
        }
    }
}

/**
 * A/B Testing Component
 * Manage and monitor A/B tests
 * 
 * @param tests List of A/B tests
 * @param onTestToggle Callback when test is toggled
 * @param onTestCreate Callback when new test is created
 */
@Composable
fun ABTestingComponent(
    tests: List<ABTestConfig>,
    onTestToggle: (ABTestConfig, Boolean) -> Unit = { _, _ -> },
    onTestCreate: () -> Unit = {}
) {
    Column {
        // Header with create button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "A/B Tests",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Button(
                onClick = onTestCreate
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Test"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Create Test")
            }
        }
        
        // Tests list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(tests) { test ->
                ABTestCard(
                    test = test,
                    onToggle = { isActive ->
                        onTestToggle(test, isActive)
                    }
                )
            }
        }
    }
}

/**
 * UX Issue Detection Component
 * Detect and display UX issues
 * 
 * @param issues List of detected issues
 * @param onIssueResolve Callback when issue is resolved
 */
@Composable
fun UXIssueDetectionComponent(
    issues: List<UXIssue>,
    onIssueResolve: (UXIssue) -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Detected Issues (${issues.size})",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(issues) { issue ->
            UXIssueCard(
                issue = issue,
                onResolve = { onIssueResolve(issue) }
            )
        }
    }
}

/**
 * Optimization Recommendations Component
 * Display optimization recommendations
 * 
 * @param recommendations List of recommendations
 * @param onRecommendationApply Callback when recommendation is applied
 */
@Composable
fun OptimizationRecommendationsComponent(
    recommendations: List<OptimizationRecommendation>,
    onRecommendationApply: (OptimizationRecommendation) -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Optimization Recommendations",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(recommendations) { recommendation ->
            OptimizationRecommendationCard(
                recommendation = recommendation,
                onApply = { onRecommendationApply(recommendation) }
            )
        }
    }
}

/**
 * Helper Components
 */

@Composable
private fun UXOptimizationHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = "UX Optimization",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = "UX Optimization Dashboard",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Monitor and optimize user experience",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun UXOverviewTab(
    metrics: List<UXMetric>,
    issues: List<UXIssue>,
    recommendations: List<OptimizationRecommendation>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            UXOverviewCards(metrics, issues, recommendations)
        }
        
        item {
            Text(
                text = "Key Metrics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
        
        item {
            RealTimeUXMetricsComponent(metrics.take(4))
        }
        
        item {
            Text(
                text = "Critical Issues",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
        
        items(issues.filter { it.severity == UXIssueSeverity.CRITICAL }.take(3)) { issue ->
            UXIssueCard(issue = issue)
        }
    }
}

@Composable
private fun UXMetricsTab(metrics: List<UXMetric>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "UX Metrics",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(metrics) { metric ->
            UXMetricDetailCard(metric = metric)
        }
    }
}

@Composable
private fun UserJourneyTab(journeys: List<UserJourney>) {
    UserJourneyAnalyticsComponent(journeys = journeys)
}

@Composable
private fun UXIssuesTab(issues: List<UXIssue>) {
    UXIssueDetectionComponent(issues = issues)
}

@Composable
private fun ABTestingTab(tests: List<ABTestConfig>) {
    ABTestingComponent(tests = tests)
}

@Composable
private fun OptimizationRecommendationsTab(recommendations: List<OptimizationRecommendation>) {
    OptimizationRecommendationsComponent(recommendations = recommendations)
}

@Composable
private fun UXOverviewCards(
    metrics: List<UXMetric>,
    issues: List<UXIssue>,
    recommendations: List<OptimizationRecommendation>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${metrics.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Metrics",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        Card(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${issues.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (issues.any { it.severity == UXIssueSeverity.CRITICAL }) Color.Red else MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Issues",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        
        Card(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${recommendations.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Recommendations",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun UXMetricCard(
    metric: UXMetric,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { onClick() }
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
                    text = formatMetricValue(metric.value, metric.unit),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                if (metric.trend != 0.0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (metric.trend > 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                            contentDescription = "Trend",
                            tint = if (metric.trend > 0) Color.Green else Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${if (metric.trend > 0) "+" else ""}${String.format("%.1f", metric.trend)}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (metric.trend > 0) Color.Green else Color.Red
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = formatMetricType(metric.type),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            if (metric.target != null) {
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Target: ${formatMetricValue(metric.target, metric.unit)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun UXMetricDetailCard(metric: UXMetric) {
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
                        text = formatMetricType(metric.type),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = formatMetricValue(metric.value, metric.unit),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                if (metric.trend != 0.0) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (metric.trend > 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                                contentDescription = "Trend",
                                tint = if (metric.trend > 0) Color.Green else Color.Red,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "${if (metric.trend > 0) "+" else ""}${String.format("%.1f", metric.trend)}%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (metric.trend > 0) Color.Green else Color.Red,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        Text(
                            text = "vs last period",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            if (metric.target != null) {
                Spacer(modifier = Modifier.height(8.dp))
                
                val progress = (metric.value / metric.target).toFloat().coerceIn(0f, 1f)
                
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progress to target",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Updated: ${metric.timestamp.format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun UserJourneyCard(
    journey: UserJourney,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
                        text = "User ${journey.userId.take(8)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = "${journey.events.size} events • ${formatDuration(journey.totalDuration)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${(journey.completionRate * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (journey.completionRate > 0.8) Color.Green else if (journey.completionRate > 0.5) Orange else Color.Red
                    )
                    
                    Text(
                        text = "Completion",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Journey stages visualization
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(UserJourneyStage.values()) { stage ->
                    val hasEvent = journey.events.any { it.stage == stage }
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (hasEvent) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline
                            )
                    )
                }
            }
            
            if (journey.satisfactionScore != null) {
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Satisfaction: ",
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < journey.satisfactionScore) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Star",
                            tint = if (index < journey.satisfactionScore) Color(0xFFFFD700) else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserJourneyDetailView(
    journey: UserJourney,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column {
                Text(
                    text = "User Journey Details",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "User ${journey.userId.take(8)} • Session ${journey.sessionId.take(8)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Journey events
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(journey.events) { event ->
                UserJourneyEventCard(event = event)
            }
        }
    }
}

@Composable
private fun UserJourneyEventCard(event: UserJourneyEvent) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stage indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (event.success) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (event.success) Icons.Default.CheckCircle else Icons.Default.Error,
                    contentDescription = "Status",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.action,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = "${event.stage.name} • ${formatDuration(event.duration)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = event.timestamp.format(DateTimeFormatter.ofPattern("MMM dd, HH:mm:ss")),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun UXIssueCard(
    issue: UXIssue,
    onResolve: () -> Unit = {}
) {
    val severityColor = when (issue.severity) {
        UXIssueSeverity.CRITICAL -> Color.Red
        UXIssueSeverity.HIGH -> Color(0xFFFF5722)
        UXIssueSeverity.MEDIUM -> Color(0xFFFF9800)
        UXIssueSeverity.LOW -> Color(0xFF4CAF50)
        UXIssueSeverity.INFO -> Color(0xFF2196F3)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = severityColor.copy(alpha = 0.1f)
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
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = issue.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Badge(
                    containerColor = severityColor
                ) {
                    Text(
                        text = issue.severity.name,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${issue.affectedUsers} users affected",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "Priority: ${issue.priority}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (issue.recommendation.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Recommendation: ${issue.recommendation}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onResolve
                ) {
                    Text("Mark Resolved")
                }
            }
        }
    }
}

@Composable
private fun ABTestCard(
    test: ABTestConfig,
    onToggle: (Boolean) -> Unit = {}
) {
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
                        text = test.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = test.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Switch(
                    checked = test.isActive,
                    onCheckedChange = onToggle
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${test.variants.size} variants",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "${test.duration} days",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (test.isActive) {
                Spacer(modifier = Modifier.height(8.dp))
                
                // Traffic split visualization
                Column {
                    Text(
                        text = "Traffic Split:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                    
                    test.variants.forEach { variant ->
                        val split = test.trafficSplit[variant] ?: 0.0
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = variant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "${(split * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OptimizationRecommendationCard(
    recommendation: OptimizationRecommendation,
    onApply: () -> Unit = {}
) {
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
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = recommendation.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Badge(
                    containerColor = when (recommendation.priority) {
                        1 -> Color.Red
                        2 -> Orange
                        3 -> Color.Blue
                        else -> Color.Gray
                    }
                ) {
                    Text(
                        text = "P${recommendation.priority}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Impact: ${recommendation.expectedImpact}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "Effort: ${recommendation.effort}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (recommendation.estimatedROI != null) {
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Estimated ROI: ${(recommendation.estimatedROI * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Green,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onApply
                ) {
                    Text("Apply Recommendation")
                }
            }
        }
    }
}

/**
 * Helper Functions
 */

private fun formatMetricType(type: UXMetricType): String {
    return when (type) {
        UXMetricType.PAGE_LOAD_TIME -> "Page Load Time"
        UXMetricType.USER_ENGAGEMENT -> "User Engagement"
        UXMetricType.TASK_COMPLETION_RATE -> "Task Completion Rate"
        UXMetricType.ERROR_RATE -> "Error Rate"
        UXMetricType.USER_SATISFACTION -> "User Satisfaction"
        UXMetricType.BOUNCE_RATE -> "Bounce Rate"
        UXMetricType.SESSION_DURATION -> "Session Duration"
        UXMetricType.CONVERSION_RATE -> "Conversion Rate"
        UXMetricType.ACCESSIBILITY_SCORE -> "Accessibility Score"
        UXMetricType.PERFORMANCE_SCORE -> "Performance Score"
    }
}

private fun formatMetricValue(value: Double, unit: String): String {
    return when (unit) {
        "ms" -> "${value.toInt()}ms"
        "s" -> "${String.format("%.1f", value)}s"
        "%" -> "${(value * 100).toInt()}%"
        "score" -> "${String.format("%.1f", value)}"
        else -> "${String.format("%.2f", value)} $unit"
    }
}

private fun formatDuration(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    
    return when {
        hours > 0 -> "${hours}h ${minutes % 60}m"
        minutes > 0 -> "${minutes}m ${seconds % 60}s"
        else -> "${seconds}s"
    }
}

private fun generateMockUXMetrics(): List<UXMetric> {
    return listOf(
        UXMetric(
            type = UXMetricType.PAGE_LOAD_TIME,
            value = Random.nextDouble(1.0, 5.0),
            unit = "s",
            target = 3.0,
            trend = Random.nextDouble(-10.0, 10.0)
        ),
        UXMetric(
            type = UXMetricType.USER_ENGAGEMENT,
            value = Random.nextDouble(0.6, 0.9),
            unit = "%",
            target = 0.8,
            trend = Random.nextDouble(-5.0, 15.0)
        ),
        UXMetric(
            type = UXMetricType.TASK_COMPLETION_RATE,
            value = Random.nextDouble(0.7, 0.95),
            unit = "%",
            target = 0.9,
            trend = Random.nextDouble(-3.0, 8.0)
        ),
        UXMetric(
            type = UXMetricType.ERROR_RATE,
            value = Random.nextDouble(0.01, 0.05),
            unit = "%",
            target = 0.02,
            trend = Random.nextDouble(-20.0, 5.0)
        ),
        UXMetric(
            type = UXMetricType.USER_SATISFACTION,
            value = Random.nextDouble(3.5, 4.8),
            unit = "score",
            target = 4.5,
            trend = Random.nextDouble(-2.0, 12.0)
        ),
        UXMetric(
            type = UXMetricType.CONVERSION_RATE,
            value = Random.nextDouble(0.02, 0.08),
            unit = "%",
            target = 0.05,
            trend = Random.nextDouble(-15.0, 25.0)
        )
    )
}

private fun generateMockUserJourneys(): List<UserJourney> {
    return (1..5).map { index ->
        val events = (1..Random.nextInt(3, 8)).map { eventIndex ->
            UserJourneyEvent(
                id = "event_${index}_$eventIndex",
                stage = UserJourneyStage.values().random(),
                action = listOf(
                    "View Product",
                    "Add to Cart",
                    "Checkout",
                    "Search",
                    "Filter Results",
                    "Read Reviews",
                    "Compare Products",
                    "Contact Support"
                ).random(),
                timestamp = LocalDateTime.now().minusMinutes(Random.nextLong(1, 120)),
                duration = Random.nextLong(1000, 30000),
                success = Random.nextBoolean()
            )
        }
        
        UserJourney(
            userId = "user_$index",
            sessionId = "session_${index}_${Random.nextInt(1000, 9999)}",
            events = events,
            startTime = events.minByOrNull { it.timestamp }?.timestamp ?: LocalDateTime.now(),
            endTime = events.maxByOrNull { it.timestamp }?.timestamp,
            totalDuration = events.sumOf { it.duration },
            completionRate = Random.nextDouble(0.3, 1.0),
            satisfactionScore = if (Random.nextBoolean()) Random.nextDouble(1.0, 5.0) else null
        )
    }
}

private fun generateMockABTests(): List<ABTestConfig> {
    return listOf(
        ABTestConfig(
            id = "test_1",
            name = "Checkout Button Color",
            description = "Testing different colors for the checkout button",
            variants = listOf("Blue", "Green", "Orange"),
            trafficSplit = mapOf("Blue" to 0.33, "Green" to 0.33, "Orange" to 0.34),
            metrics = listOf(UXMetricType.CONVERSION_RATE, UXMetricType.USER_ENGAGEMENT),
            duration = 14,
            isActive = true
        ),
        ABTestConfig(
            id = "test_2",
            name = "Product Page Layout",
            description = "Testing different layouts for product pages",
            variants = listOf("Layout A", "Layout B"),
            trafficSplit = mapOf("Layout A" to 0.5, "Layout B" to 0.5),
            metrics = listOf(UXMetricType.USER_ENGAGEMENT, UXMetricType.TASK_COMPLETION_RATE),
            duration = 21,
            isActive = false
        )
    )
}

private fun detectUXIssues(metrics: List<UXMetric>): List<UXIssue> {
    val issues = mutableListOf<UXIssue>()
    
    metrics.forEach { metric ->
        when (metric.type) {
            UXMetricType.PAGE_LOAD_TIME -> {
                if (metric.value > 3.0) {
                    issues.add(
                        UXIssue(
                            id = "slow_loading",
                            title = "Slow Page Loading",
                            description = "Page load time exceeds 3 seconds",
                            severity = if (metric.value > 5.0) UXIssueSeverity.CRITICAL else UXIssueSeverity.HIGH,
                            category = "Performance",
                            affectedUsers = Random.nextInt(100, 1000),
                            impact = "Users may abandon the page",
                            recommendation = "Optimize images and reduce bundle size",
                            estimatedEffort = "Medium",
                            priority = if (metric.value > 5.0) 1 else 2
                        )
                    )
                }
            }
            UXMetricType.ERROR_RATE -> {
                if (metric.value > 0.03) {
                    issues.add(
                        UXIssue(
                            id = "high_error_rate",
                            title = "High Error Rate",
                            description = "Error rate is above acceptable threshold",
                            severity = UXIssueSeverity.HIGH,
                            category = "Reliability",
                            affectedUsers = Random.nextInt(50, 500),
                            impact = "Users experiencing frequent errors",
                            recommendation = "Investigate and fix common error sources",
                            estimatedEffort = "High",
                            priority = 1
                        )
                    )
                }
            }
            UXMetricType.TASK_COMPLETION_RATE -> {
                if (metric.value < 0.8) {
                    issues.add(
                        UXIssue(
                            id = "low_completion_rate",
                            title = "Low Task Completion Rate",
                            description = "Users are not completing tasks successfully",
                            severity = UXIssueSeverity.MEDIUM,
                            category = "Usability",
                            affectedUsers = Random.nextInt(200, 800),
                            impact = "Reduced user satisfaction and conversions",
                            recommendation = "Simplify user flows and improve navigation",
                            estimatedEffort = "Medium",
                            priority = 2
                        )
                    )
                }
            }
            else -> {}
        }
    }
    
    return issues
}

private fun generateOptimizationRecommendations(
    metrics: List<UXMetric>,
    issues: List<UXIssue>
): List<OptimizationRecommendation> {
    val recommendations = mutableListOf<OptimizationRecommendation>()
    
    // Generate recommendations based on metrics and issues
    if (issues.any { it.category == "Performance" }) {
        recommendations.add(
            OptimizationRecommendation(
                id = "perf_opt_1",
                title = "Implement Lazy Loading",
                description = "Implement lazy loading for images and components to improve page load times",
                strategy = OptimizationStrategy.PERFORMANCE_OPTIMIZATION,
                expectedImpact = "20-30% improvement in load times",
                effort = "Medium",
                priority = 1,
                metrics = listOf(UXMetricType.PAGE_LOAD_TIME, UXMetricType.USER_ENGAGEMENT),
                implementation = "Use React.lazy() and Intersection Observer API",
                estimatedROI = 0.25
            )
        )
    }
    
    if (issues.any { it.category == "Usability" }) {
        recommendations.add(
            OptimizationRecommendation(
                id = "ux_opt_1",
                title = "Simplify Navigation",
                description = "Redesign navigation to reduce cognitive load and improve task completion",
                strategy = OptimizationStrategy.NAVIGATION_ENHANCEMENT,
                expectedImpact = "15-25% increase in task completion",
                effort = "High",
                priority = 2,
                metrics = listOf(UXMetricType.TASK_COMPLETION_RATE, UXMetricType.USER_SATISFACTION),
                implementation = "Conduct user testing and implement simplified menu structure",
                estimatedROI = 0.35
            )
        )
    }
    
    // Always suggest A/B testing
    recommendations.add(
        OptimizationRecommendation(
            id = "ab_test_1",
            title = "A/B Test Call-to-Action Buttons",
            description = "Test different CTA button designs to improve conversion rates",
            strategy = OptimizationStrategy.A_B_TESTING,
            expectedImpact = "5-15% increase in conversions",
            effort = "Low",
            priority = 3,
            metrics = listOf(UXMetricType.CONVERSION_RATE, UXMetricType.USER_ENGAGEMENT),
            implementation = "Create variants and split traffic using A/B testing platform",
            estimatedROI = 0.18
        )
    )
    
    return recommendations
}