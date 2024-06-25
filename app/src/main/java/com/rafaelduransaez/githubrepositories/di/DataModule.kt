package com.rafaelduransaez.githubrepositories.di

import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.data.repositories.GithubReposRepositoryImpl
import com.rafaelduransaez.data.repositories.PagedGithubReposRepository
import com.rafaelduransaez.domain.repositories.GithubReposRepository
import com.rafaelduransaez.githubrepositories.sources.local.sources.GithubReposRoomLocalDataSourceImpl
import com.rafaelduransaez.githubrepositories.sources.mediator.GithubReposMediatorDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindGithubReposRepository(repo: GithubReposRepositoryImpl): GithubReposRepository

    @Binds
    abstract fun bindPagedGithubReposRepository(repo: GithubReposRepositoryImpl)
            : PagedGithubReposRepository

    @Binds
    abstract fun bindMediatorDataSource(remoteDataSource: GithubReposMediatorDataSourceImpl)
            : GithubReposMediatorDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSource: GithubReposRoomLocalDataSourceImpl)
            : GithubReposLocalDataSource
}