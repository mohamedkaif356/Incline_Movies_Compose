package com.example.wsaudiology.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.wsaudiology.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.wsaudiology.domain.usecase.MoviesAndTVShowsUseCase
import com.example.wsaudiology.utils.NetworkStatusLiveData
import com.example.wsaudiology.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesAndTVShowsListViewModel @Inject constructor(
    private val moviesAndTVShowsUseCase: MoviesAndTVShowsUseCase,
    context: Context
) : ViewModel() {

    private val networkStatusLiveData = NetworkStatusLiveData(context).asFlow()


    val moviesAndTVShowList = mutableStateOf<List<MoviesAndTVShowsResult>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    var liveNetworkState = mutableStateOf(true)
    init {
        viewModelScope.launch {
            networkStatusLiveData.map  {
                liveNetworkState.value = it
            }
        }
        moviesAndTVShowList(liveNetworkState.value)
    }
    fun moviesAndTVShowList(isNetworkAvailable: Boolean) = viewModelScope.launch{
        isLoading.value = true
        moviesAndTVShowsUseCase.invoke(isNetworkAvailable).collect { list ->
            when(list) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    if (!list.data.isNullOrEmpty()) {
                        moviesAndTVShowList.value = list.data
                    } else {
                        loadError.value = "No Data Available"
                    }
                }
                is Resource.Error -> {
                    isLoading.value = false
                    loadError.value = list.error?.message ?: "Unknown error"
                }

                else -> {}
            }
        }
    }


    fun searchTVShowList(tvShowName: String) = viewModelScope.launch{
        isLoading.value = true
        isSearching.value = true
        if (tvShowName == "") {
            moviesAndTVShowList(liveNetworkState.value)
        } else {
            moviesAndTVShowsUseCase.invoke(tvShowName).collect { list ->
                when(list) {
                    is Resource.Success -> {
                        loadError.value = ""
                        isLoading.value = false
                        if (!list.data?.results.isNullOrEmpty()) {
                            moviesAndTVShowList.value = list.data?.results!!
                        } else {
                            loadError.value = "No Data Available"
                        }
                    }
                    is Resource.Error -> {
                        isLoading.value = false
                        loadError.value = list.error?.message ?: "Unknown error"
                    }

                    else -> {}
                }
            }
        }
    }
}