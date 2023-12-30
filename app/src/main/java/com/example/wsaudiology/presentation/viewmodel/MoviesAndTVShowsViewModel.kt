package com.example.wsaudiology.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.wsaudiology.domain.usecase.MoviesAndTVShowsUseCase
import com.example.wsaudiology.utils.NetworkStatusLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesAndTVShowsViewModel @Inject constructor(
    moviesAndTVShowsUseCase: MoviesAndTVShowsUseCase,
    private val networkStatusLiveData: NetworkStatusLiveData
) : ViewModel() {

    val moviesAndTVShows = moviesAndTVShowsUseCase.invoke().asLiveData()


}