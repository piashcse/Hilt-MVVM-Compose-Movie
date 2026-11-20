package com.piashcse.hilt_mvvm_compose_movie.di

import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.movie.LocalMovieRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.movie.LocalMovieRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries.LocalTvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries.LocalTvSeriesRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.artist.ArtistRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.artist.ArtistRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity.CelebrityRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity.CelebrityRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindTvSeriesRepository(impl: TvSeriesRepositoryImpl): TvSeriesRepository

    @Binds
    @Singleton
    abstract fun bindArtistRepository(impl: ArtistRepositoryImpl): ArtistRepository

    @Binds
    @Singleton
    abstract fun bindCelebrityRepository(impl: CelebrityRepositoryImpl): CelebrityRepository

    @Binds
    @Singleton
    abstract fun bindLocalMovieRepository(impl: LocalMovieRepositoryImpl): LocalMovieRepository

    @Binds
    @Singleton
    abstract fun bindLocalTvSeriesRepository(impl: LocalTvSeriesRepositoryImpl): LocalTvSeriesRepository
}
