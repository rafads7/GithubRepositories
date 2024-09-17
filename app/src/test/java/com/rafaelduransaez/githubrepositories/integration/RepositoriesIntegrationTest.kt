package com.rafaelduransaez.githubrepositories.integration

import app.cash.turbine.test
import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.githubrepositories.CoroutinesTestRule
import com.rafaelduransaez.githubrepositories.entities.buildMockRepository
import com.rafaelduransaez.githubrepositories.entities.mockFavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.entities.mockRepoEntity
import com.rafaelduransaez.githubrepositories.entities.mockRepoEntityFav
import com.rafaelduransaez.githubrepositories.entities.mockRepoUserEntity
import com.rafaelduransaez.githubrepositories.entities.mockRepoUserEntityFav
import com.rafaelduransaez.githubrepositories.framework.local.sources.GithubReposRoomLocalDataSourceImpl
import com.rafaelduransaez.githubrepositories.ui.classical.list.RepositoriesViewModel
import com.rafaelduransaez.usecases.GetFavouriteReposUseCase
import com.rafaelduransaez.usecases.GetPagedBestRatedReposUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoriesIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()


    //Do not know why this test is not emitting any values, so it is failing...
    @Test
    fun `paged repos are loaded from remote source`() = runTest {

        val viewModel = buildReposViewModel()

        viewModel.favRepos.test(timeout = Duration.INFINITE) {
            assertEquals(mockRepoUserEntityFav.repo.id, awaitItem().first().id)
            awaitComplete()
        }

    }

    private fun buildReposViewModel(): RepositoriesViewModel {

        val fakeReposDao = FakeRepoDao(
            repos = listOf(mockRepoEntity, mockRepoEntityFav),
            detailedRepos = listOf(mockRepoUserEntity, mockRepoUserEntityFav)
        )
        val fakeFavReposDao = FakeFavReposDao(listOf(mockFavouriteRepoEntity))

        val repository = GithubRepository(
            localDataSource = GithubReposRoomLocalDataSourceImpl(fakeReposDao, fakeFavReposDao),
            mediatorDataSource = FakeMediatorDataSource(listOf(buildMockRepository()))
        )

        val favUseCase = GetFavouriteReposUseCase(repository)
        val pagedUseCase = GetPagedBestRatedReposUseCase(repository)

        return RepositoriesViewModel(
            getPagedBestRatedReposUseCase = pagedUseCase,
            getFavouriteReposUseCase = favUseCase
        )
    }
}