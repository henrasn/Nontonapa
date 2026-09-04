package com.henrasn.nontonapa.data.module

import android.content.Context
import androidx.room.Room
import com.henrasn.nontonapa.data.local.MovieDatabase
import com.henrasn.nontonapa.data.local.dao.MovieDao
import com.henrasn.nontonapa.data.local.dao.MovieRemoteKeysDao
import com.henrasn.nontonapa.data.local.dao.ReviewDao
import com.henrasn.nontonapa.data.local.dao.ReviewRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun provideMovieRemoteKeysDao(database: MovieDatabase): MovieRemoteKeysDao {
        return database.movieRemoteKeysDao()
    }

    @Provides
    fun provideReviewDao(database: MovieDatabase): ReviewDao {
        return database.reviewDao()
    }

    @Provides
    fun provideReviewRemoteKeysDao(database: MovieDatabase): ReviewRemoteKeysDao {
        return database.reviewRemoteKeysDao()
    }
}
