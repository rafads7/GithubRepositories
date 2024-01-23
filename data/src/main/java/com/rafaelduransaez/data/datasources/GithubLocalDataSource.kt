package com.rafaelduransaez.data.datasources

import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.RepositoryDetail
import kotlinx.coroutines.flow.Flow

interface GithubLocalDataSource {

    fun getRepoDetailById(id: Int): Flow<RepositoryDetail>
    suspend fun updateFavRepo(repo: RepositoryDetail): Error?
}