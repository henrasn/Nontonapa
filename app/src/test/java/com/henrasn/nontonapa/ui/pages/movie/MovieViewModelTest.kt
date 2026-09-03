package com.henrasn.nontonapa.ui.pages.movie

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.data.repo.MovieRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    private val movieRepository: MovieRepository = mockk()
    private lateinit var viewModel: MovieViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        viewModel.viewModelScope.cancel()
        Dispatchers.resetMain()
    }

    @Test
    fun `initial ui state is default`() {
        // When
        val state = viewModel.uiState.value

        // Then
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `movies uses genre id from caller and emits paged data`() = runTest {
        // Given
        val movie = MovieUiData(1, "Spider-Man", "/backdrop.jpg", "2026-07-29", 7.9f)
        every { movieRepository.getDiscoverMovies(35) } returns flowOf(PagingData.from(listOf(movie)))

        // When
        viewModel.movies(35).first()

        // Then
        verify(exactly = 1) { movieRepository.getDiscoverMovies(35) }
        assertFalse(viewModel.uiState.value.isLoading)
    }
}
