package com.rafaelduransaez.data

import com.rafaelduransaez.data.datasources.GithubLocalDataSource
import com.rafaelduransaez.data.datasources.RepositoriesMediatorDataSource
import com.rafaelduransaez.domain.RepositoryDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val localDataSource: GithubLocalDataSource,
    private val mediatorDataSource: RepositoriesMediatorDataSource,
) {
    suspend fun getPagedBestRatedRepositories() = mediatorDataSource.reposPager()

    fun getRepoDetailById(id: Int) =
        localDataSource.getRepoDetailById(id)

    suspend fun updateFavRepo(repo: RepositoryDetail) =
        localDataSource.updateFavRepo(repo)

}