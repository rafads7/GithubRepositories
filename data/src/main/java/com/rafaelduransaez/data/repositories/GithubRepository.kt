package com.rafaelduransaez.data.repositories

import com.rafaelduransaez.domain.sources.GithubReposLocalDataSource
import com.rafaelduransaez.domain.sources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.models.RepoDetailModel
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