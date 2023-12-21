package com.rafaelduransaez.githubrepositories.framework.data

import com.rafaelduransaez.data.datasources.GithubRemoteDataSource
import com.rafaelduransaez.domain.toRepository
import com.rafaelduransaez.githubrepositories.framework.api.RepositoriesService
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(
    private val apiService: RepositoriesService
) : GithubRemoteDataSource {
    override suspend fun getBestRatedRepositories() =
        apiService.bestRatedRepos()
            .repos.map { it.toRepository() }
}