package com.rafaelduransaez.data.repositories

import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.RepoDetailModel
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val localDataSource: GithubReposLocalDataSource,
    private val mediatorDataSource: GithubReposMediatorDataSource,
) {
    fun getPagedBestRatedRepositories() = mediatorDataSource.reposPager()

    fun getFavouriteRepositories() = localDataSource.getFavouriteRepositories()

    fun getRepoDetailById(id: Int) = localDataSource.getRepoDetailById(id)

    suspend fun updateFavRepo(repo: RepoDetailModel) = localDataSource.updateFavRepo(repo)

}