package com.example.inclinemoviescompose.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.inclinemoviescompose.domain.Constants
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.inclinemoviescompose.presentation.ui.components.SearchBar
import com.example.inclinemoviescompose.presentation.ui.components.ShowToast
import com.example.inclinemoviescompose.presentation.viewmodel.MoviesAndTVShowsListViewModel

@Composable
fun MoviesAndTVShowsDetails(
    navController: NavController,
    viewModel: MoviesAndTVShowsListViewModel = hiltViewModel()
) {
    val isNetworkAvailable = viewModel.networkStatusLiveData.observeAsState()
    Column {
        Row {
            Surface(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
            ) {
                SearchBar(
                    hint = "Search your favorite TV Show",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    if (isNetworkAvailable.value == true) {
                        viewModel.searchTVShowList(it)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (isNetworkAvailable.value == false) {
            ShowToast(message = "Please make sure you have Internet connection to search your favorite TV Show and check out it's details")
            viewModel.moviesAndTVShowList(isNetworkAvailable.value!!)
        } else {
            isNetworkAvailable.value?.let { viewModel.moviesAndTVShowList(it) }
        }
        Row {
            Surface(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MoviesAndTVShowsList(navController = navController)
            }
        }
    }
}

@Composable
fun MoviesAndTVShowsList(
    navController: NavController,
    viewModel: MoviesAndTVShowsListViewModel = hiltViewModel()
) {
    val isNetworkAvailable = viewModel.networkStatusLiveData.observeAsState()
    val moviesAndTVShowsList by remember { viewModel.moviesAndTVShowList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(6.dp)) {
        items(moviesAndTVShowsList) {
            if (!isSearching && !isLoading && moviesAndTVShowsList.isEmpty()) {
                isNetworkAvailable.value?.let { it1 -> viewModel.moviesAndTVShowList(it1) }
            }
            MovieAndTVShowCard(movieAndTVShow = it, navController = navController)
        }
    }
    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                isNetworkAvailable.value?.let { viewModel.moviesAndTVShowList(it) }
            }
        }
    }
}

@Composable
fun MovieAndTVShowCard(
    movieAndTVShow: MoviesAndTVShowsResult,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MoviesAndTVShowsListViewModel = hiltViewModel()
) {
    val isSearching by remember { viewModel.isSearching }
    val isNetworkAvailable = viewModel.networkStatusLiveData.observeAsState()
    Box(
        contentAlignment = Center,
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(0.75f)
            .clickable {
                if (isNetworkAvailable.value == true
                    && movieAndTVShow.id.toString() != ""
                    && movieAndTVShow.mediaType.toString() != "") {
                    navController.navigate(
                        "movie_and_tv_show_details_screen/${movieAndTVShow.id}/${movieAndTVShow.mediaType}"
                    )
                } else if (isNetworkAvailable.value == true && isSearching) {
                    navController.navigate(
                        "movie_and_tv_show_details_screen/${movieAndTVShow.id}/tv"
                    )
                }
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
            contentDescription = movieAndTVShow.originalName,
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

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
