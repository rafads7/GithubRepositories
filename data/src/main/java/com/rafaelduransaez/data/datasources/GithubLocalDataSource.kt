package com.rafaelduransaez.data.datasources

import com.rafaelduransaez.domain.RepositoryDetail
import kotlinx.coroutines.flow.Flow

interface GithubLocalDataSource {

    fun getRepoDetailById(id: Int): Flow<RepositoryDetail>
}