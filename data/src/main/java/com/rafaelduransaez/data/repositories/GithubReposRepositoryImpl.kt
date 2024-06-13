package com.rafaelduransaez.data.repositories

import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.models.RepoDetailModel
import com.rafaelduransaez.domain.repositories.GithubReposRepository
import javax.inject.Inject

class GithubReposRepositoryImpl @Inject constructor(
    private val localDataSource: GithubReposLocalDataSource,
    private val mediatorDataSource: GithubReposMediatorDataSource,
): GithubReposRepository, PagedGithubReposRepository {
    override fun getPagedBestRatedRepositories() = mediatorDataSource.reposPager()

    override fun getFavouriteRepositories() = localDataSource.getFavouriteRepositories()

    override fun getRepoDetailById(id: Int) = localDataSource.getRepoDetailById(id)

    override suspend fun updateFavRepo(repo: RepoDetailModel) = localDataSource.updateFavRepo(repo)

}