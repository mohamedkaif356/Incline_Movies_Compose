package com.example.wsaudiology.data.datasource.remote.moviesandtvshows

import com.example.wsaudiology.domain.model.moviedetails.MovieDetailsResponse
import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.wsaudiology.domain.model.tvshowdetails.TVShowDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAndTVShowAPI {
    @GET("trending/all/week")
    suspend fun moviesAndTVShows() : MoviesAndTVShowsResponse

    @GET("search/tv")
    suspend fun searchTVShows(
        @Query("query") tvShowName: String,
    ) : MoviesAndTVShowsResponse

    @GET("tv/{series_id}")
    suspend fun tvShowDetails(
        @Path("series_id") seriesId: String
    ) : TVShowDetailsResponse

    @GET("movie/{movie_id}")
    suspend fun movieDetails(
        @Path("movie_id") movieId: String
    ) : MovieDetailsResponse

    @GET("tv/{series_id}/similar")
    suspend fun similarTVShow(
        @Path("movie_id") seriesId: String
    ) : MoviesAndTVShowsResponse

    @GET("movie/{movie_id}/similar")
    suspend fun similarMovie(
        @Path("movie_id") movieId: String
    ) : MoviesAndTVShowsResponse

}