package com.xiaomi.base.components.visualization.data

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.ScatterPlot
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random
import com.xiaomi.base.ui.theme.Orange
import com.xiaomi.base.ui.theme.Purple

// Enums
enum class ChartType {
    LINE_CHART,
    BAR_CHART,
    PIE_CHART,
    AREA_CHART,
    SCATTER_PLOT,
    HISTOGRAM,
    HEATMAP,
    RADAR_CHART,
    CANDLESTICK,
    BUBBLE_CHART
}

enum class DataAggregationType {
    SUM,
    AVERAGE,
    COUNT,
    MIN,
    MAX,
    MEDIAN,
    PERCENTILE
}

enum class TimeRange {
    HOUR,
    DAY,
    WEEK,
    MONTH,
    QUARTER,
    YEAR,
    CUSTOM
}

enum class InteractionType {
    ZOOM,
    PAN,
    SELECT,
    HOVER,
    DRILL_DOWN,
    FILTER
}

enum class AnimationType {
    FADE_IN,
    SLIDE_IN,
    SCALE_UP,
    DRAW_ON,
    BOUNCE,
    ELASTIC
}

// Data Classes
data class DataPoint(
    val x: Float,
    val y: Float,
    val label: String = "",
    val value: Double = y.toDouble(),
    val timestamp: Long = System.currentTimeMillis(),
    val metadata: Map<String, Any> = emptyMap()
)

data class DataSeries(
    val id: String,
    val name: String,
    val data: List<DataPoint>,
    val color: Color = Color.Blue,
    val strokeWidth: Float = 2f,
    val visible: Boolean = true,
    val style: SeriesStyle = SeriesStyle.SOLID
)

data class ChartConfig(
    val type: ChartType,
    val title: String = "",
    val xAxisLabel: String = "",
    val yAxisLabel: String = "",
    val showGrid: Boolean = true,
    val showLegend: Boolean = true,
    val showTooltip: Boolean = true,
    val enableInteraction: Boolean = true,
    val animationType: AnimationType = AnimationType.FADE_IN,
    val animationDuration: Int = 1000
)

data class ChartAxis(
    val min: Float,
    val max: Float,
    val step: Float,
    val label: String = "",
    val formatter: (Float) -> String = { it.toString() }
)

data class ChartLegend(
    val position: LegendPosition = LegendPosition.RIGHT,
    val visible: Boolean = true,
    val items: List<LegendItem> = emptyList()
)

data class LegendItem(
    val label: String,
    val color: Color,
    val visible: Boolean = true
)

data class TooltipData(
    val position: Offset,
    val content: String,
    val visible: Boolean = false
)

data class ChartInteraction(
    val type: InteractionType,
    val position: Offset,
    val data: Any? = null
)

data class DataVisualizationConfig(
    val enableRealTimeUpdates: Boolean = true,
    val updateInterval: Long = 1000,
    val maxDataPoints: Int = 1000,
    val enableExport: Boolean = true,
    val enableFiltering: Boolean = true,
    val enableDrillDown: Boolean = true,
    val defaultChartType: ChartType = ChartType.LINE_CHART
)

// Enums for styling
enum class SeriesStyle {
    SOLID,
    DASHED,
    DOTTED,
    DASH_DOT
}

enum class LegendPosition {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT
}

// Main Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataVisualizationComponent(
    modifier: Modifier = Modifier,
    config: DataVisualizationConfig = DataVisualizationConfig()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Charts", "Data", "Analytics", "Export", "Settings")
    
    // Sample data
    val dataSeries by remember { mutableStateOf<List<DataSeries>>(generateSampleDataSeries()) }
    val chartConfigs by remember { mutableStateOf<List<ChartConfig>>(generateSampleChartConfigs()) }
    var tooltipData by remember { mutableStateOf<TooltipData?>(null) }
    var selectedChartType by remember { mutableStateOf(ChartType.LINE_CHART) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        DataVisualizationHeader(config = config)
        
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
            0 -> ChartsTab(
                dataSeries = dataSeries,
                chartType = selectedChartType,
                onChartTypeChange = { selectedChartType = it },
                onTooltip = { tooltipData = it }
            )
            1 -> DataTab(dataSeries = dataSeries)
            2 -> AnalyticsTab(dataSeries = dataSeries)
            3 -> ExportTab(dataSeries = dataSeries)
            4 -> SettingsTab(config = config)
        }
        
        // Tooltip overlay
        tooltipData?.let { tooltip ->
            if (tooltip.visible) {
                TooltipComponent(data = tooltip)
            }
        }
    }
}

