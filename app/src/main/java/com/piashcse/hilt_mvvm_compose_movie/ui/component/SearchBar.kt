package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.MainViewModel
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.Blue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@ExperimentalCoroutinesApi
@Composable
fun SearchBar(isAppBarVisible:MutableState<Boolean>, viewModel: MainViewModel){
    var text by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    BackHandler(isAppBarVisible.value.not()) {
        isAppBarVisible.value = true
    }
    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                disabledLabelColor = Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                text = it
                viewModel.searchApi(it)
            },
            //shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                if (text.trim().isNotEmpty()) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "clear text",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .offset(x = 10.dp)
                            .clickable {
                                text = ""
                            }
                    )
                } else {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .offset(x = 10.dp)
                            .clickable {

                            }
                    )
                }
            }
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

}