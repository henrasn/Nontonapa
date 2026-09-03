package com.henrasn.nontonapa.data.module

import com.henrasn.nontonapa.data.source.MovieDataSource
import com.henrasn.nontonapa.data.source.MovieDataSourceImpl
import com.henrasn.nontonapa.data.source.MovieDetailDataSource
import com.henrasn.nontonapa.data.source.MovieDetailDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindMovieDataSource(dataSource: MovieDataSourceImpl): MovieDataSource

    @Binds
    abstract fun bindMovieDetailDataSource(dataSource: MovieDetailDataSourceImpl): MovieDetailDataSource
}