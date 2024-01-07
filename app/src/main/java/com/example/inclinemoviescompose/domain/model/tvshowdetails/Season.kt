package com.example.inclinemoviescompose.domain.model.tvshowdetails

data class Season(
    val air_date: String? = "",
    val episode_count: Int? = 0,
    val id: Int? = 0,
    val name: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val season_number: Int? = 0,
    val vote_average: Double? = 0.0
)