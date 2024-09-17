package com.rafaelduransaez.githubrepositories.integration

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.entities.buildMockRepository
import com.rafaelduransaez.githubrepositories.entities.mockFavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.entities.mockRepoEntity
import com.rafaelduransaez.githubrepositories.entities.mockRepoEntityFav
import com.rafaelduransaez.githubrepositories.entities.mockRepoUserEntity
import com.rafaelduransaez.githubrepositories.entities.mockRepoUserEntityFav
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.FavouriteRepoDao
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.FavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoUserEntity
import com.rafaelduransaez.githubrepositories.framework.local.sources.GithubReposRoomLocalDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update


class FakeRepoDao(
    repos: List<RepoEntity> = emptyList(),
    detailedRepos: List<RepoUserEntity> = emptyList()
) : ReposDao {

    private val inMemoryRepos = MutableStateFlow(repos)
    private val inMemoryDetailedRepos = MutableStateFlow(detailedRepos)
    private lateinit var repoFlow: MutableStateFlow<RepoEntity>

    override fun pagingRepos(): PagingSource<Int, RepoEntity> {
        return FakePagingSource(inMemoryRepos.value)
    }

    override fun get(id: Int): Flow<RepoEntity> {
        return MutableStateFlow(inMemoryRepos.value.first { it.id == id })
    }

    override fun getRepoDetail(id: Int): Flow<RepoUserEntity> {
        return MutableStateFlow(inMemoryDetailedRepos.value.first { it.repo.id == id })
    }

    override fun getFavouriteRepos(): Flow<List<RepoUserEntity>> {
        return MutableStateFlow(inMemoryDetailedRepos.value.filter { it.repo.favourite })
    }

    override fun clearAll(): Int {
        return inMemoryRepos.value.size
    }

    override suspend fun upsert(repos: List<RepoEntity>): List<Long> {
        inMemoryRepos.value = repos
        if (::repoFlow.isInitialized) {
            repos.firstOrNull { it.id == repoFlow.value.id }?.let {
                repoFlow = MutableStateFlow(it)
            }
        }
        return inMemoryRepos.value.map { it.id.toLong() }
    }

    override suspend fun upsert(repo: RepoEntity): Long {
        inMemoryRepos.update { inMemoryRepos.value.filter { it.githubId != repo.githubId } }
        inMemoryRepos.update { inMemoryRepos.value + repo }
        return repo.id.toLong()
    }

    override suspend fun update(id: Int, favourite: Boolean): Int {
        val repoToUpdate = inMemoryRepos.value.firstOrNull { it.id == id }
        val updatedRepo = repoToUpdate?.copy(favourite = favourite)
        inMemoryRepos.value.subtract(listOfNotNull(repoToUpdate).toSet())

        updatedRepo?.let {
            inMemoryRepos.update { inMemoryRepos.value + updatedRepo }
            return 1
        } ?: return 0
    }

    override suspend fun updateFavoriteStatus(reposGithubIds: List<Int>): Int {
        val favRepos = inMemoryRepos.value.filter { it.githubId in reposGithubIds }
        inMemoryRepos.update { inMemoryRepos.value - favRepos.toSet() }
        inMemoryRepos.update { inMemoryRepos.value + favRepos.map { it.copy(favourite = true) } }
        return favRepos.size
    }

}

class FakeFavReposDao(repos: List<FavouriteRepoEntity>) : FavouriteRepoDao {

    private var inMemoryRepos = MutableStateFlow(repos)

    override fun getAll(): Flow<List<FavouriteRepoEntity>> {
        return inMemoryRepos
    }

    override suspend fun upsert(repo: FavouriteRepoEntity): Long {
        inMemoryRepos.update { inMemoryRepos.value.filter { it.githubId != repo.githubId } }
        inMemoryRepos.update { inMemoryRepos.value + repo }
        return 1
    }

    override suspend fun delete(githubId: Int): Int {
        inMemoryRepos.update { inMemoryRepos.value.filter { it.githubId != githubId } }
        return 1
    }

}

class FakeMediatorDataSource(private val repos: List<Repository>) : GithubReposMediatorDataSource {

    override fun reposPager(): Flow<PagingData<Repository>> {
        return flowOf(PagingData.from(repos))
    }

}

class FakePagingSource<T : Any>(private val data: List<T>) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val nextKey = if (params is LoadParams.Append) params.key + 1 else null
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

val fakeReposDao = FakeRepoDao(
    repos = listOf(mockRepoEntity, mockRepoEntityFav),
    detailedRepos = listOf(mockRepoUserEntity, mockRepoUserEntityFav)
)
val fakeFavReposDao = FakeFavReposDao(listOf(mockFavouriteRepoEntity))

val fakeRepository = GithubRepository(
    localDataSource = GithubReposRoomLocalDataSourceImpl(fakeReposDao, fakeFavReposDao),
    mediatorDataSource = FakeMediatorDataSource(listOf(buildMockRepository()))
)
