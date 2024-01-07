package com.example.inclinemoviescompose.domain.usecase

import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.inclinemoviescompose.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.inclinemoviescompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class MoviesAndTVShowsUseCase @Inject constructor(
    private val moviesAndTVShowsRepositoryInterface: MoviesAndTVShowsRepositoryInterface
) {
    operator fun invoke(isNetworkAvailable: Boolean): Flow<Resource<List<MoviesAndTVShowsResult>>> {
        return moviesAndTVShowsRepositoryInterface.moviesAndTVShows(isNetworkAvailable)
            .catch { throwable ->
                emit(Resource.Error(throwable))
            }
    }

    operator fun invoke(tvShowName: String): Flow<Resource<MoviesAndTVShowsResponse>> {
        return moviesAndTVShowsRepositoryInterface.searchTVShows(tvShowName)
            .catch { throwable ->
                emit(Resource.Error(throwable))
            }
    }
}