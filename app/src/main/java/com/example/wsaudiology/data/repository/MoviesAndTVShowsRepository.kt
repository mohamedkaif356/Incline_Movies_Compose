package com.example.wsaudiology.data.repository

import com.example.wsaudiology.data.datasource.local.moviesandtvshows.MoviesAndTVShowsDao
import com.example.wsaudiology.data.datasource.remote.moviesandtvshows.MoviesAndTVShowAPI
import com.example.wsaudiology.domain.repository.MoviesAndTVShowsRepositoryInterface
import com.example.wsaudiology.utils.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesAndTVShowsRepository @Inject constructor(
    private val moviesAndTVShowAPI: MoviesAndTVShowAPI,
    private val moviesAndTVShowsDao: MoviesAndTVShowsDao
) : MoviesAndTVShowsRepositoryInterface {
    override fun getPopularMovies() = networkBoundResource(
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


}