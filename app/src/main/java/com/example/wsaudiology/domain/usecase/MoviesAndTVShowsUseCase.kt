package com.example.wsaudiology.domain.usecase

import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.wsaudiology.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class MoviesAndTVShowsUseCase @Inject constructor(
    private val moviesAndTVShowsRepositoryInterface: MoviesAndTVShowsRepositoryInterface
) {
    operator fun invoke(): Flow<Resource<List<MoviesAndTVShowsResult>>> {
        return moviesAndTVShowsRepositoryInterface.getPopularMovies()
            .catch { throwable ->
                emit(Resource.Error(throwable))
            }
    }
}