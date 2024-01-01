package com.example.wsaudiology.data.repository

import com.example.wsaudiology.data.datasource.local.moviesandtvshows.MoviesAndTVShowsDao
import com.example.wsaudiology.data.datasource.remote.moviesandtvshows.MoviesAndTVShowAPI
import com.example.wsaudiology.domain.model.moviedetails.MovieDetailsResponse
import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.wsaudiology.domain.model.tvshowdetails.TVShowDetailsResponse
import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.wsaudiology.utils.Resource
import com.example.wsaudiology.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesAndTVShowsRepository @Inject constructor(
    private val moviesAndTVShowAPI: MoviesAndTVShowAPI,
    private val moviesAndTVShowsDao: MoviesAndTVShowsDao
) : MoviesAndTVShowsRepositoryInterface {

    private fun <T> fetchData(block: suspend () -> T): Flow<Resource<T>> {
        return flow {
            try {
                emit(Resource.Success(block()))
            } catch (throwable: Throwable) {
                emit(Resource.Error(throwable))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun moviesAndTVShows() = networkBoundResource(
        query = {
            moviesAndTVShowsDao.getMoviesAndTVShows()
        },
        fetch = {
            moviesAndTVShowAPI.moviesAndTVShows()
        },
        saveFetchResult = { moviesAndTVShowsResult ->
            moviesAndTVShowsDao.insertMoviesAndTVShows(moviesAndTVShowsResult.results)
        },
        shouldFetch = {
            true
        }
    )

    override fun searchTVShows(tvShowName: String): Flow<Resource<MoviesAndTVShowsResponse>> {
        return fetchData {
            moviesAndTVShowAPI.searchTVShows(tvShowName)
        }
    }

    override fun tvShowDetails(seriesId: String): Flow<Resource<TVShowDetailsResponse>> {
        return fetchData {
            moviesAndTVShowAPI.tvShowDetails(seriesId)
        }
    }

    override fun movieDetails(movieId: String): Flow<Resource<MovieDetailsResponse>> {
        return fetchData {
            moviesAndTVShowAPI.movieDetails(movieId)
        }
    }

    override fun similarTVShow(seriesId: String): Flow<Resource<MoviesAndTVShowsResponse>> {
        return fetchData {
            moviesAndTVShowAPI.similarTVShow(seriesId)
        }
    }

    override fun similarMovie(movieId: String): Flow<Resource<MoviesAndTVShowsResponse>> {
        return fetchData {
            moviesAndTVShowAPI.similarMovie(movieId)
        }
    }
}