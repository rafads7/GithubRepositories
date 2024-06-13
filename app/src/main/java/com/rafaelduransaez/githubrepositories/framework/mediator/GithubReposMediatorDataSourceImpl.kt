package com.rafaelduransaez.githubrepositories.framework.mediator

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.rafaelduransaez.domain.sources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.models.RepoModel
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
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