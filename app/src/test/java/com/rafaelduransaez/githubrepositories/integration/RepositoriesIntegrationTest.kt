package com.rafaelduransaez.githubrepositories.integration

import app.cash.turbine.test
import com.rafaelduransaez.apptestshared.FakeFavReposDao
import com.rafaelduransaez.apptestshared.FakeMediatorDataSource
import com.rafaelduransaez.apptestshared.FakeRepoDao
import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.githubrepositories.CoroutinesTestRule
import com.rafaelduransaez.apptestshared.buildMockRepository
import com.rafaelduransaez.apptestshared.fakeRepository
import com.rafaelduransaez.apptestshared.mockFavouriteRepoEntity
import com.rafaelduransaez.apptestshared.mockRepoEntity
import com.rafaelduransaez.apptestshared.mockRepoEntityFav
import com.rafaelduransaez.apptestshared.mockRepoUserEntity
import com.rafaelduransaez.apptestshared.mockRepoUserEntityFav
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

    @Test
    fun `paged repos are loaded from remote source`() = runTest {

        //
        //Do not know why this test is not emitting any values, so it is failing...
        // Same error than in Instrumentation test: kotlinx.coroutines.test.UncompletedCoroutinesError:
        // After waiting for 10s, the test coroutine is not completing, there were active child jobs:
        // [ScopeCoroutine{Active}@360356a]
        //

        val viewModel = buildReposViewModel()

        viewModel.favRepos.test(timeout = Duration.INFINITE) {
            assertEquals(mockRepoUserEntityFav.repo.id, awaitItem().first().id)
            awaitComplete()
        }

    }

    private fun buildReposViewModel(): RepositoriesViewModel {

        val favUseCase = GetFavouriteReposUseCase(fakeRepository)
        val pagedUseCase = GetPagedBestRatedReposUseCase(fakeRepository)

        return RepositoriesViewModel(
            getPagedBestRatedReposUseCase = pagedUseCase,
            getFavouriteReposUseCase = favUseCase
        )
    }
}