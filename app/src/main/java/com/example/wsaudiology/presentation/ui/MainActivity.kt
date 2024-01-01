package com.example.wsaudiology.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wsaudiology.presentation.ui.screens.MovieAndTVShowDetailsScreen
import com.example.wsaudiology.presentation.ui.screens.MoviesAndTVShowsDetails
import com.example.wsaudiology.presentation.ui.theme.WSAudiologyTheme
import com.example.wsaudiology.presentation.viewmodel.NetworkStateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NetworkStateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.networkStatus.observe(this) { networkStatus ->
            setContent {
                WSAudiologyTheme {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "movies_and_tv_shows_list_screen"
                    ) {
                        composable("movies_and_tv_shows_list_screen") {
                            MoviesAndTVShowsDetails(
                                navController = navController,
                                networkStatus
                            )
                        }
                        composable(
                            "movie_and_tv_show_details_screen/{movieOrTVShowId}/{movieOrTVShow}",
                            arguments = listOf(
                                navArgument("movieOrTVShowId") {
                                    type = NavType.StringType
                                },
                                navArgument("movieOrTVShow") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val movieOrTvShowId = remember {
                                it.arguments?.getString("movieOrTVShowId")
                            }
                            val movieOrTvShow = remember {
                                it.arguments?.getString("movieOrTVShow")
                            }
                            MovieAndTVShowDetailsScreen(
                                movieOrTvShowId ?: "",
                                movieOrTvShow ?: "",
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}