@Composable
fun DataVisualizationHeader(
    config: DataVisualizationConfig
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
                text = "Data Visualization",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Interactive charts and data analysis",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quick stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickStatCard(title = "Charts", value = "12", color = Color.Blue)
                QuickStatCard(title = "Data Points", value = "1.2K", color = Color.Green)
                QuickStatCard(title = "Real-time", value = if (config.enableRealTimeUpdates) "ON" else "OFF", color = Orange)
                QuickStatCard(title = "Export", value = "Ready", color = Purple)
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
fun ChartsTab(
    dataSeries: List<DataSeries>,
    chartType: ChartType,
    onChartTypeChange: (ChartType) -> Unit,
    onTooltip: (TooltipData?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Chart type selector
        ChartTypeSelector(
            selectedType = chartType,
            onTypeChange = onChartTypeChange
        )
        
        // Main chart
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (chartType) {
                    ChartType.LINE_CHART -> LineChart(
                        dataSeries = dataSeries,
                        modifier = Modifier.fillMaxSize(),
                        onTooltip = onTooltip
                    )
                    ChartType.BAR_CHART -> BarChart(
                        dataSeries = dataSeries,
                        modifier = Modifier.fillMaxSize(),
                        onTooltip = onTooltip
                    )
                    ChartType.PIE_CHART -> PieChart(
                        dataSeries = dataSeries,
                        modifier = Modifier.fillMaxSize(),
                        onTooltip = onTooltip
                    )
                    ChartType.AREA_CHART -> AreaChart(
                        dataSeries = dataSeries,
                        modifier = Modifier.fillMaxSize(),
                        onTooltip = onTooltip
                    )
                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Chart type not implemented yet")
                        }
                    }
                }
            }
        }
        
        // Chart legend
        if (dataSeries.isNotEmpty()) {
            ChartLegendComponent(dataSeries = dataSeries)
        }
    }
}

@Composable
fun DataTab(dataSeries: List<DataSeries>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dataSeries) { series ->
            DataSeriesCard(series = series)
        }
    }
}

@Composable
fun AnalyticsTab(dataSeries: List<DataSeries>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Data Analytics",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(dataSeries) { series ->
            AnalyticsCard(series = series)
        }
    }
}

@Composable
fun ExportTab(dataSeries: List<DataSeries>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Export Options",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Export as CSV */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Export as CSV")
                }
                
                Button(
                    onClick = { /* Export as JSON */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Export as JSON")
                }
                
                Button(
                    onClick = { /* Export as PNG */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Export as PNG")
                }
            }
        }
    }
}

@Composable
fun SettingsTab(config: DataVisualizationConfig) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            SettingsCard(
                title = "Real-time Updates",
                description = "Enable automatic data updates",
                enabled = config.enableRealTimeUpdates,
                onToggle = { /* Handle toggle */ }
            )
        }
        
        item {
            SettingsCard(
                title = "Export",
                description = "Enable data export functionality",
                enabled = config.enableExport,
                onToggle = { /* Handle toggle */ }
            )
        }
        
        item {
            SettingsCard(
                title = "Filtering",
                description = "Enable data filtering options",
                enabled = config.enableFiltering,
                onToggle = { /* Handle toggle */ }
            )
        }
    }
}

