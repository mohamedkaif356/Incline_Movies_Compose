package com.example.wsaudiology.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wsaudiology.domain.Constants
import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.wsaudiology.domain.model.tvshowdetails.Season
import com.example.wsaudiology.presentation.viewmodel.MovieAndTVShowDetailsViewModel

@Composable
fun MovieAndTVShowDetailsScreen(
    movieOrTvShowId: String,
    movieOrTvShow: String,
    navController: NavController,
    viewModel: MovieAndTVShowDetailsViewModel = hiltViewModel()
) {
    val movieDetails by remember { viewModel.movieDetails }
    val tvShowDetails by remember { viewModel.tvShowDetails }
    val similarMoviesAndTVShows by remember { viewModel.similarMoviesAndTVShowList }
    val isLoading by remember { viewModel.isLoading }

    val image = if (movieOrTvShow == "tv") {
        tvShowDetails.poster_path
    } else {
        movieDetails.poster_path
    }
    val title = if (movieOrTvShow == "tv") {
        tvShowDetails.original_name
    } else {
        movieDetails.original_title
    }
    val voteAverage = if (movieOrTvShow == "tv") {
        tvShowDetails.vote_average
    } else {
        movieDetails.vote_average
    }
    val voteCount = if (movieOrTvShow == "tv") {
        tvShowDetails.vote_count
    } else {
        movieDetails.vote_count
    }

    LaunchedEffect(key1 = movieOrTvShowId, key2 = movieOrTvShow) {
        viewModel.movieOrTVShowDetails(movieOrTvShow, movieOrTvShowId)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        } else {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .aspectRatio(0.75f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    val painter: Painter =
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = Constants.IMAGE_URL + image.toString())
                                .apply(block = fun ImageRequest.Builder.() {
                                    transformations(

                                    )
                                }).build()
                        )
                    Image(
                        painter = painter,
                        contentDescription = title,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color.Black
                                    )
                                )
                            )
                    )
                }
                Text(
                    text = title ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
                Text(
                    text = "Rating: $voteAverage \nVote Count: $voteCount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
                if (movieOrTvShow == "tv") {
                    Text(
                        text = "Seasons:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                    LazyRow(contentPadding = PaddingValues(6.dp)) {
                        items(tvShowDetails.seasons!!) {
                            ShowSeasons(it)
                        }
                    }
                }
                Text(
                    text = "Similar:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
                LazyRow(contentPadding = PaddingValues(6.dp)) {
                    items(similarMoviesAndTVShows) {
                        ShowSimilarMoviesOrTVShows(it, navController)
                    }
                }
            }
        }

    }
}

@Composable
fun ShowSeasons(season: Season?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(300.dp)
            .width(150.dp)
    ) {
        val painter: Painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = Constants.IMAGE_URL + season?.poster_path.toString())
                    .apply(block = fun ImageRequest.Builder.() {
                        transformations(

                        )
                    }).build()
            )
        Image(
            painter = painter,
            contentDescription = season?.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            Alignment.BottomCenter
        ) {
            season?.name.let {
                if (it != null) {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ShowSimilarMoviesOrTVShows(movieAndTVShow: MoviesAndTVShowsResult, navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(300.dp)
            .width(150.dp).clickable {
                navController.navigate(
                    "movie_and_tv_show_details_screen/${movieAndTVShow.id}/${movieAndTVShow.mediaType}"
                )
            }
    ) {
        val painter: Painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = Constants.IMAGE_URL + movieAndTVShow.posterPath)
                    .apply(block = fun ImageRequest.Builder.() {
                        transformations(

                        )
                    }).build()
            )
        Image(
            painter = painter,
            contentDescription = movieAndTVShow.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            Alignment.BottomCenter
        ) {
            if (movieAndTVShow.mediaType == "tv") {
                movieAndTVShow.name?.let {
                    Text(
                        text = it,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    )
                }
            } else {
                movieAndTVShow.originalTitle?.let {
                    Text(
                        text = it,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    )
                }
            }
        }
    }
}