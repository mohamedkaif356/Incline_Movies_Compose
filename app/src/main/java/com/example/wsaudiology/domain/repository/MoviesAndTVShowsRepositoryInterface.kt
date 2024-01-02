package com.example.wsaudiology.domain.repository

import com.example.wsaudiology.domain.model.moviedetails.MovieDetailsResponse
import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.wsaudiology.domain.model.tvshowdetails.TVShowDetailsResponse
import com.example.wsaudiology.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesAndTVShowsRepositoryInterface {

    fun moviesAndTVShows(isNetworkAvailable: Boolean): Flow<Resource<List<MoviesAndTVShowsResult>>>

    fun searchTVShows(tvShowName: String): Flow<Resource<MoviesAndTVShowsResponse>>

    fun tvShowDetails(seriesId: String): Flow<Resource<TVShowDetailsResponse>>

    fun movieDetails(movieId: String): Flow<Resource<MovieDetailsResponse>>

    fun similarMovieOrTVShow(
        movieOrTVShow: String,
        movieOrSeriesId: String
    ): Flow<Resource<MoviesAndTVShowsResponse>>

}