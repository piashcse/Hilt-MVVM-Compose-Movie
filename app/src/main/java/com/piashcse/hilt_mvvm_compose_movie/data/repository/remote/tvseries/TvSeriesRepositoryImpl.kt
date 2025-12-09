package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.AiringTodayTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.OnTheAirTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.PopularTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.TopRatedTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvSeriesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TvSeriesRepository {
    override fun airingTodayTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>> =
        Pager(
            pagingSourceFactory = { AiringTodayTvSeriesPagingDataSource(apiService, genreId) },
            config = PagingConfig(pageSize = 20)
        ).flow

    override fun onTheAirTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>>  =
        Pager(
            pagingSourceFactory = { OnTheAirTvSeriesPagingDataSource(apiService, genreId) },
            config = PagingConfig(pageSize = 20)
        ).flow

    override fun popularTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>>  =
        Pager(
            pagingSourceFactory = { PopularTvSeriesPagingDataSource(apiService, genreId) },
            config = PagingConfig(pageSize = 20)
        ).flow

    override fun topRatedTvSeriesPagingDataSource(genreId: String?): Flow<PagingData<TvSeriesItem>>  =
        Pager(
            pagingSourceFactory = { TopRatedTvSeriesPagingDataSource(apiService, genreId) },
            config = PagingConfig(pageSize = 20)
        ).flow

    override suspend fun searchTvSeries(searchKey: String): Flow<DataState<SearchBaseModel>>  =
        safeApiCall { apiService.searchTvSeries(searchKey) }

    override suspend fun tvSeriesDetail(seriesId: Int): Flow<DataState<TvSeriesDetail>>  =
        safeApiCall { apiService.tvSeriesDetail(seriesId) }

    override suspend fun recommendedTvSeries(seriesId: Int): Flow<DataState<List<TvSeriesItem>>> =
        safeApiCall { apiService.recommendedTvSeries(seriesId).results }

    override suspend fun artistDetail(personId: Int): Flow<DataState<Artist>> =
        safeApiCall { apiService.tvSeriesCredit(personId) }
}