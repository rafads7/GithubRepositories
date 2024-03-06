package com.rafaelduransaez.githubrepositories.framework.local

import androidx.room.Transaction
import com.rafaelduransaez.data.datasources.GithubLocalDataSource
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.domain.UserDetail
import com.rafaelduransaez.githubrepositories.framework.database.dao.FavouriteRepoDao
import com.rafaelduransaez.githubrepositories.framework.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.utils.toFavouriteRepo
import com.rafaelduransaez.githubrepositories.utils.toRepositoryDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val repoDao: ReposDao,
    private val favouriteRepoDao: FavouriteRepoDao
) : GithubLocalDataSource {

    override fun getRepoDetailById(id: Int): Flow<RepositoryDetail> =
        repoDao.getRepoDetail(id).map { it.toRepositoryDetail() }

    override fun getFavouriteRepositories(): Flow<List<RepositoryDetail>> =
        repoDao.getFavouriteRepos().map { it.map { it.toRepositoryDetail() } }


    @Transaction
    override suspend fun updateFavRepo(detail: RepositoryDetail): Error? {

        return try {
            repoDao.update(detail.id, detail.favourite)
            val updatedRepo = repoDao.get(detail.id)
                .firstOrNull()?.toFavouriteRepo()

            updatedRepo?.let {
                if (detail.favourite)
                    favouriteRepoDao.upsert(it)
                else
                    favouriteRepoDao.delete(it.githubId)
            }
            null
        } catch (e: Exception) {
            Error.Database
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