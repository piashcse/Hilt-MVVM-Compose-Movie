package com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.popular

import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity.CelebrityRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularCelebritiesViewModel @Inject constructor(
    repository: CelebrityRepository,
) : MediaListViewModel<Celebrity>({ repository.popularCelebrities() })
