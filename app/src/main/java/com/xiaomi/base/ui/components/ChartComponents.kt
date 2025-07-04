package com.xiaomi.base.ui.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.ui.theme.*
import com.xiaomi.base.utils.*
import kotlin.random.Random

// Chart Configuration Components
@Composable
fun ChartAppearanceCard(
    onColorSchemeChange: (String) -> Unit = {},
    onGridToggle: (Boolean) -> Unit = {},
    onLegendToggle: (Boolean) -> Unit = {},
    onAnimationToggle: (Boolean) -> Unit = {},
    selectedColorScheme: String = "Default",
    showGrid: Boolean = true,
    showLegend: Boolean = true,
    enableAnimation: Boolean = true
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Chart Appearance",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Color Scheme Selection
            Text(
                text = "Color Scheme",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val colorSchemes = listOf("Default", "Pastel", "Vibrant", "Monochrome", "Dark")
                items(colorSchemes) { scheme ->
                    ColorSchemeChip(
                        name = scheme,
                        selected = scheme == selectedColorScheme,
                        onClick = { onColorSchemeChange(scheme) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Toggle Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show Grid",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = showGrid,
                    onCheckedChange = onGridToggle
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show Legend",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = showLegend,
                    onCheckedChange = onLegendToggle
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable Animation",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = enableAnimation,
                    onCheckedChange = onAnimationToggle
                )
            }
        }
    }
}

@Composable
fun ColorSchemeChip(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Blue.copy(alpha = 0.1f) else Color.Transparent
    val borderColor = if (selected) Blue else Color.Gray.copy(alpha = 0.3f)
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color preview dots
            when (name) {
                "Default" -> {
                    ColorDot(Blue)
                    ColorDot(Green)
                    ColorDot(Orange)
                }
                "Pastel" -> {
                    ColorDot(Color(0xFFAEC6CF))
                    ColorDot(Color(0xFFFFDAB9))
                    ColorDot(Color(0xFFDEA5A4))
                }
                "Vibrant" -> {
                    ColorDot(Color(0xFFFF5252))
                    ColorDot(Color(0xFF00E676))
                    ColorDot(Color(0xFF2979FF))
                }
                "Monochrome" -> {
                    ColorDot(Color(0xFF212121))
                    ColorDot(Color(0xFF616161))
                    ColorDot(Color(0xFFBDBDBD))
                }
                "Dark" -> {
                    ColorDot(Color(0xFF1E88E5))
                    ColorDot(Color(0xFF43A047))
                    ColorDot(Color(0xFFE53935))
                }
            }
            
            Spacer(modifier = Modifier.width(4.dp))
            
            Text(
                text = name,
                fontSize = 12.sp,
                color = if (selected) Blue else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ColorDot(color: Color) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .background(color, CircleShape)
            .padding(end = 4.dp)
    )
}

@Composable
fun DataProcessingCard(
    onSmoothingChange: (Float) -> Unit = {},
    onAggregationChange: (String) -> Unit = {},
    onOutlierRemovalToggle: (Boolean) -> Unit = {},
    smoothingLevel: Float = 0.5f,
    selectedAggregation: String = "Average",
    removeOutliers: Boolean = true
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Data Processing",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Smoothing Slider
            Text(
                text = "Smoothing: ${(smoothingLevel * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Slider(
                value = smoothingLevel,
                onValueChange = onSmoothingChange,
                valueRange = 0f..1f,
                steps = 10
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Aggregation Method
            Text(
                text = "Aggregation Method",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val aggregationMethods = listOf("Average", "Sum", "Maximum", "Minimum", "Median")
                items(aggregationMethods) { method ->
                    AggregationMethodChip(
                        name = method,
                        selected = method == selectedAggregation,
                        onClick = { onAggregationChange(method) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Outlier Removal
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remove Outliers",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = removeOutliers,
                    onCheckedChange = onOutlierRemovalToggle
                )
            }
        }
    }
}

@Composable
fun AggregationMethodChip(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Blue.copy(alpha = 0.1f) else Color.Transparent
    val borderColor = if (selected) Blue else Color.Gray.copy(alpha = 0.3f)
    val textColor = if (selected) Blue else MaterialTheme.colorScheme.onSurface
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = name,
            fontSize = 12.sp,
            color = textColor
        )
    }
}

