package com.example.wsaudiology.presentation.di

import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.wsaudiology.domain.usecase.MoviesAndTVShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @ViewModelScoped
    @Provides
    fun provideGetPopularMoviesUseCase(
        repository: MoviesAndTVShowsRepositoryInterface
    ): MoviesAndTVShowsUseCase {
        return MoviesAndTVShowsUseCase(repository)
    }
}