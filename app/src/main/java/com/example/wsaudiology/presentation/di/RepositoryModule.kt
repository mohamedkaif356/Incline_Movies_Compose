package com.example.wsaudiology.presentation.di

import com.example.wsaudiology.data.datasource.local.moviesandtvshows.MoviesAndTVShowsDao
import com.example.wsaudiology.data.datasource.remote.moviesandtvshows.MoviesAndTVShowAPI
import com.example.wsaudiology.data.repository.MoviesAndTVShowsRepository
import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
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