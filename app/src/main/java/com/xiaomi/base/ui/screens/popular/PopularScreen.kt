package com.xiaomi.base.ui.screens.popular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems

import com.xiaomi.base.R
import com.xiaomi.base.ui.component.ErrorView
import com.xiaomi.base.ui.component.ItemCard

/**
 * Composable function that displays the popular items screen.
 *
 * @param navController The navigation controller for handling navigation events.
 * @param viewModel The view model for this screen.
 */
@Composable
fun PopularScreen(
    navController: NavController,
    viewModel: PopularViewModel = hiltViewModel()
) {
    val popularItems = viewModel.popularItems.collectAsLazyPagingItems()
    
    Box(modifier = Modifier.fillMaxSize()) {
        when (popularItems.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is LoadState.Error -> {
                val error = popularItems.loadState.refresh as LoadState.Error
                ErrorView(
                    message = error.error.message ?: stringResource(R.string.error_unknown),
                    onRetry = { popularItems.retry() }
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.popular),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    
                    items(
                        count = popularItems.itemCount,
                        key = { index -> popularItems[index]?.id ?: index }
                    ) { index ->
                        popularItems[index]?.let { item ->
                            ItemCard(
                                item = item,
                                onClick = { viewModel.navigateToItemDetail(navController, item.id) }
                            )
                        }
                    }
                    
                    when (popularItems.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize().padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        is LoadState.Error -> {
                            val error = popularItems.loadState.append as LoadState.Error
                            item {
                                ErrorView(
                                    message = error.error.message ?: stringResource(R.string.error_unknown),
                                    onRetry = { popularItems.retry() }
                                )
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}