// Chart Components
@Composable
fun ChartTypeSelector(
    selectedType: ChartType,
    onTypeChange: (ChartType) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ChartType.values()) { type ->
            FilterChip(
                selected = selectedType == type,
                onClick = { onTypeChange(type) },
                label = {
                    Text(
                        text = type.name.replace("_", " "),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}

@Composable
fun LineChart(
    dataSeries: List<DataSeries>,
    modifier: Modifier = Modifier,
    onTooltip: (TooltipData?) -> Unit
) {
    Canvas(modifier = modifier) {
        if (dataSeries.isEmpty()) return@Canvas
        
        val chartArea = androidx.compose.ui.geometry.Rect(
            offset = Offset(40f, 20f),
            size = Size(size.width - 80f, size.height - 60f)
        )
        
        // Draw grid
        drawGrid(chartArea)
        
        // Draw data series
        dataSeries.forEach { series ->
            if (series.visible && series.data.isNotEmpty()) {
                drawLineSeries(series, chartArea)
            }
        }
    }
}

@Composable
fun BarChart(
    dataSeries: List<DataSeries>,
    modifier: Modifier = Modifier,
    onTooltip: (TooltipData?) -> Unit
) {
    Canvas(modifier = modifier) {
        if (dataSeries.isEmpty()) return@Canvas
        
        val chartArea = androidx.compose.ui.geometry.Rect(
            offset = Offset(40f, 20f),
            size = Size(size.width - 80f, size.height - 60f)
        )
        
        // Draw grid
        drawGrid(chartArea)
        
        // Draw bars
        dataSeries.forEach { series ->
            if (series.visible && series.data.isNotEmpty()) {
                drawBarSeries(series, chartArea)
            }
        }
    }
}

@Composable
fun PieChart(
    dataSeries: List<DataSeries>,
    modifier: Modifier = Modifier,
    onTooltip: (TooltipData?) -> Unit
) {
    Canvas(modifier = modifier) {
        if (dataSeries.isEmpty()) return@Canvas
        
        val center = Offset(size.width / 2, size.height / 2)
        val radius = minOf(size.width, size.height) / 3
        
        // Calculate total for percentages
        val total = dataSeries.flatMap { it.data }.sumOf { it.value }
        
        var startAngle = 0f
        dataSeries.forEach { series ->
            val seriesTotal = series.data.sumOf { it.value }
            val sweepAngle = (seriesTotal / total * 360).toFloat()
            
            drawArc(
                color = series.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            
            startAngle += sweepAngle
        }
    }
}

@Composable
fun AreaChart(
    dataSeries: List<DataSeries>,
    modifier: Modifier = Modifier,
    onTooltip: (TooltipData?) -> Unit
) {
    Canvas(modifier = modifier) {
        if (dataSeries.isEmpty()) return@Canvas
        
        val chartArea = androidx.compose.ui.geometry.Rect(
            offset = Offset(40f, 20f),
            size = Size(size.width - 80f, size.height - 60f)
        )
        
        // Draw grid
        drawGrid(chartArea)
        
        // Draw area series
        dataSeries.forEach { series ->
            if (series.visible && series.data.isNotEmpty()) {
                drawAreaSeries(series, chartArea)
            }
        }
    }
}

@Composable
fun ChartLegendComponent(dataSeries: List<DataSeries>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Legend",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dataSeries) { series ->
                    LegendItemComponent(
                        label = series.name,
                        color = series.color,
                        visible = series.visible
                    )
                }
            }
        }
    }
}

@Composable
fun LegendItemComponent(
    label: String,
    color: Color,
    visible: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color = if (visible) color else Color.Gray, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = if (visible) MaterialTheme.colorScheme.onSurface else Color.Gray
        )
    }
}

