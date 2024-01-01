package com.example.wsaudiology.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wsaudiology.presentation.viewmodel.MovieAndTVShowDetailsViewModel

@Composable
fun MovieAndTVShowDetailsScreen(
    movieOrTvShowId: String,
    movieOrTvShow: String,
    navController: NavController,
    viewModel: MovieAndTVShowDetailsViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = movieOrTvShow + movieOrTvShowId)
    }

}