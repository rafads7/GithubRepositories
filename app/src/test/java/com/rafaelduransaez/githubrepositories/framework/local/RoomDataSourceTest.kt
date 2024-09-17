package com.rafaelduransaez.githubrepositories.framework.local

import com.rafaelduransaez.githubrepositories.entities.buildMockRepoDetail
import com.rafaelduransaez.githubrepositories.entities.mockRepoEntity
import com.rafaelduransaez.githubrepositories.entities.mockRepoUserEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.FavouriteRepoDao
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.framework.local.sources.GithubReposRoomLocalDataSourceImpl
import com.rafaelduransaez.githubrepositories.utils.toFavouriteRepo
import com.rafaelduransaez.githubrepositories.utils.toRepositoryDetail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

class RoomDataSourceTest {

    private lateinit var roomDataSource: GithubReposRoomLocalDataSourceImpl
    private val mockRepoDao: ReposDao = mock()
    private val mockFavouriteRepoDao: FavouriteRepoDao = mock()

    @Before
    fun setUp() {
        roomDataSource = GithubReposRoomLocalDataSourceImpl(mockRepoDao, mockFavouriteRepoDao)
    }

    @After
    fun tearDown() = Unit

    @Test
    fun `getRepoDetailById should return RepositoryDetail`() = runBlocking {
        val mockRepoDetail = buildMockRepoDetail()
        val mockRepoUser = mockRepoUserEntity
        whenever(mockRepoDao.getRepoDetail(mockRepoDetail.id)).thenReturn(flowOf(mockRepoUser))

        val result = roomDataSource.getRepoDetailById(mockRepoDetail.id)

        verify(mockRepoDao).getRepoDetail(mockRepoDetail.id)
        assertEquals(result.first(), mockRepoUser.toRepositoryDetail())
    }

    @Test
    fun `getFavouriteRepositories should return RepositoryDetail`() = runBlocking {
        val mockRepoUsers = listOf(mockRepoUserEntity)
        whenever(mockRepoDao.getFavouriteRepos()).thenReturn(flowOf(mockRepoUsers))

        val result = roomDataSource.getFavouriteRepositories()

        verify(mockRepoDao).getFavouriteRepos()
        assertEquals(result.first(), mockRepoUsers.map { it.toRepositoryDetail() })
    }

    @Test
    fun `updateFavRepo should update favorite status`(): Unit = runBlocking {
        // Given
        val repoId = 1
        val fav = true
        val repo = mockRepoEntity.copy(favourite = fav)
        whenever(mockRepoDao.get(repoId)).thenReturn(flowOf(repo))

        // When
        roomDataSource.updateFavRepo(buildMockRepoDetail().copy(favourite = fav))

        // Then
        assertEquals(fav, repo.favourite)
        verify(mockRepoDao).update(repoId, fav)
        if (fav) {
            verify(mockFavouriteRepoDao).upsert(repo.toFavouriteRepo())
        } else {
            verify(mockFavouriteRepoDao).delete(repo.githubId)
        }
    }

    //Not working
    /*@Test
    fun `updateFavRepo should return ErrorDatabase on failed update`() = runBlocking {
        // Given
        val repoId = 1
        val fav = true
        val repo = mockRepoEntity.copy(favourite = !fav)
        whenever(mockRepoDao.get(anyInt())).thenReturn(flowOf(repo))
        whenever(mockRepoDao.update(repoId, fav)).doThrow(RuntimeException())

        // When
        val result = roomDataSource.updateFavRepo(buildMockRepoDetail())

        // Then
        assertEquals(Error.Database, result)
    }*/
}