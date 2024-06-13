package com.rafaelduransaez.data.repositories

import androidx.paging.PagingData
import com.rafaelduransaez.domain.models.RepoModel
import kotlinx.coroutines.flow.Flow

interface PagedGithubReposRepository {
    fun getPagedBestRatedRepositories(): Flow<PagingData<RepoModel>>

}
