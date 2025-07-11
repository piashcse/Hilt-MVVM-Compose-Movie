package com.xiaomi.base.preview.demos.universal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.model.ItemStatus
import com.xiaomi.base.preview.base.BasePreviewScreen
import com.xiaomi.base.ui.components.*
import java.util.Date

/**
 * Interactive playground for testing universal components with different configurations
 */
@Composable
fun ComponentPlayground() {
    var selectedComponent by remember { mutableStateOf(PlaygroundComponent.UNIVERSAL_CARD) }
    
    BasePreviewScreen(
        title = "Component Playground",
        subtitle = "Interactive testing ground for universal components"
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Component selector
            ComponentSelector(
                selectedComponent = selectedComponent,
                onComponentSelected = { selectedComponent = it }
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // Component playground
            when (selectedComponent) {
                PlaygroundComponent.UNIVERSAL_CARD -> UniversalCardPlayground()
                PlaygroundComponent.METRIC_CARD -> MetricCardPlayground()
                PlaygroundComponent.FORM_INPUT -> FormInputPlayground()
                PlaygroundComponent.PROGRESS_CARD -> ProgressCardPlayground()
                PlaygroundComponent.MEDIA_VIEWER -> MediaViewerPlayground()
                PlaygroundComponent.RATING_INPUT -> RatingInputPlayground()
            }
        }
    }
}

