package com.rafaelduransaez.githubrepositories.framework.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Transaction
import androidx.room.withTransaction
import com.rafaelduransaez.githubrepositories.framework.local.database.GithubReposDatabase
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.KeyEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.mapper.toKeyEntityList
import com.rafaelduransaez.githubrepositories.framework.remote.GithubReposService
import com.rafaelduransaez.githubrepositories.framework.remote.mapper.toRepoEntityList
import com.rafaelduransaez.githubrepositories.framework.remote.mapper.toUserEntityList
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GithubReposRemoteMediator @Inject constructor(
    private val database: GithubReposDatabase,
    private val apiService: GithubReposService
) : RemoteMediator<Int, RepoEntity>() {

    private val reposDao = database.reposDao()
    private val usersDao = database.usersDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, RepoEntity>)
            : MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    // We can return Success with endOfPaginationReached = false because Paging
                    // will call this method again if RemoteKeys becomes non-null.
                    // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                    // the end of pagination for append.
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val apiResponse = apiService.bestRatedRepos(page) //state.config.pageSize
            val repos = apiResponse.toRepoEntityList()
            val users = apiResponse.toUserEntityList()

            // We use withTransaction in order to assure all previous transactions
            // before each transaction are executed successfully.
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    reposDao.clearAll()
                    usersDao.clearAll()
                }

                // Insert new repos into database, which invalidates the
                // current PagingData, allowing Paging to present the updates in the DB.

                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (repos.isEmpty()) null else page + 1
                val keys = repos.toKeyEntityList(prevKey, nextKey)

                usersDao.upsert(users)
                insertOrUpdateReposWithFavorites(repos)
                remoteKeyDao.upsert(keys)
            }

            MediatorResult.Success(
                endOfPaginationReached = repos.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    @Transaction
    private suspend fun insertOrUpdateReposWithFavorites(repos: List<RepoEntity>) {
        // Insert all repositories
        reposDao.upsert(repos)
        // Update favorite status based on the existence in the favorite_repo table
        reposDao.updateFavoriteStatus(repos.map { it.githubId })
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepoEntity>): KeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                remoteKeyDao.remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RepoEntity>): KeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                remoteKeyDao.remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RepoEntity>
    ): KeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeyDao.remoteKeysRepoId(repoId)
            }
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    companion object {
        private const val GITHUB_STARTING_PAGE_INDEX = 1
    }
}
