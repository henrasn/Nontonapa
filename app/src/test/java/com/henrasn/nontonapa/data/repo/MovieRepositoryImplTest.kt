package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.core.error.AppException
import com.henrasn.nontonapa.data.model.dto.genre.GenresItem
import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse
import com.henrasn.nontonapa.data.source.MovieDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private lateinit var movieDataSource: MovieDataSource
    private lateinit var movieRepository: MovieRepositoryImpl

    @Before
    fun setUp() {
        movieDataSource = mockk()
        movieRepository = MovieRepositoryImpl(movieDataSource, UnconfinedTestDispatcher())
    }

    @Test
    fun `getMovieGenres should return success when data source returns data`() = runTest {
        // Given
        val mockResponse = MovieGenreResponse(
            genres = listOf(
                GenresItem(id = 1, name = "Action"),
                GenresItem(id = 2, name = "Comedy")
            )
        )
        coEvery { movieDataSource.getMovieGenres() } returns mockResponse

        // When
        val result = movieRepository.getMovieGenres()

        // Then
        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertEquals(2, data?.size)
        assertEquals(1, data?.get(0)?.id)
        assertEquals("Action", data?.get(0)?.genreName)
        assertEquals(2, data?.get(1)?.id)
        assertEquals("Comedy", data?.get(1)?.genreName)
    }

    @Test
    fun `getMovieGenres should return empty list when data source returns null genres`() = runTest {
        // Given
        val mockResponse = MovieGenreResponse(genres = null)
        coEvery { movieDataSource.getMovieGenres() } returns mockResponse

        // When
        val result = movieRepository.getMovieGenres()

        // Then
        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertTrue(data?.isEmpty() == true)
    }

    @Test
    fun `getMovieGenres should return empty list when data source returns empty genres`() =
        runTest {
            // Given
            val mockResponse = MovieGenreResponse(genres = emptyList())
            coEvery { movieDataSource.getMovieGenres() } returns mockResponse

            // When
            val result = movieRepository.getMovieGenres()

            // Then
            assertTrue(result.isSuccess)
            val data = result.getOrNull()
            assertTrue(data?.isEmpty() == true)
        }

    @Test
    fun `getMovieGenres should return failure when data source throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { movieDataSource.getMovieGenres() } throws exception

        // When
        val result = movieRepository.getMovieGenres()

        // Then
        assertTrue(result.isFailure)
        val failure = result.exceptionOrNull()
        assertTrue(failure is AppException)
        assertEquals(exception, failure?.cause)
    }

    @Test
    fun `getMovieGenres should handle null items in genres list gracefully`() = runTest {
        // Given
        val mockResponse = MovieGenreResponse(
            genres = listOf(
                GenresItem(id = 1, name = "Action"),
                null
            )
        )
        coEvery { movieDataSource.getMovieGenres() } returns mockResponse

        // When
        val result = movieRepository.getMovieGenres()

        // Then
        assertTrue(result.isSuccess)
        val data = result.getOrNull()
        assertEquals(1, data?.size)
        assertEquals(1, data?.get(0)?.id)
        assertEquals("Action", data?.get(0)?.genreName)
    }
}
