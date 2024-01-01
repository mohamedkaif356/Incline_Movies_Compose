package com.example.wsaudiology.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wsaudiology.utils.NetworkStatusLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkStateViewModel @Inject constructor(
    private val networkStatusLiveData: NetworkStatusLiveData
) : ViewModel() { val networkStatus get() = networkStatusLiveData }