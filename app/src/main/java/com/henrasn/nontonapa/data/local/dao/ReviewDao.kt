package com.henrasn.nontonapa.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henrasn.nontonapa.data.local.entity.ReviewEntity

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE movieId = :movieId ORDER BY sortOrder")
    fun getReviewsPagingSource(movieId: Int): PagingSource<Int, ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reviews: List<ReviewEntity>)

    @Query("DELETE FROM reviews WHERE movieId = :movieId")
    suspend fun clearByMovie(movieId: Int)
}
