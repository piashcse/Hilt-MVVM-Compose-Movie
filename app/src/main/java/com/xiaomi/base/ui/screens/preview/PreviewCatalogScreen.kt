package com.xiaomi.base.ui.screens.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xiaomi.base.R
import com.xiaomi.base.preview.catalog.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewCatalogScreen(
    navController: NavController,
    modifier: Modifier = Modifier
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
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.preview_catalog),
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        onClick = { showFilters = !showFilters }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filters"
                        )
                    }
                    
                    IconButton(
                        onClick = { isGridView = !isGridView }
                    ) {
                        Icon(
                            imageVector = if (isGridView) Icons.Default.ViewList else Icons.Default.GridView,
                            contentDescription = if (isGridView) "List View" else "Grid View"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search previews...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { searchQuery = "" }
                        ) {
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
            
            // Filters Section
            if (showFilters) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Filters",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Category Filter
                        Text(
                            text = "Category",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            item {
                                FilterChip(
                                    onClick = { selectedCategory = null },
                                    label = { Text("All") },
                                    selected = selectedCategory == null
                                )
                            }
                            
                            items(PreviewCategory.values().toList()) { category ->
                                FilterChip(
                                    onClick = { 
                                        selectedCategory = if (selectedCategory == category) null else category 
                                    },
                                    label = { Text(category.displayName) },
                                    selected = selectedCategory == category
                                )
                            }
                        }
                        
                        // Difficulty Filter
                        Text(
                            text = "Difficulty",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            item {
                                FilterChip(
                                    onClick = { selectedDifficulty = null },
                                    label = { Text("All") },
                                    selected = selectedDifficulty == null
                                )
                            }
                            
                            items(PreviewDifficulty.values().toList()) { difficulty ->
                                FilterChip(
                                    onClick = { 
                                        selectedDifficulty = if (selectedDifficulty == difficulty) null else difficulty 
                                    },
                                    label = { Text(difficulty.displayName) },
                                    selected = selectedDifficulty == difficulty
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Results Count
            Text(
                text = "${filteredItems.size} previews found",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Preview List/Grid
            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No previews found",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Try adjusting your search or filters",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                if (isGridView) {
                    PreviewGrid(
                        items = filteredItems,
                        onItemClick = { previewItem ->
                            // Launch demo as standalone screen
                            val intent = com.xiaomi.base.preview.catalog.DemoActivity.createIntent(
                                navController.context, 
                                previewItem.id
                            )
                            navController.context.startActivity(intent)
                        },
                        columns = if (isTablet) 3 else 2
                    )
                } else {
                    PreviewList(
                        items = filteredItems,
                        onItemClick = { previewItem ->
                            // Launch demo as standalone screen
                            val intent = com.xiaomi.base.preview.catalog.DemoActivity.createIntent(
                                navController.context, 
                                previewItem.id
                            )
                            navController.context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PreviewList(
    items: List<PreviewItem>,
    onItemClick: (PreviewItem) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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

@Composable
fun PreviewGrid(
    items: List<PreviewItem>,
    onItemClick: (PreviewItem) -> Unit,
    columns: Int = 2
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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