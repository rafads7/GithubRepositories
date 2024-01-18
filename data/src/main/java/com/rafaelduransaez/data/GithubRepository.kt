package com.rafaelduransaez.data

import com.rafaelduransaez.data.datasources.GithubLocalDataSource
import com.rafaelduransaez.data.datasources.GithubRemoteDataSource
import com.rafaelduransaez.data.datasources.RepositoriesMediatorDataSource
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val remoteDataSource: GithubRemoteDataSource,
    private val localDataSource: GithubLocalDataSource,
    private val mediatorDataSource: RepositoriesMediatorDataSource,
) {
    //val bestRatedRepos get() = localDataSource.bestRatedRepos

    suspend fun getBestRatedRepositories() = remoteDataSource.getBestRatedRepositories()

    suspend fun getPagedBestRatedRepositories() = mediatorDataSource.reposPager()

    fun getRepoDetailById(id: Int) = localDataSource.getRepoDetailById(id)

}