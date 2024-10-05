package com.piashcse.hilt_mvvm_compose_movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.AiringTodayTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.OnTheAirTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.PopularTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.tv_series.TopRatedTvSeriesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvSeriesRepository @Inject constructor(
    private val apiService: ApiService
) : TvSeriesRepositoryInterFace {
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

    override suspend fun tvSeriesDetail(seriesId: Int): Flow<DataState<TvSeriesDetail>>  = flow {
        emit(DataState.Loading)
        try {
            val apiResponse = apiService.tvSeriesDetail(seriesId)
            emit(DataState.Success(apiResponse))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun recommendedTvSeries(seriesId: Int): Flow<DataState<List<TvSeriesItem>>> = flow {
        emit(DataState.Loading)
        try {
            val apiResponse = apiService.recommendedTvSeries(seriesId)
            emit(DataState.Success(apiResponse.results))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun artistDetail(personId: Int): Flow<DataState<Artist>> = flow {
        emit(DataState.Loading)
        try {
            val apiResponse = apiService.tvSeriesCredit(personId)
            emit(DataState.Success(apiResponse))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}