package com.henrasn.nontonapa.ui.pages.genre

import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData
import com.henrasn.nontonapa.data.repo.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieGenreViewModelTest {

    private val movieRepository: MovieRepository = mockk()
    private lateinit var viewModel: MovieGenreViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieGenreViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertTrue(state.genres.isEmpty())
    }

    @Test
    fun `loadMovieGenres success updates state with genres`() = runTest {
        // Given
        val mockGenres = listOf(
            GenreUiData(1, "Action"),
            GenreUiData(2, "Comedy")
        )
        coEvery { movieRepository.getMovieGenres() } returns Result.success(mockGenres)

        // When
        viewModel.onIntent(MovieGenreIntent.loadMovieGenres)

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(mockGenres, state.genres)
    }

    @Test
    fun `loadMovieGenres failure sends ShowError effect`() = runTest {
        // Given
        val exception = RuntimeException("API Error")
        coEvery { movieRepository.getMovieGenres() } returns Result.failure(exception)


        val effects = mutableListOf<MovieGenreEffect>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect { effects.add(it) }
        }

        // When
        viewModel.onIntent(MovieGenreIntent.loadMovieGenres)

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        assertTrue(effects.isNotEmpty())
        assertTrue(effects[0] is MovieGenreEffect.ShowError)

        job.cancel()
    }

    @Test
    fun `refreshPage success updates state and resets isRefreshing`() = runTest {
        // Given
        val mockGenres = listOf(GenreUiData(1, "Action"))
        coEvery { movieRepository.getMovieGenres() } returns Result.success(mockGenres)

        // When
        viewModel.onIntent(MovieGenreIntent.refreshPage)

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isRefreshing)
        assertFalse(state.isLoading)
        assertEquals(mockGenres, state.genres)
    }
}
