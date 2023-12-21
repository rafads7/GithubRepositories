package com.rafaelduransaez.data

import com.rafaelduransaez.data.datasources.GithubRemoteDataSource
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val remoteDataSource: GithubRemoteDataSource
) {
    //val bestRatedRepos get() = localDataSource.bestRatedRepos

    suspend fun getBestRatedRepositories() = remoteDataSource.getBestRatedRepositories()
}