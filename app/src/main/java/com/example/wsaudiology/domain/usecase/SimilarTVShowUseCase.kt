package com.example.wsaudiology.domain.usecase

import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.wsaudiology.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class SimilarTVShowUseCase @Inject constructor(
    private val moviesAndTVShowsRepositoryInterface: MoviesAndTVShowsRepositoryInterface
) {
    operator fun invoke(seriesId: String): Flow<Resource<MoviesAndTVShowsResponse>> {
        return moviesAndTVShowsRepositoryInterface.similarMovie(seriesId)
            .catch { throwable ->
                emit(Resource.Error(throwable))
            }
    }
}