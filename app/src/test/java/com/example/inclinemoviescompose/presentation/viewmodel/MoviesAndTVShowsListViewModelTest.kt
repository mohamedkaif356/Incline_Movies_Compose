package com.example.inclinemoviescompose.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import com.example.inclinemoviescompose.domain.usecase.MoviesAndTVShowsUseCase
import com.example.inclinemoviescompose.utils.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesAndTVShowsListViewModelTest {
    private fun createViewModel(mockUseCase: MoviesAndTVShowsUseCase, mockContext: Context): MoviesAndTVShowsListViewModel {
        return MoviesAndTVShowsListViewModel(mockUseCase, mockContext)
    }

    private val testDispatcher = StandardTestDispatcher()
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test loading initial movies and TV shows`() = runTest {
        val mockUseCase = mockk<MoviesAndTVShowsUseCase>()
        val mockContext = mockk<Context>()
        val mockConnectivityManager = mockk<ConnectivityManager>()
        every { mockContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns mockConnectivityManager
        every { mockContext.applicationContext } returns mockContext
        val viewModel = createViewModel(mockUseCase, mockContext)

        val emptyData = emptyList<MoviesAndTVShowsResult>()
        coEvery { mockUseCase.invoke(true) } returns flowOf(Resource.Success(emptyData))

        viewModel.moviesAndTVShowList(true)

        // Verify handling of empty data response
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(emptyData, viewModel.moviesAndTVShowList.value)
    }

}