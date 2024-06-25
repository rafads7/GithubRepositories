package com.rafaelduransaez.githubrepositories.sources.mediator

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.models.RepoModel
import com.rafaelduransaez.githubrepositories.sources.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.utils.toRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubReposMediatorDataSourceImpl @Inject constructor(
    private val reposPager: Pager<Int, RepoEntity>
) : GithubReposMediatorDataSource {
    override fun reposPager(): Flow<PagingData<RepoModel>> {
        return reposPager.flow.map { pagingData ->
            pagingData.map {
                it.toRepository()
            }
        }
    }
}