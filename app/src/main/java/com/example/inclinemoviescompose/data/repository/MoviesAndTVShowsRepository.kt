package com.example.inclinemoviescompose.data.repository

import com.example.inclinemoviescompose.data.datasource.local.moviesandtvshows.MoviesAndTVShowsDao
import com.example.inclinemoviescompose.data.datasource.remote.moviesandtvshows.MoviesAndTVShowAPI
import com.example.inclinemoviescompose.domain.model.moviedetails.MovieDetailsResponse
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.inclinemoviescompose.domain.model.tvshowdetails.TVShowDetailsResponse
import com.example.inclinemoviescompose.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.inclinemoviescompose.utils.Resource
import com.example.inclinemoviescompose.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

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

    override fun moviesAndTVShows(isNetworkAvailable: Boolean) = networkBoundResource(
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
            isNetworkAvailable
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

    override fun similarMovieOrTVShow(
        movieOrTVShow: String,
        movieOrSeriesId: String
    ): Flow<Resource<MoviesAndTVShowsResponse>> {
        return fetchData {
            moviesAndTVShowAPI.similarMovieOrTVShow(movieOrTVShow, movieOrSeriesId)
        }
    }
}