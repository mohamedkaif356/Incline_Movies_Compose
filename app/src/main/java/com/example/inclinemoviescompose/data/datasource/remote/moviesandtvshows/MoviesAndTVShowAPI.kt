package com.example.inclinemoviescompose.data.datasource.remote.moviesandtvshows

import com.example.inclinemoviescompose.domain.model.moviedetails.MovieDetailsResponse
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import com.example.inclinemoviescompose.domain.model.tvshowdetails.TVShowDetailsResponse
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

    @GET("{movie_or_tv_show}/{movie_or_tv_show_id}/similar")
    suspend fun similarMovieOrTVShow(
        @Path("movie_or_tv_show") seriesOrMovie: String,
        @Path("movie_or_tv_show_id") seriesOrMovieId: String
    ) : MoviesAndTVShowsResponse

}