package com.example.wsaudiology.domain.repository

import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.wsaudiology.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesAndTVShowsRepositoryInterface {

    fun getPopularMovies() : Flow<Resource<List<MoviesAndTVShowsResult>>>
}