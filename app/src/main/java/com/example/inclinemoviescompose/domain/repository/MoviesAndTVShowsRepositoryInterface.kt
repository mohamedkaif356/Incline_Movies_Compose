package com.example.inclinemoviescompose.domain.repository

import com.example.inclinemoviescompose.domain.model.moviedetails.MovieDetailsResponse
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.inclinemoviescompose.domain.model.tvshowdetails.TVShowDetailsResponse
import com.example.inclinemoviescompose.utils.Resource
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