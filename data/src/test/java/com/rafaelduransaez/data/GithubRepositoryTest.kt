package com.rafaelduransaez.data

import androidx.paging.PagingData
import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.data.mockEntities.buildMockRepo
import com.rafaelduransaez.data.mockEntities.buildMockRepoDetail
import com.rafaelduransaez.data.mockEntities.buildMockUserDetail
import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.domain.UserDetail
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class GithubRepositoryTest {

    private val repoLocalDataSource: GithubReposLocalDataSource = mock()
    private val repoMediatorDataSource: GithubReposMediatorDataSource = mock()
    private val pagedRepos: PagingData<Repository> = mock()

    private lateinit var repo: GithubRepository
    private lateinit var mockRepoDetail: RepositoryDetail
    private lateinit var mockUserDetail: UserDetail

    @Before
    fun setUp() {
        repo = buildMockRepo(repoLocalDataSource, repoMediatorDataSource)
        mockRepoDetail = buildMockRepoDetail()
        mockUserDetail = buildMockUserDetail()
    }

    @After
    fun tearDown() = Unit

    @Test
    fun `when getRepoDetailById is called, local data source is called`() = runTest {
        val repoDetail =  flowOf(mockRepoDetail)
        whenever(repoLocalDataSource.getRepoDetailById(mockRepoDetail.id)).doReturn(repoDetail)

        val result = repo.getRepoDetailById(mockRepoDetail.id)

        verify(repoLocalDataSource).getRepoDetailById(mockRepoDetail.id)
        assertEquals(repoDetail, result)
    }

    @Test
    fun `when getFavouriteRepositories is called, local data source is called`() = runTest {
        val favRepos =  flowOf(listOf(mockRepoDetail))
        whenever(repoLocalDataSource.getFavouriteRepositories()).doReturn(favRepos)

        val result = repo.getFavouriteRepositories()

        verify(repoLocalDataSource).getFavouriteRepositories()
        assertEquals(favRepos, result)
    }

    @Test
    fun `when getPagedBestRatedRepositories is called, local data source is called`() = runTest {
        val repos =  flowOf(pagedRepos)
        whenever(repoMediatorDataSource.reposPager()).doReturn(repos)

        val result = repo.getPagedBestRatedRepositories()

        verify(repoMediatorDataSource).reposPager()
        assertEquals(repos, result)
    }

    @Test
    fun `when updateFavRepo is called, local data source is called`() = runTest {
        whenever(repoLocalDataSource.updateFavRepo(mockRepoDetail)).doReturn(null)

        val result = repo.updateFavRepo(mockRepoDetail)

        verify(repoLocalDataSource).updateFavRepo(mockRepoDetail)
        assertEquals(null, result)
    }

    @Test
    fun `when updateFavRepo fails, returns Error`() = runTest {
        whenever(repoLocalDataSource.updateFavRepo(mockRepoDetail)).doReturn(Error.Database)

        val result = repo.updateFavRepo(mockRepoDetail)

        verify(repoLocalDataSource).updateFavRepo(mockRepoDetail)
        assertEquals(result, Error.Database)
    }
}