package com.example.wsaudiology.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wsaudiology.presentation.viewmodel.MoviesAndTVShowsListViewModel

@Composable
fun MoviesAndTVShowsDetails(
    navController: NavController,
    isNetworkAvailable: Boolean,
    viewModel: MoviesAndTVShowsListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = isNetworkAvailable.toString())
        navController.navigate(
            "movie_and_tv_show_details_screen/${"ABC"}/${"ABC"}"
        )
    }

}