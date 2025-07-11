package com.xiaomi.base.preview.demos.universal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.model.ItemStatus
import com.xiaomi.base.preview.base.BasePreviewScreen
import com.xiaomi.base.ui.components.*
import java.util.Date

/**
 * Showcase for universal components that can be used across different app domains
 */
@Composable
fun UniversalComponentShowcase() {
    BasePreviewScreen(
        title = "Universal Components",
        description = "Flexible components that can be adapted for any app type"
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Data Visualization Components
            item {
                ShowcaseSection("Data Visualization") {
                    MetricCardShowcase()
                }
            }
            
            item {
                ShowcaseSection("Progress Charts") {
                    ProgressChartShowcase()
                }
            }
            
            item {
                ShowcaseSection("Stats Grid") {
                    StatsGridShowcase()
                }
            }
            
            // Content Display Components
            item {
                ShowcaseSection("Universal Cards") {
                    UniversalCardShowcase()
                }
            }
            
            item {
                ShowcaseSection("Media Viewer") {
                    MediaViewerShowcase()
                }
            }
            
            // Input Components
            item {
                ShowcaseSection("Form Inputs") {
                    FormInputShowcase()
                }
            }
            
            item {
                ShowcaseSection("Interactive Inputs") {
                    InteractiveInputShowcase()
                }
            }
        }
    }
}

@Composable
private fun ShowcaseSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            content()
        }
    }
}

@Composable
private fun MetricCardShowcase() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MetricCard(
            title = "Total Items",
            value = "1,234",
            trend = TrendDirection.UP,
            modifier = Modifier.weight(1f)
        )
        
        MetricCard(
            title = "Active Users",
            value = "567",
            unit = "users",
            trend = TrendDirection.DOWN,
            modifier = Modifier.weight(1f)
        )
    }
    
    Spacer(modifier = Modifier.height(8.dp))
    
    MetricCard(
        title = "Weekly Progress",
        value = "89.5",
        unit = "%",
        trend = TrendDirection.UP
    )
}

@Composable
private fun ProgressChartShowcase() {
    val sampleData = listOf(
        ChartDataPoint("Mon", 20f),
        ChartDataPoint("Tue", 45f),
        ChartDataPoint("Wed", 30f),
        ChartDataPoint("Thu", 60f),
        ChartDataPoint("Fri", 75f)
    )
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProgressChart(
            data = sampleData,
            chartType = ChartType.LINE
        )
        
        ProgressChart(
            data = sampleData,
            chartType = ChartType.BAR
        )
    }
}

@Composable
private fun StatsGridShowcase() {
    val stats = listOf(
        StatItem("Revenue", "$12,345", "USD", TrendDirection.UP),
        StatItem("Orders", "89", null, TrendDirection.UP),
        StatItem("Conversion", "3.2", "%", TrendDirection.DOWN),
        StatItem("Satisfaction", "4.8", "★", TrendDirection.NEUTRAL)
    )
    
    StatsGrid(
        stats = stats,
        columns = 2
    )
}

@Composable
private fun UniversalCardShowcase() {
    val sampleItem = Item(
        id = 1,
        title = "Sample Universal Item",
        description = "This is a sample item that demonstrates how the universal card component can display any type of content.",
        score = 4.5f,
        status = ItemStatus.ACTIVE,
        tags = listOf("sample", "demo", "universal"),
        isFavorite = false,
        createdDate = Date(),
        metadata = mapOf("category" to "demo")
    )
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Compact Mode",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        UniversalCard(
            item = sampleItem,
            displayMode = CardDisplayMode.COMPACT
        )
        
        Text(
            text = "Standard Mode",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        UniversalCard(
            item = sampleItem,
            displayMode = CardDisplayMode.STANDARD
        )
        
        Text(
            text = "Detailed Mode",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        UniversalCard(
            item = sampleItem,
            displayMode = CardDisplayMode.DETAILED
        )
    }
}

@Composable
private fun MediaViewerShowcase() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MediaViewer(
            mediaUrl = "sample_image.jpg",
            mediaType = MediaType.IMAGE,
            modifier = Modifier.weight(1f),
            onEdit = { /* Handle edit */ }
        )
        
        MediaViewer(
            mediaUrl = "sample_video.mp4",
            mediaType = MediaType.VIDEO,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FormInputShowcase() {
    var textValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var numberValue by remember { mutableStateOf("") }
    var multilineValue by remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UniversalFormInput(
            inputType = InputType.TEXT,
            label = "Full Name",
            value = textValue,
            onValueChange = { textValue = it },
            placeholder = "Enter your full name",
            isRequired = true,
            leadingIcon = Icons.Default.Person
        )
        
        UniversalFormInput(
            inputType = InputType.EMAIL,
            label = "Email Address",
            value = emailValue,
            onValueChange = { emailValue = it },
            placeholder = "Enter your email",
            leadingIcon = Icons.Default.Email,
            validator = { it.contains("@") }
        )
        
        UniversalFormInput(
            inputType = InputType.NUMBER,
            label = "Age",
            value = numberValue,
            onValueChange = { numberValue = it },
            placeholder = "Enter your age",
            leadingIcon = Icons.Default.Numbers
        )
        
        UniversalFormInput(
            inputType = InputType.MULTILINE,
            label = "Comments",
            value = multilineValue,
            onValueChange = { multilineValue = it },
            placeholder = "Enter your comments here...",
            supportingText = "Optional feedback"
        )
    }
}

@Composable
private fun InteractiveInputShowcase() {
    var rating by remember { mutableStateOf(0f) }
    var counter by remember { mutableStateOf(5) }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GenericProgressCard(
            title = "Daily Goal",
            currentValue = 750f,
            targetValue = 1000f,
            unit = "steps",
            progressColor = Color(0xFF4CAF50)
        )
        
        UniversalRatingInput(
            rating = rating,
            onRatingChange = { rating = it },
            label = "Rate this experience",
            maxRating = 5
        )
        
        UniversalCounterInput(
            value = counter,
            onValueChange = { counter = it },
            label = "Quantity",
            minValue = 1,
            maxValue = 10,
            unit = "items"
        )
    }
}

// ===== Preview Functions =====

@Preview(showBackground = true)
@Composable
fun UniversalComponentShowcasePreview() {
    MaterialTheme {
        UniversalComponentShowcase()
    }
}

@Preview(showBackground = true)
@Composable
fun MetricCardShowcasePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            MetricCardShowcase()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormInputShowcasePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            FormInputShowcase()
        }
    }
} 