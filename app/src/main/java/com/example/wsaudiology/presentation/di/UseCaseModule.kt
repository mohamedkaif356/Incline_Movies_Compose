package com.example.wsaudiology.presentation.di

import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.wsaudiology.domain.usecase.MovieDetailsUseCase
import com.example.wsaudiology.domain.usecase.MoviesAndTVShowsUseCase
import com.example.wsaudiology.domain.usecase.SimilarMovieOrTVShowUseCase
import com.example.wsaudiology.domain.usecase.TVShowDetailsUseCase
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
    fun provideMoviesAndTVShowsUseCase(
        repository: MoviesAndTVShowsRepositoryInterface
    ): MoviesAndTVShowsUseCase {
        return MoviesAndTVShowsUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideMovieDetailsUseCase(
        repository: MoviesAndTVShowsRepositoryInterface
    ): MovieDetailsUseCase {
        return MovieDetailsUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideTVShowDetailsUseCase(
        repository: MoviesAndTVShowsRepositoryInterface
    ): TVShowDetailsUseCase {
        return TVShowDetailsUseCase(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideSimilarMovieOrTVShowUseCase(
        repository: MoviesAndTVShowsRepositoryInterface
    ): SimilarMovieOrTVShowUseCase {
        return SimilarMovieOrTVShowUseCase(repository)
    }

}