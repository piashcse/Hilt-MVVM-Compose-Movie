package com.xiaomi.base.preview.demos.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.xiaomi.base.preview.base.BasePreviewScreen
import com.xiaomi.base.preview.base.ComponentShowcase
import com.xiaomi.base.preview.base.InteractiveDemo
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseTheme

// Register health tracking previews
fun registerHealthTrackingPreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "health_dashboard",
            title = "Health Dashboard",
            description = "Complete health tracking dashboard with stats, charts, and quick actions",
            category = PreviewCategory.HEALTH,
            icon = Icons.Default.Dashboard,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("dashboard", "charts", "statistics", "health"),
            estimatedTime = "30 min",
            content = { HealthDashboardPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "step_counter",
            title = "Step Counter Widget",
            description = "Animated step counter with progress ring and daily goals",
            category = PreviewCategory.HEALTH,
            icon = Icons.Default.DirectionsWalk,
            difficulty = PreviewDifficulty.BEGINNER,
            tags = listOf("steps", "counter", "progress", "animation"),
            estimatedTime = "15 min",
            content = { StepCounterPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "heart_rate_monitor",
            title = "Heart Rate Monitor",
            description = "Real-time heart rate monitoring with pulse animation and history",
            category = PreviewCategory.HEALTH,
            icon = Icons.Default.Favorite,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("heart rate", "monitoring", "pulse", "real-time"),
            estimatedTime = "45 min",
            content = { HeartRateMonitorPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "water_intake_tracker",
            title = "Water Intake Tracker",
            description = "Interactive water intake tracker with visual progress and reminders",
            category = PreviewCategory.HEALTH,
            icon = Icons.Default.WaterDrop,
            difficulty = PreviewDifficulty.BEGINNER,
            tags = listOf("water", "hydration", "tracker", "progress"),
            estimatedTime = "20 min",
            content = { WaterIntakeTrackerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "sleep_analysis",
            title = "Sleep Analysis Chart",
            description = "Detailed sleep analysis with phases, quality metrics, and insights",
            category = PreviewCategory.HEALTH,
            icon = Icons.Default.Bedtime,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("sleep", "analysis", "charts", "insights"),
            estimatedTime = "40 min",
            content = { SleepAnalysisPreview() }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HealthDashboardPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Health Dashboard",
            subtitle = "Complete health tracking overview"
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Welcome Section
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Good Morning, John!",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "You're doing great! Keep up the healthy habits.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
                
                item {
                    // Quick Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        HealthStatCard(
                            title = "Steps",
                            value = "8,547",
                            goal = "10,000",
                            progress = 0.85f,
                            icon = Icons.Default.DirectionsWalk,
                            color = Color(0xFF4CAF50),
                            modifier = Modifier.weight(1f)
                        )
                        
                        HealthStatCard(
                            title = "Water",
                            value = "6",
                            goal = "8 glasses",
                            progress = 0.75f,
                            icon = Icons.Default.WaterDrop,
                            color = Color(0xFF2196F3),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        HealthStatCard(
                            title = "Sleep",
                            value = "7h 23m",
                            goal = "8h",
                            progress = 0.92f,
                            icon = Icons.Default.Bedtime,
                            color = Color(0xFF9C27B0),
                            modifier = Modifier.weight(1f)
                        )
                        
                        HealthStatCard(
                            title = "Heart Rate",
                            value = "72",
                            goal = "BPM",
                            progress = 1f,
                            icon = Icons.Default.Favorite,
                            color = Color(0xFFF44336),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                item {
                    // Recent Activities
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Recent Activities",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            ActivityItem(
                                title = "Morning Run",
                                subtitle = "3.2 km • 25 min",
                                time = "8:30 AM",
                                icon = Icons.Default.DirectionsRun,
                                color = Color(0xFF4CAF50)
                            )
                            
                            ActivityItem(
                                title = "Meditation",
                                subtitle = "10 min session",
                                time = "7:00 AM",
                                icon = Icons.Default.SelfImprovement,
                                color = Color(0xFF9C27B0)
                            )
                            
                            ActivityItem(
                                title = "Water Reminder",
                                subtitle = "Drink more water",
                                time = "6:30 AM",
                                icon = Icons.Default.WaterDrop,
                                color = Color(0xFF2196F3)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StepCounterPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Step Counter Widget",
            description = "Animated progress ring with step count and daily goal"
        ) {
            var steps by remember { mutableStateOf(8547) }
            val goal = 10000
            val progress = (steps.toFloat() / goal).coerceAtMost(1f)
            
            InteractiveDemo(
                title = "Step Counter Widget",
                description = "Interactive step counter with progress ring",
                controls = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { steps = (steps + 500).coerceAtMost(goal + 2000) }
                        ) {
                            Text("+500")
                        }
                        Button(
                            onClick = { steps = (steps - 500).coerceAtLeast(0) }
                        ) {
                            Text("-500")
                        }
                        Button(
                            onClick = { steps = goal }
                        ) {
                            Text("Goal")
                        }
                    }
                }
            ) {
                Card(
                    modifier = Modifier.size(200.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Progress Ring
                        CircularProgressIndicator(
                            progress = progress,
                            modifier = Modifier.size(160.dp),
                            strokeWidth = 12.dp,
                            color = if (progress >= 1f) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
                        )
                        
                        // Content
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.DirectionsWalk,
                                contentDescription = "Steps",
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = steps.toString(),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Text(
                                text = "of $goal steps",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            if (progress >= 1f) {
                                Text(
                                    text = "Goal Achieved! 🎉",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF4CAF50),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateMonitorPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Heart Rate Monitor",
            description = "Real-time heart rate with pulse animation"
        ) {
            var heartRate by remember { mutableStateOf(72) }
            var isMonitoring by remember { mutableStateOf(false) }
            
            InteractiveDemo(
                title = "Heart Rate Monitor",
                description = "Real-time heart rate monitoring with pulse animation",
                controls = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { isMonitoring = !isMonitoring }
                        ) {
                            Text(if (isMonitoring) "Stop" else "Start")
                        }
                        Button(
                            onClick = { heartRate = (60..100).random() }
                        ) {
                            Text("Random")
                        }
                    }
                }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Heart Icon with pulse animation
                            val pulseScale by animateFloatAsState(
                                targetValue = if (isMonitoring) 1.2f else 1f,
                                animationSpec = androidx.compose.animation.core.infiniteRepeatable<Float>(
                                    animation = androidx.compose.animation.core.tween(600),
                                    repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
                                )
                            )
                            
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Heart",
                                modifier = Modifier
                                    .size(64.dp)
                                    .graphicsLayer {
                                        scaleX = pulseScale
                                        scaleY = pulseScale
                                    },
                                tint = Color(0xFFF44336)
                            )
                            
                            // Heart Rate Display
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = heartRate.toString(),
                                    style = MaterialTheme.typography.displayMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF44336)
                                )
                                Text(
                                    text = "BPM",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            
                            // Status
                            Text(
                                text = if (isMonitoring) "Monitoring..." else "Tap Start to begin",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            // Heart Rate Zone
                            val zone = when (heartRate) {
                                in 0..60 -> "Resting" to Color(0xFF4CAF50)
                                in 61..100 -> "Normal" to Color(0xFF2196F3)
                                in 101..150 -> "Elevated" to Color(0xFFFF9800)
                                else -> "High" to Color(0xFFF44336)
                            }
                            
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = zone.second.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = zone.first,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = zone.second,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterIntakeTrackerPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Water Intake Tracker",
            description = "Track daily water consumption with visual progress"
        ) {
            var glassesConsumed by remember { mutableStateOf(6) }
            val dailyGoal = 8
            val progress = (glassesConsumed.toFloat() / dailyGoal).coerceAtMost(1f)
            
            InteractiveDemo(
                title = "Water Intake Tracker",
                description = "Interactive water consumption tracking",
                controls = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { glassesConsumed = (glassesConsumed + 1).coerceAtMost(dailyGoal + 2) }
                        ) {
                            Text("+1 Glass")
                        }
                        Button(
                            onClick = { glassesConsumed = (glassesConsumed - 1).coerceAtLeast(0) }
                        ) {
                            Text("-1 Glass")
                        }
                        Button(
                            onClick = { glassesConsumed = 0 }
                        ) {
                            Text("Reset")
                        }
                    }
                }
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Water bottle visualization
                        Box(
                            modifier = Modifier
                                .size(120.dp, 200.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
                                ),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            // Water level
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(progress)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFF64B5F6),
                                                Color(0xFF2196F3)
                                            )
                                        ),
                                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
                                    )
                            )
                            
                            // Water drop icon
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = "Water",
                                modifier = Modifier.size(32.dp),
                                tint = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Progress text
                        Text(
                            text = "$glassesConsumed / $dailyGoal glasses",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Text(
                            text = "${(progress * 100).toInt()}% of daily goal",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Progress bar
                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Color(0xFF2196F3)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Encouragement message
                        if (progress >= 1f) {
                            Text(
                                text = "🎉 Daily goal achieved!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF4CAF50),
                                fontWeight = FontWeight.SemiBold
                            )
                        } else {
                            Text(
                                text = "Keep going! ${dailyGoal - glassesConsumed} more to go.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepAnalysisPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Sleep Analysis",
            description = "Detailed sleep tracking with phases and quality metrics"
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Sleep duration
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Last Night",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "7h 23m",
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9C27B0)
                            )
                        }
                        
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF9C27B0).copy(alpha = 0.1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bedtime,
                                contentDescription = "Sleep",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(12.dp),
                                tint = Color(0xFF9C27B0)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Sleep phases
                    Text(
                        text = "Sleep Phases",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    SleepPhaseItem(
                        phase = "Deep Sleep",
                        duration = "2h 15m",
                        percentage = 31,
                        color = Color(0xFF1976D2)
                    )
                    
                    SleepPhaseItem(
                        phase = "Light Sleep",
                        duration = "4h 32m",
                        percentage = 62,
                        color = Color(0xFF42A5F5)
                    )
                    
                    SleepPhaseItem(
                        phase = "REM Sleep",
                        duration = "36m",
                        percentage = 7,
                        color = Color(0xFF9C27B0)
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Sleep quality
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SleepMetric(
                            title = "Quality",
                            value = "85%",
                            subtitle = "Good"
                        )
                        
                        SleepMetric(
                            title = "Efficiency",
                            value = "92%",
                            subtitle = "Excellent"
                        )
                        
                        SleepMetric(
                            title = "Restfulness",
                            value = "78%",
                            subtitle = "Good"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HealthStatCard(
    title: String,
    value: String,
    goal: String,
    progress: Float,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(24.dp),
                    tint = color
                )
                
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                    color = color,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = goal,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = color
            )
        }
    }
}

@Composable
fun ActivityItem(
    title: String,
    subtitle: String,
    time: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = color.copy(alpha = 0.1f),
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(20.dp),
                    tint = color
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = time,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SleepPhaseItem(
    phase: String,
    duration: String,
    percentage: Int,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = phase,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = duration,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.labelMedium,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SleepMetric(
    title: String,
    value: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}