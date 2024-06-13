package com.rafaelduransaez.domain.sources

import com.rafaelduransaez.domain.models.Error
import com.rafaelduransaez.domain.models.RepoDetailModel
import kotlinx.coroutines.flow.Flow

interface GithubReposLocalDataSource {
    fun getRepoDetailById(id: Int): Flow<RepoDetailModel>
    fun getFavouriteRepositories(): Flow<List<RepoDetailModel>>
    suspend fun updateFavRepo(repo: RepoDetailModel): Error?
}