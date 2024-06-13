package com.rafaelduransaez.data.datasources

import androidx.paging.PagingData
import com.rafaelduransaez.domain.RepoModel
import kotlinx.coroutines.flow.Flow

interface GithubReposMediatorDataSource
{
    fun reposPager(): Flow<PagingData<RepoModel>>
}