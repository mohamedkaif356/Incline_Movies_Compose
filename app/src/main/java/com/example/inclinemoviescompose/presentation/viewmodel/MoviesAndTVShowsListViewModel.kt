package com.example.inclinemoviescompose.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.inclinemoviescompose.domain.usecase.MoviesAndTVShowsUseCase
import com.example.inclinemoviescompose.utils.NetworkStatusLiveData
import com.example.inclinemoviescompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesAndTVShowsListViewModel @Inject constructor(
    private val moviesAndTVShowsUseCase: MoviesAndTVShowsUseCase,
    context: Context
) : ViewModel() {

    private val _networkStatusLiveData = NetworkStatusLiveData(context)


    val moviesAndTVShowList = mutableStateOf<List<MoviesAndTVShowsResult>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    val networkStatusLiveData: LiveData<Boolean>
        get() = _networkStatusLiveData
    fun moviesAndTVShowList(isNetworkAvailable: Boolean) = viewModelScope.launch{
        isLoading.value = true
        loadError.value = ""
        moviesAndTVShowList.value = listOf()
        moviesAndTVShowsUseCase.invoke(isNetworkAvailable).collect { list ->
            when(list) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    if (!list.data.isNullOrEmpty()) {
                        moviesAndTVShowList.value = list.data
                    } else {
                        loadError.value = "No Data Available locally"
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
            networkStatusLiveData.value?.let { moviesAndTVShowList(it) }
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
                            moviesAndTVShowList.value = listOf()
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