@Composable
private fun ComponentSelector(
    selectedComponent: PlaygroundComponent,
    onComponentSelected: (PlaygroundComponent) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(PlaygroundComponent.values()) { component ->
            FilterChip(
                onClick = { onComponentSelected(component) },
                label = { Text(component.displayName) },
                selected = selectedComponent == component,
                leadingIcon = {
                    Icon(
                        imageVector = component.icon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
        }
    }
}

@Composable
private fun UniversalCardPlayground() {
    var displayMode by remember { mutableStateOf(CardDisplayMode.STANDARD) }
    var isFavorite by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(4.5f) }
    var status by remember { mutableStateOf(ItemStatus.ACTIVE) }
    
    val sampleItem = Item(
        id = 1,
        title = "Playground Test Item",
        description = "This is a configurable test item for the playground. You can modify its properties using the controls below.",
        score = score,
        status = status,
        tags = listOf("playground", "test", "configurable"),
        isFavorite = isFavorite,
        createdDate = Date(),
        metadata = mapOf("source" to "playground")
    )
    
    PlaygroundLayout(
        title = "Universal Card",
        component = {
            UniversalCard(
                item = sampleItem,
                displayMode = displayMode,
                onFavoriteClick = { isFavorite = !isFavorite }
            )
        },
        controls = {
            PlaygroundControls {
                // Display Mode Control
                Text("Display Mode", style = MaterialTheme.typography.titleSmall)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(CardDisplayMode.values()) { mode ->
                        FilterChip(
                            onClick = { displayMode = mode },
                            label = { Text(mode.name) },
                            selected = displayMode == mode
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Score Control
                Text("Score: ${score}", style = MaterialTheme.typography.titleSmall)
                Slider(
                    value = score,
                    onValueChange = { score = it },
                    valueRange = 0f..10f,
                    steps = 19
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Status Control
                Text("Status", style = MaterialTheme.typography.titleSmall)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ItemStatus.values()) { itemStatus ->
                        FilterChip(
                            onClick = { status = itemStatus },
                            label = { Text(itemStatus.name) },
                            selected = status == itemStatus
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Favorite Toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = isFavorite,
                        onCheckedChange = { isFavorite = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Is Favorite")
                }
            }
        }
    )
}

@Composable
private fun MetricCardPlayground() {
    var title by remember { mutableStateOf("Sample Metric") }
    var value by remember { mutableStateOf("1,234") }
    var unit by remember { mutableStateOf("units") }
    var trend by remember { mutableStateOf<TrendDirection?>(TrendDirection.UP) }
    
    PlaygroundLayout(
        title = "Metric Card",
        component = {
            MetricCard(
                title = title,
                value = value,
                unit = unit.takeIf { it.isNotBlank() },
                trend = trend
            )
        },
        controls = {
            PlaygroundControls {
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Title",
                    value = title,
                    onValueChange = { title = it }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Value",
                    value = value,
                    onValueChange = { value = it }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Unit (optional)",
                    value = unit,
                    onValueChange = { unit = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Trend", style = MaterialTheme.typography.titleSmall)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            onClick = { trend = null },
                            label = { Text("None") },
                            selected = trend == null
                        )
                    }
                    items(TrendDirection.values()) { trendDirection ->
                        FilterChip(
                            onClick = { trend = trendDirection },
                            label = { Text(trendDirection.name) },
                            selected = trend == trendDirection
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun FormInputPlayground() {
    var inputType by remember { mutableStateOf(InputType.TEXT) }
    var label by remember { mutableStateOf("Sample Input") }
    var value by remember { mutableStateOf("") }
    var placeholder by remember { mutableStateOf("Enter text here...") }
    var isRequired by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(true) }
    
    PlaygroundLayout(
        title = "Form Input",
        component = {
            UniversalFormInput(
                inputType = inputType,
                label = label,
                value = value,
                onValueChange = { value = it },
                placeholder = placeholder.takeIf { it.isNotBlank() },
                isRequired = isRequired,
                enabled = enabled,
                leadingIcon = Icons.Default.Edit
            )
        },
        controls = {
            PlaygroundControls {
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Label",
                    value = label,
                    onValueChange = { label = it }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Placeholder",
                    value = placeholder,
                    onValueChange = { placeholder = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Input Type", style = MaterialTheme.typography.titleSmall)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(InputType.values()) { type ->
                        FilterChip(
                            onClick = { inputType = type },
                            label = { Text(type.name) },
                            selected = inputType == type
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = isRequired,
                        onCheckedChange = { isRequired = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Required")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = enabled,
                        onCheckedChange = { enabled = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Enabled")
                }
            }
        }
    )
}

@Composable
private fun ProgressCardPlayground() {
    var title by remember { mutableStateOf("Progress Title") }
    var currentValue by remember { mutableStateOf(75f) }
    var targetValue by remember { mutableStateOf(100f) }
    var unit by remember { mutableStateOf("points") }
    var showPercentage by remember { mutableStateOf(true) }
    
    PlaygroundLayout(
        title = "Progress Card",
        component = {
            GenericProgressCard(
                title = title,
                currentValue = currentValue,
                targetValue = targetValue,
                unit = unit.takeIf { it.isNotBlank() },
                showPercentage = showPercentage,
                progressColor = Color(0xFF4CAF50)
            )
        },
        controls = {
            PlaygroundControls {
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Title",
                    value = title,
                    onValueChange = { title = it }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Unit",
                    value = unit,
                    onValueChange = { unit = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Current Value: ${currentValue.toInt()}", style = MaterialTheme.typography.titleSmall)
                Slider(
                    value = currentValue,
                    onValueChange = { currentValue = it },
                    valueRange = 0f..200f
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text("Target Value: ${targetValue.toInt()}", style = MaterialTheme.typography.titleSmall)
                Slider(
                    value = targetValue,
                    onValueChange = { targetValue = it },
                    valueRange = 50f..200f
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = showPercentage,
                        onCheckedChange = { showPercentage = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show Percentage")
                }
            }
        }
    )
}

@Composable
private fun MediaViewerPlayground() {
    var mediaType by remember { mutableStateOf(MediaType.IMAGE) }
    var hasEditButton by remember { mutableStateOf(true) }
    
    PlaygroundLayout(
        title = "Media Viewer",
        component = {
            MediaViewer(
                mediaUrl = "sample_media",
                mediaType = mediaType,
                onEdit = if (hasEditButton) { {} } else null
            )
        },
        controls = {
            PlaygroundControls {
                Text("Media Type", style = MaterialTheme.typography.titleSmall)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(MediaType.values()) { type ->
                        FilterChip(
                            onClick = { mediaType = type },
                            label = { Text(type.name) },
                            selected = mediaType == type
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = hasEditButton,
                        onCheckedChange = { hasEditButton = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show Edit Button")
                }
            }
        }
    )
}

@Composable
private fun RatingInputPlayground() {
    var rating by remember { mutableStateOf(3f) }
    var maxRating by remember { mutableStateOf(5) }
    var showRatingText by remember { mutableStateOf(true) }
    var label by remember { mutableStateOf("Rate this item") }
    
    PlaygroundLayout(
        title = "Rating Input",
        component = {
            UniversalRatingInput(
                rating = rating,
                onRatingChange = { rating = it },
                maxRating = maxRating,
                label = label.takeIf { it.isNotBlank() },
                showRatingText = showRatingText
            )
        },
        controls = {
            PlaygroundControls {
                UniversalFormInput(
                    inputType = InputType.TEXT,
                    label = "Label",
                    value = label,
                    onValueChange = { label = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Max Rating: $maxRating", style = MaterialTheme.typography.titleSmall)
                Slider(
                    value = maxRating.toFloat(),
                    onValueChange = { 
                        maxRating = it.toInt()
                        rating = rating.coerceAtMost(maxRating.toFloat())
                    },
                    valueRange = 3f..10f,
                    steps = 6
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = showRatingText,
                        onCheckedChange = { showRatingText = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show Rating Text")
                }
            }
        }
    )
}

@Composable
private fun PlaygroundLayout(
    title: String,
    component: @Composable () -> Unit,
    controls: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Component Preview
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Preview",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                component()
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Controls
        controls()
    }
}

@Composable
private fun PlaygroundControls(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Controls",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            content()
        }
    }
}

// ===== Enums and Data Classes =====

enum class PlaygroundComponent(
    val displayName: String,
    val icon: ImageVector
) {
    UNIVERSAL_CARD("Universal Card", Icons.Default.GridView),
    METRIC_CARD("Metric Card", Icons.Default.Analytics),
    FORM_INPUT("Form Input", Icons.Default.EditNote),
    PROGRESS_CARD("Progress Card", Icons.Default.TrendingUp),
    MEDIA_VIEWER("Media Viewer", Icons.Default.Image),
    RATING_INPUT("Rating Input", Icons.Default.Star)
}

// ===== Preview =====

@Preview(showBackground = true)
@Composable
fun ComponentPlaygroundPreview() {
    MaterialTheme {
        ComponentPlayground()
    }
} 