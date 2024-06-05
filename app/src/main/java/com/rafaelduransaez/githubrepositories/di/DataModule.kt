package com.rafaelduransaez.githubrepositories.di

import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.PagerDataSource
import com.rafaelduransaez.githubrepositories.framework.local.sources.GithubReposRoomLocalDataSourceImpl
import com.rafaelduransaez.githubrepositories.framework.mediator.PagerDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindMediatorDataSource(remoteDataSource: PagerDataSourceImpl): PagerDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSource: GithubReposRoomLocalDataSourceImpl): GithubReposLocalDataSource
}