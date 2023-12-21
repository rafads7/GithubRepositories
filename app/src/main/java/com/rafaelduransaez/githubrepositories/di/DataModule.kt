package com.rafaelduransaez.githubrepositories.di

import com.rafaelduransaez.data.datasources.GithubRemoteDataSource
import com.rafaelduransaez.githubrepositories.framework.api.RepositoriesService
import com.rafaelduransaez.githubrepositories.framework.data.GithubRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: GithubRemoteDataSourceImpl): GithubRemoteDataSource
}