package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries

import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.pagedData
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvSeriesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : TvSeriesRepository {

    override fun airingTodayTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>> =
        pagedData(genreId) { page, genre -> apiService.airingTodayTvSeries(page, genre) }

    override fun onTheAirTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>> =
        pagedData(genreId) { page, genre -> apiService.onTheAirTvSeries(page, genre) }

    override fun popularTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>> =
        pagedData(genreId) { page, genre -> apiService.popularTvSeries(page, genre) }

    override fun topRatedTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>> =
        pagedData(genreId) { page, genre -> apiService.topRatedTvSeries(page, genre) }

    override suspend fun searchTvSeries(searchKey: String): Flow<DataState<SearchBaseModel>> =
        safeApiCall { apiService.searchTvSeries(searchKey) }

    override suspend fun tvSeriesDetail(seriesId: Int): Flow<DataState<TvSeriesDetail>> =
        safeApiCall { apiService.tvSeriesDetail(seriesId) }

    override suspend fun recommendedTvSeries(seriesId: Int): Flow<DataState<List<TvSeriesItem>>> =
        safeApiCall { apiService.recommendedTvSeries(seriesId).results }

    override suspend fun tvSeriesCredit(seriesId: Int): Flow<DataState<Artist>> =
        safeApiCall { apiService.tvSeriesCredit(seriesId) }
}
