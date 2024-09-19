package com.rafaelduransaez.githubrepositories.di

import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.githubrepositories.framework.local.sources.GithubReposRoomLocalDataSourceImpl
import com.rafaelduransaez.githubrepositories.framework.mediator.GithubReposMediatorDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindMediatorDataSource(remoteDataSource: GithubReposMediatorDataSourceImpl)
            : GithubReposMediatorDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSource: GithubReposRoomLocalDataSourceImpl)
            : GithubReposLocalDataSource
}