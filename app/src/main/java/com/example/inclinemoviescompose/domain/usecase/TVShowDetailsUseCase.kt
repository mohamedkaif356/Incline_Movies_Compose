package com.example.inclinemoviescompose.domain.usecase

import com.example.inclinemoviescompose.domain.model.tvshowdetails.TVShowDetailsResponse
import com.example.inclinemoviescompose.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.inclinemoviescompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class TVShowDetailsUseCase @Inject constructor(
    private val moviesAndTVShowsRepositoryInterface: MoviesAndTVShowsRepositoryInterface
) {
    operator fun invoke(seriesId: String): Flow<Resource<TVShowDetailsResponse>> {
        return moviesAndTVShowsRepositoryInterface.tvShowDetails(seriesId)
            .catch { throwable ->
                emit(Resource.Error(throwable))
            }
    }
}