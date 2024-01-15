package com.rafaelduransaez.githubrepositories.di

import com.rafaelduransaez.data.datasources.GithubRemoteDataSource
import com.rafaelduransaez.data.datasources.RepositoriesMediatorDataSource
import com.rafaelduransaez.githubrepositories.framework.mediator.GithubReposMediatorDataSource
import com.rafaelduransaez.githubrepositories.framework.mediator.ReposRemoteMediator
import com.rafaelduransaez.githubrepositories.framework.remote.GithubRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: GithubRemoteDataSourceImpl): GithubRemoteDataSource

    @Binds
    abstract fun bindMediatorDataSource(remoteDataSource: GithubReposMediatorDataSource): RepositoriesMediatorDataSource
}