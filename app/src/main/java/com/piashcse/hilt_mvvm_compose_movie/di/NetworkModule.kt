package com.piashcse.hilt_mvvm_compose_movie.di

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.paging.NowPlayingPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.paging.PopularPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.paging.TopRatedPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.paging.UpcomingPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseURL(): String {
        return ApiURL.BASE_URL
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofitClient(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideNowPlayingPagingDataSource(apiService: ApiService): NowPlayingPagingDataSource {
        return NowPlayingPagingDataSource(apiService)
    }

    @Provides
    fun providePopularPagingDataSource(apiService: ApiService): PopularPagingDataSource {
        return PopularPagingDataSource(apiService)
    }


    @Provides
    fun provideTopRatedPagingDataSource(apiService: ApiService): TopRatedPagingDataSource {
        return TopRatedPagingDataSource(apiService)
    }

    @Provides
    fun provideUpcomingPagingDataSource(apiService: ApiService): UpcomingPagingDataSource {
        return UpcomingPagingDataSource(apiService)
    }


}