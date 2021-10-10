package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMovieList()

    }

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val movies = viewModel.movies.value

                HiltMVVMComposeMovieTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        DefaultPreview(movies?.results, loading)
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun DefaultPreview(movies: List<MovieItem>?, loading: Boolean) {
    HiltMVVMComposeMovieTheme {
        CircularIndeterminateProgressBar(isDisplayed = loading, 0.3f)
        movies?.let {
            MovieList(it)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MovieList(movies: List<MovieItem>) {
    LazyVerticalGrid(cells =  GridCells.Fixed(2), content ={
        items(items = movies) { item ->
            MovieItemView(item)
        }
    })
}

@Composable
fun MovieItemView(item: MovieItem) {
    Column(modifier = Modifier.padding(10.dp)) {
        Image(
            painter = rememberImagePainter(AppConstants.IMAGE_URL.plus(item.posterPath)),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(250.dp).clip(RoundedCornerShape(10.dp))
        )
    }
}

