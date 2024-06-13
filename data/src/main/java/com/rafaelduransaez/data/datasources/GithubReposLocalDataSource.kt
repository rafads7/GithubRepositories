package com.rafaelduransaez.data.datasources

import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.RepoDetailModel
import kotlinx.coroutines.flow.Flow

interface GithubReposLocalDataSource {
    fun getRepoDetailById(id: Int): Flow<RepoDetailModel>
    fun getFavouriteRepositories(): Flow<List<RepoDetailModel>>
    suspend fun updateFavRepo(repo: RepoDetailModel): Error?
}