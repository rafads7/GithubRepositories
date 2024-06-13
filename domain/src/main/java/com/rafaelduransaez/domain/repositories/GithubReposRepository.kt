package com.rafaelduransaez.domain.repositories

import com.rafaelduransaez.domain.models.Error
import com.rafaelduransaez.domain.models.RepoDetailModel
import kotlinx.coroutines.flow.Flow

interface GithubReposRepository {

    fun getFavouriteRepositories(): Flow<List<RepoDetailModel>>

    fun getRepoDetailById(id: Int): Flow<RepoDetailModel>

    suspend fun updateFavRepo(repo: RepoDetailModel): Error?
}