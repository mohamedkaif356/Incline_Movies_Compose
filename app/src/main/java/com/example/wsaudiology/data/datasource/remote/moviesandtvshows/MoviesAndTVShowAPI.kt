package com.example.wsaudiology.data.datasource.remote.moviesandtvshows

import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResponse
import retrofit2.http.GET

interface MoviesAndTVShowAPI {
    @GET("trending/all/week")
    suspend fun moviesAndTVShows() : MoviesAndTVShowsResponse

}