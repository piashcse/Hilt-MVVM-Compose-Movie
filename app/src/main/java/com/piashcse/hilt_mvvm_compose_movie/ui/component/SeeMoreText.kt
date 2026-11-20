package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstant

/**
 * Collapsible text with an explicit, accessible "See more"/"See less" action.
 */
@Composable
fun ExpandingText(
    text: String,
    modifier: Modifier = Modifier,
    visibleLines: Int = AppConstant.MINIMIZED_MAX_LINES,
) {
    var isExpanded by rememberSaveable(text) { mutableStateOf(false) }
    var hasOverflow by rememberSaveable(text) { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = if (isExpanded) Int.MAX_VALUE else visibleLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { layout ->
                if (!isExpanded) hasOverflow = layout.hasVisualOverflow
            },
        )
        if (hasOverflow || isExpanded) {
            TextButton(
                onClick = { isExpanded = !isExpanded },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = stringResource(
                        if (isExpanded) R.string.see_less else R.string.see_more
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
