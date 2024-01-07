package com.example.inclinemoviescompose.domain.model.moviesandtvshows

import com.google.gson.annotations.SerializedName

data class MoviesAndTVShowsResponse(
    val page: Int? = 0,
    val results: List<MoviesAndTVShowsResult>,
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)