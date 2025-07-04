package com.xiaomi.base.preview.catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.tooling.preview.Preview
import com.xiaomi.base.preview.base.PreviewContainer
import com.xiaomi.base.ui.theme.BaseTheme

/**
 * Main activity for browsing and discovering preview templates
 */
class PreviewCatalogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTheme {
                PreviewCatalogScreen(
                    onPreviewClick = { previewItem ->
                        // Launch demo as standalone screen
                        val intent = DemoActivity.createIntent(this@PreviewCatalogActivity, previewItem.id)
                        startActivity(intent)
                    },
                    onBackClick = {
                        finish()
                    }
                )
            }
        }
    }
}

/**
 * Main screen for the preview catalog
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewCatalogScreen(
    onPreviewClick: (PreviewItem) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<PreviewCategory?>(null) }
    var selectedDifficulty by remember { mutableStateOf<PreviewDifficulty?>(null) }
    var isGridView by remember { mutableStateOf(true) }
    var showFilters by remember { mutableStateOf(false) }
    
    val filteredItems = remember(searchQuery, selectedCategory, selectedDifficulty) {
        PreviewRegistry.findPreviews(
            category = selectedCategory,
            difficulty = selectedDifficulty,
            searchQuery = searchQuery.takeIf { it.isNotBlank() }
        )
    }
    
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    
    PreviewContainer {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Preview Catalog",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${filteredItems.size} templates available",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filters",
                            tint = if (selectedCategory != null || selectedDifficulty != null) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                LocalContentColor.current
                            }
                        )
                    }
                    
                    IconButton(onClick = { isGridView = !isGridView }) {
                        Icon(
                            imageVector = if (isGridView) Icons.Default.ViewList else Icons.Default.GridView,
                            contentDescription = if (isGridView) "List View" else "Grid View"
                        )
                    }
                }
            )
            
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search templates...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )
            
            // Filters
            if (showFilters) {
                FilterSection(
                    selectedCategory = selectedCategory,
                    onCategoryChange = { selectedCategory = it },
                    selectedDifficulty = selectedDifficulty,
                    onDifficultyChange = { selectedDifficulty = it },
                    onClearFilters = {
                        selectedCategory = null
                        selectedDifficulty = null
                    }
                )
            }
            
            // Content
            if (filteredItems.isEmpty()) {
                EmptyState(
                    hasFilters = searchQuery.isNotBlank() || selectedCategory != null || selectedDifficulty != null,
                    onClearFilters = {
                        searchQuery = ""
                        selectedCategory = null
                        selectedDifficulty = null
                    }
                )
            } else {
                if (isGridView) {
                    PreviewGrid(
                        items = filteredItems,
                        onItemClick = onPreviewClick,
                        columns = if (isTablet) 3 else 2
                    )
                } else {
                    PreviewList(
                        items = filteredItems,
                        onItemClick = onPreviewClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCatalogScreenPreview() {
    PreviewCatalogScreen(
        onPreviewClick = {},
        onBackClick = {}
    )
}


/**
 * Filter section for categories and difficulties
 */
@Composable
fun FilterSection(
    selectedCategory: PreviewCategory?,
    onCategoryChange: (PreviewCategory?) -> Unit,
    selectedDifficulty: PreviewDifficulty?,
    onDifficultyChange: (PreviewDifficulty?) -> Unit,
    onClearFilters: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                TextButton(onClick = onClearFilters) {
                    Text("Clear All")
                }
            }
            
            // Categories
            Text(
                text = "Categories",
                style = MaterialTheme.typography.labelMedium
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(PreviewCategory.values()) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = {
                            onCategoryChange(if (selectedCategory == category) null else category)
                        },
                        label = { Text(category.displayName) },
                        leadingIcon = {
                            Icon(
                                imageVector = category.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
            
            // Difficulties
            Text(
                text = "Difficulty",
                style = MaterialTheme.typography.labelMedium
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PreviewDifficulty.values().forEach { difficulty ->
                    FilterChip(
                        selected = selectedDifficulty == difficulty,
                        onClick = {
                            onDifficultyChange(if (selectedDifficulty == difficulty) null else difficulty)
                        },
                        label = { Text(difficulty.displayName) }
                    )
                }
            }
        }
    }
    
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
fun FilterSectionPreview() {
    FilterSection(
        selectedCategory = PreviewCategory.AI,
        onCategoryChange = {},
        selectedDifficulty = PreviewDifficulty.BEGINNER,
        onDifficultyChange = {},
        onClearFilters = {}
    )
}

/**
 * Grid view for preview items
 */
@Composable
fun PreviewGrid(
    items: List<PreviewItem>,
    onItemClick: (PreviewItem) -> Unit,
    columns: Int = 2
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            PreviewItemCard(
                item = item,
                onClick = { onItemClick(item) },
                isCompact = true
            )
        }
    }
}

@Preview
@Composable
fun PreviewGridPreview() {
    val items = listOf(
        PreviewItem(
            id = "1",
            title = "Preview 1",
            description = "Description 1",
            category = PreviewCategory.AI,
            icon = Icons.Default.AcUnit,
            content = {}
        ),
        PreviewItem(
            id = "2",
            title = "Preview 2",
            description = "Description 2",
            category = PreviewCategory.HEALTH,
            icon = Icons.Default.Favorite,
            content = {}
        )
    )
    PreviewGrid(
        items = items,
        onItemClick = {},
        columns = 2
    )
}


/**
 * List view for preview items
 */
@Composable
fun PreviewList(
    items: List<PreviewItem>,
    onItemClick: (PreviewItem) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            PreviewItemCard(
                item = item,
                onClick = { onItemClick(item) },
                isCompact = false
            )
        }
    }
}

@Preview
@Composable
fun PreviewListPreview() {
    val items = listOf(
        PreviewItem(
            id = "1",
            title = "Preview 1",
            description = "Description 1",
            category = PreviewCategory.AI,
            icon = Icons.Default.AcUnit,
            content = {}
        ),
        PreviewItem(
            id = "2",
            title = "Preview 2",
            description = "Description 2",
            category = PreviewCategory.HEALTH,
            icon = Icons.Default.Favorite,
            content = {}
        )
    )
    PreviewList(items = items, onItemClick = {})
}



/**
 * Empty state when no items match filters
 */
@Composable
fun EmptyState(
    hasFilters: Boolean,
    onClearFilters: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = if (hasFilters) Icons.Default.FilterList else Icons.Default.Widgets,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = if (hasFilters) "No templates match your filters" else "No templates available",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            if (hasFilters) {
                Button(onClick = onClearFilters) {
                    Text("Clear Filters")
                }
            }
        }
    }
}

@Preview
@Composable
fun EmptyStatePreview() {
    EmptyState(hasFilters = true, onClearFilters = {})
}