// Chart Display Components
@Composable
fun LineChartCard(
    title: String,
    data: List<Pair<Float, Float>>,
    xLabel: String = "X Axis",
    yLabel: String = "Y Axis",
    color: Color = Blue,
    showGrid: Boolean = true,
    fillArea: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (showGrid) {
                        drawGrid(Color.Gray.copy(alpha = 0.2f))
                    }
                    
                    drawAxes(Color.Gray)
                    
                    if (data.isNotEmpty()) {
                        val xValues = data.map { it.first }
                        val yValues = data.map { it.second }
                        val xMin = xValues.minOrNull() ?: 0f
                        val xMax = xValues.maxOrNull() ?: 1f
                        val yMin = yValues.minOrNull() ?: 0f
                        val yMax = yValues.maxOrNull() ?: 1f
                        
                        drawLineSeries(
                            data = data,
                            color = color,
                            xRange = Pair(xMin, xMax),
                            yRange = Pair(yMin, yMax),
                            fillArea = fillArea
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = xLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = yLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun BarChartCard(
    title: String,
    data: List<Pair<Float, Float>>,
    xLabel: String = "X Axis",
    yLabel: String = "Y Axis",
    color: Color = Green,
    showGrid: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (showGrid) {
                        drawGrid(Color.Gray.copy(alpha = 0.2f))
                    }
                    
                    drawAxes(Color.Gray)
                    
                    if (data.isNotEmpty()) {
                        val xValues = data.map { it.first }
                        val yValues = data.map { it.second }
                        val xMin = xValues.minOrNull() ?: 0f
                        val xMax = xValues.maxOrNull() ?: 1f
                        val yMin = yValues.minOrNull() ?: 0f
                        val yMax = yValues.maxOrNull() ?: 1f
                        
                        drawBarSeries(
                            data = data,
                            color = color,
                            xRange = Pair(xMin, xMax),
                            yRange = Pair(yMin, yMax)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = xLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = yLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ScatterChartCard(
    title: String,
    data: List<Pair<Float, Float>>,
    xLabel: String = "X Axis",
    yLabel: String = "Y Axis",
    color: Color = Purple,
    showGrid: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (showGrid) {
                        drawGrid(Color.Gray.copy(alpha = 0.2f))
                    }
                    
                    drawAxes(Color.Gray)
                    
                    if (data.isNotEmpty()) {
                        val xValues = data.map { it.first }
                        val yValues = data.map { it.second }
                        val xMin = xValues.minOrNull() ?: 0f
                        val xMax = xValues.maxOrNull() ?: 1f
                        val yMin = yValues.minOrNull() ?: 0f
                        val yMax = yValues.maxOrNull() ?: 1f
                        
                        drawScatterSeries(
                            data = data,
                            color = color,
                            xRange = Pair(xMin, xMax),
                            yRange = Pair(yMin, yMax)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = xLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = yLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun AreaChartCard(
    title: String,
    data: List<Pair<Float, Float>>,
    xLabel: String = "X Axis",
    yLabel: String = "Y Axis",
    color: Color = Orange,
    showGrid: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (showGrid) {
                        drawGrid(Color.Gray.copy(alpha = 0.2f))
                    }
                    
                    drawAxes(Color.Gray)
                    
                    if (data.isNotEmpty()) {
                        val xValues = data.map { it.first }
                        val yValues = data.map { it.second }
                        val xMin = xValues.minOrNull() ?: 0f
                        val xMax = xValues.maxOrNull() ?: 1f
                        val yMin = yValues.minOrNull() ?: 0f
                        val yMax = yValues.maxOrNull() ?: 1f
                        
                        drawAreaSeries(
                            data = data,
                            color = color,
                            xRange = Pair(xMin, xMax),
                            yRange = Pair(yMin, yMax)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = xLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = yLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun CandlestickChartCard(
    title: String,
    data: List<Candlestick>,
    xLabel: String = "Time",
    yLabel: String = "Price",
    upColor: Color = Green,
    downColor: Color = Red,
    showGrid: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (showGrid) {
                        drawGrid(Color.Gray.copy(alpha = 0.2f))
                    }
                    
                    drawAxes(Color.Gray)
                    
                    if (data.isNotEmpty()) {
                        drawCandlestickSeries(
                            data = data,
                            upColor = upColor,
                            downColor = downColor
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = xLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = yLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Chart Type Selection
@Composable
fun ChartTypeSelector(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    val chartTypes = listOf("Line", "Bar", "Area", "Scatter", "Pie", "Candlestick")
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(chartTypes) { type ->
            ChartTypeChip(
                type = type,
                selected = type == selectedType,
                onClick = { onTypeSelected(type) }
            )
        }
    }
}

@Composable
fun ChartTypeChip(
    type: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Blue.copy(alpha = 0.1f) else Color.Transparent
    val borderColor = if (selected) Blue else Color.Gray.copy(alpha = 0.3f)
    val textColor = if (selected) Blue else MaterialTheme.colorScheme.onSurface
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (type) {
                    "Line" -> Icons.Default.ShowChart
                    "Bar" -> Icons.Default.BarChart
                    "Area" -> Icons.Default.AreaChart
                    "Scatter" -> Icons.Default.BubbleChart
                    "Pie" -> Icons.Default.PieChart
                    "Candlestick" -> Icons.Default.CandlestickChart
                    else -> Icons.Default.ShowChart
                },
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
            
            Spacer(modifier = Modifier.width(4.dp))
            
            Text(
                text = type,
                fontSize = 14.sp,
                color = textColor
            )
        }
    }
}

// Utility Components
@Composable
fun MetricMiniChart(
    data: List<Float>,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .width(80.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawMetricMiniChart(data, color)
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    trend: String,
    data: List<Float> = List(10) { Random.nextFloat() * 100 },
    color: Color = Blue,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val (icon, trendColor) = when (trend.lowercase()) {
                            "increasing" -> Icons.Default.TrendingUp to Green
                            "decreasing" -> Icons.Default.TrendingDown to Red
                            else -> Icons.Default.TrendingFlat to Color.Gray
                        }
                        
                        Icon(
                            imageVector = icon,
                            contentDescription = trend,
                            tint = trendColor,
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        Text(
                            text = trend.capitalizeWords(),
                            style = MaterialTheme.typography.bodySmall,
                            color = trendColor
                        )
                    }
                }
                
                MetricMiniChart(
                    data = data,
                    color = color
                )
            }
        }
    }
}

// Helper functions
fun getChartTypeIcon(type: String): ImageVector {
    return when (type.lowercase()) {
        "line" -> Icons.Default.ShowChart
        "bar" -> Icons.Default.BarChart
        "area" -> Icons.Default.AreaChart
        "scatter" -> Icons.Default.BubbleChart
        "pie" -> Icons.Default.PieChart
        "candlestick" -> Icons.Default.CandlestickChart
        else -> Icons.Default.ShowChart
    }
}

/**
 * Extension function to capitalize words in a string
 */
fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}