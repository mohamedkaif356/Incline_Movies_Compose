package com.example.inclinemoviescompose.domain.usecase

import com.example.inclinemoviescompose.domain.model.moviedetails.MovieDetailsResponse
import com.example.inclinemoviescompose.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.inclinemoviescompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val moviesAndTVShowsRepositoryInterface: MoviesAndTVShowsRepositoryInterface
) {
    operator fun invoke(movieId: String): Flow<Resource<MovieDetailsResponse>> {
        return moviesAndTVShowsRepositoryInterface.movieDetails(movieId)
            .catch { throwable ->
                emit(Resource.Error(throwable))
            }
    }
}