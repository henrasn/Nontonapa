package com.henrasn.nontonapa.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.henrasn.nontonapa.data.local.dao.MovieDao
import com.henrasn.nontonapa.data.local.entity.MovieEntity
import com.henrasn.nontonapa.data.model.dto.movie.MovieDiscoverResponse
import com.henrasn.nontonapa.data.model.dto.movie.MovieItem
import com.henrasn.nontonapa.data.source.MovieDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class MovieRemoteMediatorTest {

    private lateinit var movieDataSource: MovieDataSource
    private lateinit var movieDao: MovieDao
    private lateinit var mediator: MovieRemoteMediator

    @Before
    fun setUp() {
        movieDataSource = mockk()
        movieDao = mockk(relaxed = true)
        mediator = MovieRemoteMediator(
            genreId = 28,
            movieDataSource = movieDataSource,
            movieDao = movieDao,
            dispatcher = UnconfinedTestDispatcher()
        )
    }

    private fun pagingState(
        pages: List<PagingSource.LoadResult.Page<Int, MovieEntity>> = emptyList(),
        anchorPosition: Int? = null
    ): PagingState<Int, MovieEntity> {
        return PagingState(
            pages = pages,
            anchorPosition = anchorPosition,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )
    }

    private fun page(entities: List<MovieEntity>): PagingSource.LoadResult.Page<Int, MovieEntity> {
        return PagingSource.LoadResult.Page(entities, null, null)
    }

    @Test
    fun `refresh success inserts entities and not end of pagination`() = runTest {
        // Given
        val response = MovieDiscoverResponse(
            page = 1,
            totalPages = 5,
            totalResults = 100,
            results = listOf(
                MovieItem(id = 1, title = "A"),
                MovieItem(id = 2, title = "B")
            )
        )
        coEvery { movieDataSource.getDiscoverMovies(1, 28) } returns response

        // When
        val result = mediator.load(LoadType.REFRESH, pagingState())

        // Then
        coVerify(exactly = 1) { movieDao.clearAll() }
        coVerify(exactly = 1) { movieDao.insertAll(any()) }
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        val success = result as RemoteMediator.MediatorResult.Success
        assertFalse(success.endOfPaginationReached)
    }

    @Test
    fun `refresh on single page reaches end of pagination`() = runTest {
        // Given
        val response = MovieDiscoverResponse(
            page = 1,
            totalPages = 1,
            totalResults = 1,
            results = listOf(MovieItem(id = 3, title = "C"))
        )
        coEvery { movieDataSource.getDiscoverMovies(1, 28) } returns response

        // When
        val result = mediator.load(LoadType.REFRESH, pagingState())

        // Then
        val success = result as RemoteMediator.MediatorResult.Success
        assertTrue(success.endOfPaginationReached)
    }

    @Test
    fun `refresh with empty results returns end of pagination without inserting`() = runTest {
        // Given
        val response = MovieDiscoverResponse(
            page = 1,
            totalPages = 0,
            totalResults = 0,
            results = emptyList()
        )
        coEvery { movieDataSource.getDiscoverMovies(1, 28) } returns response

        // When
        val result = mediator.load(LoadType.REFRESH, pagingState())

        // Then
        val success = result as RemoteMediator.MediatorResult.Success
        assertTrue(success.endOfPaginationReached)
        coVerify(exactly = 0) { movieDao.insertAll(any()) }
    }

    @Test
    fun `data source throws returns error`() = runTest {
        // Given
        coEvery { movieDataSource.getDiscoverMovies(any(), any()) } throws
                RuntimeException("Network error")

        // When
        val result = mediator.load(LoadType.REFRESH, pagingState())

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun `append computes next page from last item sort order`() = runTest {
        // Given
        val lastEntity = MovieEntity(
            id = 20, title = "Z", backdropPath = "", releaseDate = "",
            voteAverage = 0.0f, genreIds = "", sortOrder = 19
        )
        val state = pagingState(pages = listOf(page(listOf(lastEntity))))
        val response = MovieDiscoverResponse(
            page = 2,
            totalPages = 3,
            totalResults = 60,
            results = listOf(MovieItem(id = 21, title = "W"))
        )
        coEvery { movieDataSource.getDiscoverMovies(2, 28) } returns response

        // When
        val result = mediator.load(LoadType.APPEND, state)

        // Then next page = (19 / 20) + 2 = 2
        coVerify { movieDataSource.getDiscoverMovies(2, 28) }
        val success = result as RemoteMediator.MediatorResult.Success
        assertFalse(success.endOfPaginationReached)
    }

    @Test
    fun `append when no last item returns end of pagination without fetch`() = runTest {
        // When
        val result = mediator.load(LoadType.APPEND, pagingState())

        // Then
        val success = result as RemoteMediator.MediatorResult.Success
        assertTrue(success.endOfPaginationReached)
        coVerify(exactly = 0) { movieDataSource.getDiscoverMovies(any(), any()) }
    }

    @Test
    fun `prepend returns end of pagination without fetching`() = runTest {
        // When
        val result = mediator.load(LoadType.PREPEND, pagingState())

        // Then
        val success = result as RemoteMediator.MediatorResult.Success
        assertTrue(success.endOfPaginationReached)
        coVerify(exactly = 0) { movieDataSource.getDiscoverMovies(any(), any()) }
    }
}
