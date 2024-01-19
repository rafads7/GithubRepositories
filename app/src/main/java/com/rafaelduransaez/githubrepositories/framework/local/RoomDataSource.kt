package com.rafaelduransaez.githubrepositories.framework.local

import com.rafaelduransaez.data.datasources.GithubLocalDataSource
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.framework.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.utils.toRepositoryDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val repoDao: ReposDao,
): GithubLocalDataSource {
    override fun getRepoDetailById(id: Int): Flow<RepositoryDetail> =
        repoDao.getRepoDetail(id).map { it.toRepositoryDetail() }
}