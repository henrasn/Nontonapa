package com.henrasn.nontonapa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henrasn.nontonapa.data.local.dao.MovieDao
import com.henrasn.nontonapa.data.local.dao.MovieRemoteKeysDao
import com.henrasn.nontonapa.data.local.entity.MovieEntity
import com.henrasn.nontonapa.data.local.entity.MovieRemoteKeys

@Database(
    entities = [MovieEntity::class, MovieRemoteKeys::class],
    version = 3,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao
}
