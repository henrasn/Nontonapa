package com.henrasn.nontonapa.data.mapper

import com.henrasn.nontonapa.data.model.dto.genre.GenresItem
import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GenreResponseMapperTest {

    @Test
    fun `toGenreUiDataList should map response to UI model list`() {
        val response = MovieGenreResponse(
            genres = listOf(
                GenresItem(id = 1, name = "Action"),
                GenresItem(id = 2, name = "Comedy")
            )
        )

        val result = response.toGenreUiDataList()

        assertEquals(2, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Action", result[0].genreName)
        assertEquals(2, result[1].id)
        assertEquals("Comedy", result[1].genreName)
    }

    @Test
    fun `toGenreUiDataList should filter out null items`() {
        val response = MovieGenreResponse(
            genres = listOf(
                GenresItem(id = 1, name = "Action"),
                null
            )
        )

        val result = response.toGenreUiDataList()

        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
    }

    @Test
    fun `toGenreUiDataList should return empty list for null genres`() {
        val response = MovieGenreResponse(genres = null)
        val result = response.toGenreUiDataList()
        assertTrue(result.isEmpty())
    }
}
