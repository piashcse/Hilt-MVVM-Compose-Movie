package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.MainViewModel
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.Blue
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH

@Composable
fun SearchBar(isAppBarVisible: MutableState<Boolean>, viewModel: MainViewModel, isLoading: Boolean = false) {
    var text by remember { mutableStateOf("") }
    val selectedSearchType by remember { mutableIntStateOf(viewModel.uiState.value.selectedSearchType) }
    val focusRequester = remember { FocusRequester() }

    BackHandler(isAppBarVisible.value.not()) {
        isAppBarVisible.value = true
    }

    Column(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxWidth()
            .background(Color.White)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
                disabledLabelColor = Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = { newText ->
                text = newText
                when (selectedSearchType) {
                    MOVIE_SEARCH -> viewModel.searchMovies(newText)
                    TV_SERIES_SEARCH -> viewModel.searchTvSeries(newText)
                    CELEBRITIES_SEARCH -> viewModel.searchCelebrities(newText)
                }
            },
            singleLine = true,
            trailingIcon = {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else if (text.isNotBlank()) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "clear text",
                        modifier = Modifier.clickable { text = "" }
                    )
                } else {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "search"
                    )
                }
            }
        )
        
        SearchTypeSelector(
            selectedType = selectedSearchType,
            onTypeSelected = { type ->
                viewModel.setSelectedSearchType(type)
                // Search will automatically trigger via TextField onValueChange when text is not empty
            }
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}