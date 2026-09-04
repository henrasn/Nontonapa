package com.henrasn.nontonapa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henrasn.nontonapa.data.local.entity.ReviewRemoteKeys

@Dao
interface ReviewRemoteKeysDao {

    @Query("SELECT * FROM review_remote_keys WHERE id = :id AND movieId = :movieId")
    suspend fun getRemoteKeys(id: String, movieId: Int): ReviewRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<ReviewRemoteKeys>)

    @Query("DELETE FROM review_remote_keys WHERE movieId = :movieId")
    suspend fun clearByMovie(movieId: Int)
}
