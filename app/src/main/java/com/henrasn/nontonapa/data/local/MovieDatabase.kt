package com.henrasn.nontonapa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henrasn.nontonapa.data.local.dao.MovieDao
import com.henrasn.nontonapa.data.local.dao.MovieRemoteKeysDao
import com.henrasn.nontonapa.data.local.dao.ReviewDao
import com.henrasn.nontonapa.data.local.dao.ReviewRemoteKeysDao
import com.henrasn.nontonapa.data.local.entity.MovieEntity
import com.henrasn.nontonapa.data.local.entity.MovieRemoteKeys
import com.henrasn.nontonapa.data.local.entity.ReviewEntity
import com.henrasn.nontonapa.data.local.entity.ReviewRemoteKeys

@Database(
    entities = [
        MovieEntity::class,
        MovieRemoteKeys::class,
        ReviewEntity::class,
        ReviewRemoteKeys::class
    ],
    version = 4,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao
    abstract fun reviewDao(): ReviewDao
    abstract fun reviewRemoteKeysDao(): ReviewRemoteKeysDao
}
