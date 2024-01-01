package com.example.wsaudiology.domain.usecase

import com.example.wsaudiology.domain.model.tvshowdetails.TVShowDetailsResponse
import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.wsaudiology.utils.Resource
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