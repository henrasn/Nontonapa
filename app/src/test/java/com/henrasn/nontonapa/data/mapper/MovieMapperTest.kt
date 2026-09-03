package com.henrasn.nontonapa.data.mapper

import com.henrasn.nontonapa.data.local.entity.MovieEntity
import com.henrasn.nontonapa.data.model.dto.movie.MovieItem
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `toMovieEntity should map dto to entity with sort order`() {
        // Given
        val item = MovieItem(
            id = 1,
            title = "Spider-Man: Brand New Day",
            backdropPath = "/backdrop.jpg",
            releaseDate = "2026-07-29",
            voteAverage = 7.875f,
            genreIds = listOf(28, 12)
        )

        // When
        val entity = item.toMovieEntity(sortOrder = 3)

        // Then
        assertEquals(1, entity.id)
        assertEquals("Spider-Man: Brand New Day", entity.title)
        assertEquals("/backdrop.jpg", entity.backdropPath)
        assertEquals("2026-07-29", entity.releaseDate)
        assertEquals(7.875f, entity.voteAverage, 0.0f)
        assertEquals("28,12", entity.genreIds)
        assertEquals(3, entity.sortOrder)
    }

    @Test
    fun `toMovieEntity should default nullable fields`() {
        // Given
        val item = MovieItem(id = 2)

        // When
        val entity = item.toMovieEntity(sortOrder = 0)

        // Then
        assertEquals("", entity.title)
        assertEquals("", entity.backdropPath)
        assertEquals("", entity.releaseDate)
        assertEquals(0.0f, entity.voteAverage, 0.0f)
        assertEquals("", entity.genreIds)
    }

    @Test
    fun `toMovieUiData should map entity to ui data`() {
        // Given
        val entity = MovieEntity(
            id = 1,
            title = "Spider-Man: Brand New Day",
            backdropPath = "/backdrop.jpg",
            releaseDate = "2026-07-29",
            voteAverage = 7.875f,
            genreIds = "28,12",
            sortOrder = 3
        )

        // When
        val uiData = entity.toMovieUiData()

        // Then
        assertEquals(1, uiData.id)
        assertEquals("Spider-Man: Brand New Day", uiData.title)
        assertEquals("/backdrop.jpg", uiData.backdropPath)
        assertEquals("2026-07-29", uiData.releaseDate)
        assertEquals(7.875f, uiData.voteAverage, 0.0f)
    }
}
