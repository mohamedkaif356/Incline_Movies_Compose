package com.example.inclinemoviescompose.presentation.di

import com.example.inclinemoviescompose.data.datasource.local.moviesandtvshows.MoviesAndTVShowsDao
import com.example.inclinemoviescompose.data.datasource.remote.moviesandtvshows.MoviesAndTVShowAPI
import com.example.inclinemoviescompose.data.repository.MoviesAndTVShowsRepository
import com.example.inclinemoviescompose.domain.repository.MoviesAndTVShowsRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesAndTVShowsRepository(
        moviesAndTVShowAPI: MoviesAndTVShowAPI,
        moviesAndTVShowsDao: MoviesAndTVShowsDao
    ): MoviesAndTVShowsRepositoryInterface {
        return MoviesAndTVShowsRepository(
            moviesAndTVShowAPI,
            moviesAndTVShowsDao
        )
    }
}