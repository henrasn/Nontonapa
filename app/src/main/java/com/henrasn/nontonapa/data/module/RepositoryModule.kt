package com.henrasn.nontonapa.data.module

import com.henrasn.nontonapa.data.repo.MovieRepository
import com.henrasn.nontonapa.data.repo.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(repo: MovieRepositoryImpl): MovieRepository
}