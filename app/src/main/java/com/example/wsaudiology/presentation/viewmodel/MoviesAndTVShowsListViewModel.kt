package com.example.wsaudiology.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wsaudiology.domain.usecase.MoviesAndTVShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesAndTVShowsListViewModel @Inject constructor(
    private val moviesAndTVShowsUseCase: MoviesAndTVShowsUseCase
) : ViewModel() {

}