package com.rafaelduransaez.githubrepositories.framework.local

import com.rafaelduransaez.data.datasources.GithubLocalDataSource
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.domain.UserDetail
import com.rafaelduransaez.githubrepositories.framework.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.utils.toRepositoryDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val repoDao: ReposDao,
) : GithubLocalDataSource {

    override fun getRepoDetailById(id: Int): Flow<RepositoryDetail> =
       repoDao.getRepoDetail(id).map { it.toRepositoryDetail() }

    /*
    repoDao.getRepoDetail(id).map {
        RepositoryDetail(
            id, it.name, it.description ?: "",
            it.starsCount, it.forksCount, it.language ?: "",
            owner = UserDetail("username", "url"), url = it.url
        )
       }
    */

    override suspend fun updateFavRepo(repo: RepositoryDetail): Error? {
        repoDao.update(repo.id, repo.favourite).let { updatedRows ->
            return try {
                if (updatedRows > 0) null
                else Error.Database
            } catch (e: Exception) {
                Error.Database
            }
        }

        /*
        var error: Error? = null
        repoDao.get(repo.id).collect {

            error = repoDao.upsert(it.copy(favourite = !it.favourite)).let { updatedRows ->
                try {
                    if (updatedRows > 0) null
                    else Error.Database
                } catch (e: Exception) {
                    Error.Database
                }
            }
        }
        return error
         */
    }
}