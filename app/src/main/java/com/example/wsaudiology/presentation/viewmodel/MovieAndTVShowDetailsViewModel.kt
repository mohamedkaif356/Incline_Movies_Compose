package com.example.wsaudiology.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wsaudiology.domain.usecase.MovieDetailsUseCase
import com.example.wsaudiology.domain.usecase.TVShowDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieAndTVShowDetailsViewModel @Inject constructor(
    movieDetailsUseCase: MovieDetailsUseCase,
    tvShowDetailsUseCase: TVShowDetailsUseCase
) : ViewModel() {
}