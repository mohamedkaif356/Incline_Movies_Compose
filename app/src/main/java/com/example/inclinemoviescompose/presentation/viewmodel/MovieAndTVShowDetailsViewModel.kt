package com.example.inclinemoviescompose.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inclinemoviescompose.domain.model.moviedetails.MovieDetailsResponse
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.inclinemoviescompose.domain.model.tvshowdetails.TVShowDetailsResponse
import com.example.inclinemoviescompose.domain.usecase.MovieDetailsUseCase
import com.example.inclinemoviescompose.domain.usecase.SimilarMovieOrTVShowUseCase
import com.example.inclinemoviescompose.domain.usecase.TVShowDetailsUseCase
import com.example.inclinemoviescompose.utils.NetworkStatusLiveData
import com.example.inclinemoviescompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieAndTVShowDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val tvShowDetailsUseCase: TVShowDetailsUseCase,
    private val similarMovieOrTVShowUseCase: SimilarMovieOrTVShowUseCase,
    context: Context
) : ViewModel() {

    private val _networkStatusLiveData = NetworkStatusLiveData(context)

    val networkStatusLiveData: LiveData<Boolean>
        get() = _networkStatusLiveData

    val similarMoviesAndTVShowList = mutableStateOf<List<MoviesAndTVShowsResult>>(listOf())
    val movieDetails = mutableStateOf(MovieDetailsResponse())
    val tvShowDetails = mutableStateOf(TVShowDetailsResponse())
    var loadError = mutableStateOf("")
    var isLoading: MutableState<Boolean> = mutableStateOf(false)

    fun movieOrTVShowDetails(movieOrTVShow: String, movieOrTVShowId: String) =
        viewModelScope.launch {
            isLoading.value = true
            if (movieOrTVShow == "tv") {
                tvShowDetailsUseCase.invoke(movieOrTVShowId).collect { details ->
                    when (details) {
                        is Resource.Success -> {
                            isLoading.value = false
                            if (details.data != null) {
                                tvShowDetails.value = details.data
                            } else {
                                loadError.value = "No Data Available"
                            }
                        }

                        is Resource.Error -> {
                            isLoading.value = false
                            loadError.value = details.error?.message ?: "Unknown error"
                        }

                        else -> {}
                    }
                }
            } else {
                movieDetailsUseCase.invoke(movieOrTVShowId).collect { details ->
                    when (details) {
                        is Resource.Success -> {
                            isLoading.value = false
                            if (details.data != null) {
                                movieDetails.value = details.data
                            } else {
                                loadError.value = "No Data Available"
                            }
                        }

                        is Resource.Error -> {
                            isLoading.value = false
                            loadError.value = details.error?.message ?: "Unknown error"
                        }

                        else -> {}
                    }
                }
            }
            similarMovieOrTVShowUseCase.invoke(movieOrTVShow, movieOrTVShowId).collect { similar ->
                when (similar) {
                    is Resource.Success -> {
                        isLoading.value = false
                        if (similar.data?.results != null) {
                            similarMoviesAndTVShowList.value = similar.data.results
                        } else {
                            loadError.value = "No Data Available"
                        }
                    }

                    is Resource.Error -> {
                        isLoading.value = false
                        loadError.value = similar.error?.message ?: "Unknown error"
                    }

                    else -> {}
                }
            }
        }
}