package com.henrasn.nontonapa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henrasn.nontonapa.data.local.entity.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {

    @Query("SELECT * FROM movie_remote_keys WHERE movieId = :movieId AND genreId = :genreId")
    suspend fun getRemoteKeys(movieId: Int, genreId: Int): MovieRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<MovieRemoteKeys>)

    @Query("DELETE FROM movie_remote_keys WHERE genreId = :genreId")
    suspend fun clearByGenre(genreId: Int)
}