@Composable
fun TooltipComponent(
    data: TooltipData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .offset(x = data.position.x.dp, y = data.position.y.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = data.content,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

// Card Components
@Composable
fun DataSeriesCard(series: DataSeries) {
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
                    text = series.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(color = series.color, shape = CircleShape)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Data Points: ${series.data.size}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Visible: ${if (series.visible) "Yes" else "No"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AnalyticsCard(series: DataSeries) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = series.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (series.data.isNotEmpty()) {
                val values = series.data.map { it.value }
                val min = values.minOrNull() ?: 0.0
                val max = values.maxOrNull() ?: 0.0
                val avg = values.average()
                
                Text(
                    text = "Min: ${String.format("%.2f", min)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Max: ${String.format("%.2f", max)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Average: ${String.format("%.2f", avg)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun SettingsCard(
    title: String,
    description: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Switch(
                checked = enabled,
                onCheckedChange = onToggle
            )
        }
    }
}

// Drawing Helper Functions
fun DrawScope.drawGrid(chartArea: androidx.compose.ui.geometry.Rect) {
    val gridColor = Color.Gray.copy(alpha = 0.3f)
    val gridLines = 5
    
    // Vertical grid lines
    for (i in 0..gridLines) {
        val x = chartArea.left + (i * chartArea.width / gridLines)
        drawLine(
            color = gridColor,
            start = Offset(x, chartArea.top),
            end = Offset(x, chartArea.bottom),
            strokeWidth = 1.dp.toPx()
        )
    }
    
    // Horizontal grid lines
    for (i in 0..gridLines) {
        val y = chartArea.top + (i * chartArea.height / gridLines)
        drawLine(
            color = gridColor,
            start = Offset(chartArea.left, y),
            end = Offset(chartArea.right, y),
            strokeWidth = 1.dp.toPx()
        )
    }
}

fun DrawScope.drawLineSeries(series: DataSeries, chartArea: androidx.compose.ui.geometry.Rect) {
    if (series.data.size < 2) return
    
    val path = Path()
    val maxY = series.data.maxOf { it.y }
    val minY = series.data.minOf { it.y }
    val rangeY = maxY - minY
    
    series.data.forEachIndexed { index, point ->
        val x = chartArea.left + (index.toFloat() / (series.data.size - 1)) * chartArea.width
        val y = if (rangeY > 0) {
            chartArea.bottom - ((point.y - minY) / rangeY) * chartArea.height
        } else {
            chartArea.bottom - chartArea.height / 2
        }
        
        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    
    drawPath(
        path = path,
        color = series.color,
        style = Stroke(width = series.strokeWidth.dp.toPx())
    )
}

fun DrawScope.drawBarSeries(series: DataSeries, chartArea: androidx.compose.ui.geometry.Rect) {
    if (series.data.isEmpty()) return
    
    val maxY = series.data.maxOf { it.y }
    val barWidth = chartArea.width / series.data.size * 0.8f
    
    series.data.forEachIndexed { index, point ->
        val x = chartArea.left + (index + 0.1f) * (chartArea.width / series.data.size)
        val barHeight = (point.y / maxY) * chartArea.height
        val y = chartArea.bottom - barHeight
        
        drawRect(
            color = series.color,
            topLeft = Offset(x, y),
            size = Size(barWidth, barHeight)
        )
    }
}

fun DrawScope.drawAreaSeries(series: DataSeries, chartArea: androidx.compose.ui.geometry.Rect) {
    if (series.data.size < 2) return
    
    val path = Path()
    val maxY = series.data.maxOf { it.y }
    val minY = series.data.minOf { it.y }
    val rangeY = maxY - minY
    
    // Start from bottom left
    path.moveTo(chartArea.left, chartArea.bottom)
    
    // Draw the top line
    series.data.forEachIndexed { index, point ->
        val x = chartArea.left + (index.toFloat() / (series.data.size - 1)) * chartArea.width
        val y = if (rangeY > 0) {
            chartArea.bottom - ((point.y - minY) / rangeY) * chartArea.height
        } else {
            chartArea.bottom - chartArea.height / 2
        }
        
        path.lineTo(x, y)
    }
    
    // Close the path at bottom right
    path.lineTo(chartArea.right, chartArea.bottom)
    path.close()
    
    drawPath(
        path = path,
        color = series.color.copy(alpha = 0.3f)
    )
    
    // Draw the line on top
    drawLineSeries(series, chartArea)
}

// Sample Data Generators
fun generateSampleDataSeries(): List<DataSeries> {
    return listOf(
        DataSeries(
            id = "series1",
            name = "Sales",
            data = (0..10).map { i ->
                DataPoint(
                    x = i.toFloat(),
                    y = 50f + Random.nextFloat() * 50f,
                    label = "Point $i",
                    value = (50 + Random.nextDouble() * 50)
                )
            },
            color = Color.Blue
        ),
        DataSeries(
            id = "series2",
            name = "Revenue",
            data = (0..10).map { i ->
                DataPoint(
                    x = i.toFloat(),
                    y = 30f + Random.nextFloat() * 70f,
                    label = "Point $i",
                    value = (30 + Random.nextDouble() * 70)
                )
            },
            color = Color.Green
        ),
        DataSeries(
            id = "series3",
            name = "Profit",
            data = (0..10).map { i ->
                DataPoint(
                    x = i.toFloat(),
                    y = 20f + Random.nextFloat() * 40f,
                    label = "Point $i",
                    value = (20 + Random.nextDouble() * 40)
                )
            },
            color = Orange
        )
    )
}

fun generateSampleChartConfigs(): List<ChartConfig> {
    return listOf(
        ChartConfig(
            type = ChartType.LINE_CHART,
            title = "Sales Trends",
            xAxisLabel = "Time",
            yAxisLabel = "Amount",
            showGrid = true,
            showLegend = true,
            showTooltip = true
        ),
        ChartConfig(
            type = ChartType.BAR_CHART,
            title = "Monthly Revenue",
            xAxisLabel = "Month",
            yAxisLabel = "Revenue",
            showGrid = true,
            showLegend = true,
            showTooltip = true
        ),
        ChartConfig(
            type = ChartType.PIE_CHART,
            title = "Market Share",
            showLegend = true,
            showTooltip = true
        )
    )
}