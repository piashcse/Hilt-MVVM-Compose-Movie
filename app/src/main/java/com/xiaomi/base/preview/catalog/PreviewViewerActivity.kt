package com.xiaomi.base.preview.catalog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.theme.BaseTheme

/**
 * Activity for viewing individual preview content
 */
class PreviewViewerActivity : ComponentActivity() {
    companion object {
        private const val EXTRA_PREVIEW_ID = "preview_id"
        
        fun createIntent(context: Context, previewId: String): Intent {
            return Intent(context, PreviewViewerActivity::class.java).apply {
                putExtra(EXTRA_PREVIEW_ID, previewId)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val previewId = intent.getStringExtra(EXTRA_PREVIEW_ID)
        if (previewId == null) {
            finish()
            return
        }
        
        val previewItem = PreviewRegistry.getPreview(previewId)
        if (previewItem == null) {
            finish()
            return
        }
        
        setContent {
            BaseTheme {
                PreviewViewerScreen(
                    previewItem = previewItem,
                    onBackClick = { finish() }
                )
            }
        }
    }
}

/**
 * Screen for viewing individual preview content
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewViewerScreen(
    previewItem: PreviewItem,
    onBackClick: () -> Unit
) {
    var showFullScreen by remember { mutableStateOf(true) }
    
    if (showFullScreen) {
        // Full-screen demo mode
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            previewItem.content()
            
            // Floating controls
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(48.dp),
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Catalog"
                    )
                }
                
                FloatingActionButton(
                    onClick = { showFullScreen = false },
                    modifier = Modifier.size(48.dp),
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Show Info"
                    )
                }
            }
        }
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = previewItem.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${previewItem.difficulty.displayName} • ${previewItem.estimatedTime}",
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
                    IconButton(onClick = { 
                        showFullScreen = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "Full Screen Demo"
                        )
                    }
                    
                    IconButton(onClick = { 
                        // TODO: Add bookmark/favorite functionality
                    }) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark"
                        )
                    }
                    
                    IconButton(onClick = { 
                        // TODO: Add share functionality
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Preview Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = previewItem.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        
                        AssistChip(
                            onClick = { },
                            label = { Text(previewItem.category.displayName) },
                            leadingIcon = {
                                Icon(
                                    imageVector = previewItem.category.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                        
                        AssistChip(
                            onClick = { },
                            label = { Text(previewItem.difficulty.displayName) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }
                    
                    Text(
                        text = previewItem.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (previewItem.tags.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            previewItem.tags.take(4).forEach { tag ->
                                SuggestionChip(
                                    onClick = { },
                                    label = { 
                                        Text(
                                            text = tag,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                )
                            }
                            
                            if (previewItem.tags.size > 4) {
                                SuggestionChip(
                                    onClick = { },
                                    label = { 
                                        Text(
                                            text = "+${previewItem.tags.size - 4}",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
            
            // Full-Screen Preview Content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Render the actual preview content full-screen
                previewItem.content()
            }
        }
    }
} 