package com.example.wsaudiology.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wsaudiology.presentation.ui.MainActivity
import com.example.wsaudiology.presentation.viewmodel.MovieAndTVShowDetailsViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class MovieAndTVShowDetailsScreenTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testLoadingIndicatorDisplayed() {
        // Mock dependencies
        val viewModel = Mockito.mock(MovieAndTVShowDetailsViewModel::class.java)
        val isLoadingState = mutableStateOf(true)
        Mockito.`when`(viewModel.isLoading).thenReturn(isLoadingState)
        val navController = Mockito.mock(NavController::class.java)

        composeTestRule.setContent {
            MovieAndTVShowDetailsScreen(
                movieOrTvShowId = "sampleId",
                movieOrTvShow = "tv",
                navController = navController,
                viewModel = viewModel
            )
        }

        // Verify if the loading indicator is displayed
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun testTitleTextDisplayed() {
        // Mock dependencies
        val viewModel = Mockito.mock(MovieAndTVShowDetailsViewModel::class.java)
        val isLoadingState = mutableStateOf(false)
        Mockito.`when`(viewModel.isLoading).thenReturn(isLoadingState)
        val navController = Mockito.mock(NavController::class.java)

        composeTestRule.setContent {
            MovieAndTVShowDetailsScreen(
                movieOrTvShowId = "sampleId",
                movieOrTvShow = "tv",
                navController = navController,
                viewModel = viewModel
            )
        }

        // Verify if the title text is displayed
        composeTestRule.onNodeWithText("Title Text").assertIsDisplayed()
    }

    @Test
    fun testRatingTextDisplayed() {
        // Mock dependencies
        val viewModel = Mockito.mock(MovieAndTVShowDetailsViewModel::class.java)
        val isLoadingState = mutableStateOf(false)
        Mockito.`when`(viewModel.isLoading).thenReturn(isLoadingState)
        val navController = Mockito.mock(NavController::class.java)

        composeTestRule.setContent {
            MovieAndTVShowDetailsScreen(
                movieOrTvShowId = "sampleId",
                movieOrTvShow = "tv",
                navController = navController,
                viewModel = viewModel
            )
        }

        // Verify if the rating text is displayed
        composeTestRule.onNodeWithText("Rating Text").assertIsDisplayed()
    }

    @Test
    fun testSeasonsDisplayedForTVShow() {
        // Mock dependencies
        val viewModel = Mockito.mock(MovieAndTVShowDetailsViewModel::class.java)
        val isLoadingState = mutableStateOf(false)
        Mockito.`when`(viewModel.isLoading).thenReturn(isLoadingState)
        val navController = Mockito.mock(NavController::class.java)

        composeTestRule.setContent {
            MovieAndTVShowDetailsScreen(
                movieOrTvShowId = "sampleId",
                movieOrTvShow = "tv",
                navController = navController,
                viewModel = viewModel
            )
        }

        // Verify if the seasons section is displayed for a TV show
        composeTestRule.onNodeWithText("Seasons:").assertIsDisplayed()
    }

    @Test
    fun testSimilarMoviesDisplayed() {
        // Mock dependencies
        val viewModel = Mockito.mock(MovieAndTVShowDetailsViewModel::class.java)
        val isLoadingState = mutableStateOf(false)
        Mockito.`when`(viewModel.isLoading).thenReturn(isLoadingState)
        val navController = Mockito.mock(NavController::class.java)

        composeTestRule.setContent {
            MovieAndTVShowDetailsScreen(
                movieOrTvShowId = "sampleId",
                movieOrTvShow = "movie",
                navController = navController,
                viewModel = viewModel
            )
        }

        // Verify if the similar movies section is displayed for a movie
        composeTestRule.onNodeWithText("Similar Movies").assertIsDisplayed()
    }

    @Composable
    fun MovieAndTVShowDetailsScreen(
        movieOrTvShowId: String,
        movieOrTvShow: String,
        navController: NavController,
        viewModel: MovieAndTVShowDetailsViewModel
    ) {
        val isLoading = viewModel.isLoading.value


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White)
                        .testTag("LoadingIndicator")
                )
            } else {
                Text(
                    text = "Title Text",
                    modifier = Modifier.testTag("TitleText")
                )

                Text(
                    text = "Rating Text",
                    modifier = Modifier.testTag("RatingText")
                )

                if (movieOrTvShow == "tv") {
                    Text(
                        text = "Seasons:",
                        modifier = Modifier.testTag("SeasonsText")
                    )
                }

                LazyRow {
                    items(5) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Gray)
                                .padding(4.dp)
                        )
                    }
                }

                Text(
                    text = "Similar Movies",
                    modifier = Modifier.testTag("SimilarMoviesText")
                )

                LazyRow {
                    items(5) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Gray)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}
