package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ConfirmDialog
import com.piashcse.hilt_mvvm_compose_movie.ui.component.EmptyState
import com.piashcse.hilt_mvvm_compose_movie.ui.component.PosterImage

/**
 * Shared favorites grid with an empty state and a single confirmation dialog
 * for removals.
 */
@Composable
fun <T : Any> FavoritesScreen(
    items: List<T>,
    key: (T) -> Any,
    title: (T) -> String,
    imagePath: (T) -> String?,
    emptyText: String,
    onClick: (T) -> Unit,
    onDelete: (T) -> Unit,
) {
    var pendingDelete by remember { mutableStateOf<T?>(null) }

    if (items.isEmpty()) {
        EmptyState(message = emptyText)
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items, key = key) { item ->
            val itemTitle = title(item)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onClick(item) }
                    .padding(bottom = 4.dp),
            ) {
                Box {
                    PosterImage(
                        path = imagePath(item),
                        contentDescription = stringResource(
                            R.string.image_content_description,
                            itemTitle,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f),
                    )
                    IconButton(
                        onClick = { pendingDelete = item },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(
                                R.string.delete_content_description,
                                itemTitle,
                            ),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                Text(
                    text = itemTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 6.dp),
                )
            }
        }
    }

    pendingDelete?.let { item ->
        ConfirmDialog(
            title = stringResource(R.string.remove_from_favorite),
            description = stringResource(R.string.do_you_wanna_remove_this_from_favorite),
            onConfirm = {
                onDelete(item)
                pendingDelete = null
            },
            onDismiss = { pendingDelete = null },
        )
    }
}
