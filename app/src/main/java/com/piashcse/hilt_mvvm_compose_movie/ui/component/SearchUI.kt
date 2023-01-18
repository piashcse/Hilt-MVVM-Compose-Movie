package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.SecondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState

@Composable
fun SearchUI(navController:NavController, searchData: MutableState<DataState<BaseModel>?>, itemClick:()->Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 350.dp) // define max height
            .padding(start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(color = DefaultBackgroundColor)
            .padding(top = 8.dp)

    ) {
        searchData.value?.let {
            if (it is DataState.Success<BaseModel>) {
                items(items = it.data.results, itemContent = { item ->
                    Row(modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable {
                            itemClick.invoke()
                            navController.navigate(
                                Screen.MovieDetail.route.plus(
                                    "/${item.id}"
                                )
                            )
                        }) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ApiURL.IMAGE_URL.plus(
                                    item.backdropPath
                                )
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(60.dp)
                                .width(40.dp)
                            // .clip(RoundedCornerShape(10.dp))
                        )
                        Column {
                            Text(
                                text = item.title,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 4.dp
                                ),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${stringResource(R.string.rating_search)}${item.voteAverage}",
                                color = SecondaryFontColor,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                })
            }
        }
    }
}