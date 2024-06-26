package com.rafaelduransaez.githubrepositories.framework.local.sources

import androidx.room.Transaction
import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.FavouriteRepoDao
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.utils.toFavouriteRepo
import com.rafaelduransaez.githubrepositories.utils.toRepositoryDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubReposRoomLocalDataSourceImpl @Inject constructor(
    private val repoDao: ReposDao,
    private val favouriteRepoDao: FavouriteRepoDao
) : GithubReposLocalDataSource {

    override fun getRepoDetailById(id: Int): Flow<RepositoryDetail> =
        repoDao.getRepoDetail(id).map { it.toRepositoryDetail() }

    override fun getFavouriteRepositories(): Flow<List<RepositoryDetail>> =
        repoDao.getFavouriteRepos().map { repos -> repos.map { it.toRepositoryDetail() } }

    @Transaction
    override suspend fun updateFavRepo(repo: RepositoryDetail): Error? {

        return try {
            repoDao.update(repo.id, repo.favourite)
            val updatedRepo = repoDao.get(repo.id)
                .firstOrNull()?.toFavouriteRepo()

            updatedRepo?.let {
                if (repo.favourite)
                    favouriteRepoDao.upsert(it)
                else
                    favouriteRepoDao.delete(it.githubId)
            }
            null
        } catch (e: Exception) {
            Error.Database
        }
    }
}