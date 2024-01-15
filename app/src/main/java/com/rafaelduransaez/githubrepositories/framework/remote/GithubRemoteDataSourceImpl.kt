package com.rafaelduransaez.githubrepositories.framework.remote

import com.rafaelduransaez.data.datasources.GithubRemoteDataSource
import com.rafaelduransaez.githubrepositories.utils.toRepository
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(
    private val apiService: RepositoriesService,
) : GithubRemoteDataSource {
    override suspend fun getBestRatedRepositories() =
        apiService.bestRatedRepos()
            .repos.map { it.toRepository() }
}