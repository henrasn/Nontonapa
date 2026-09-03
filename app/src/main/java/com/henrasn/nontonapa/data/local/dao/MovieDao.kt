package com.henrasn.nontonapa.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henrasn.nontonapa.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY sortOrder")
    fun getMoviesPagingSource(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
