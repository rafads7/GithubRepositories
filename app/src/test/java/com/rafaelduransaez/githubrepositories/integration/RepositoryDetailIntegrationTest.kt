package com.rafaelduransaez.githubrepositories.integration

import app.cash.turbine.test
import com.rafaelduransaez.githubrepositories.CoroutinesTestRule
import com.rafaelduransaez.githubrepositories.entities.mockRepoDetail
import com.rafaelduransaez.githubrepositories.entities.mockRepoEntity
import com.rafaelduransaez.githubrepositories.ui.classical.detail.RepositoryDetailViewModel
import com.rafaelduransaez.usecases.GetRepoDetailByIdUseCase
import com.rafaelduransaez.usecases.UpdateFavReposUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: RepositoryDetailViewModel

    @Before
    fun setUp() {
        viewModel = buildRepoDetailViewModel()
    }

    @Test
    fun `onFavButtonClicked should update repo to favourite`() = runTest {
        // When
        viewModel.onFavButtonClicked()

        // Then
        viewModel.state.test {
            assertEquals(null, awaitItem().actionError)
            cancel()
        }
    }

    @Test
    fun `getRepository should update state with repo detail`() = runTest {
        // When init{...}
        // Then
        val state = viewModel.state.first()
        assertEquals(mockRepoDetail, state.repo)
        assertEquals(null, state.error)
        assertEquals(null, state.actionError)
    }

    @Test
    fun `onRetryClicked should call getRepoDetailByIdUseCase`() = runTest {

        // When
        viewModel.onRetryClicked()

        // Then
        viewModel.state.test {
            assertEquals(RepositoryDetailViewModel.UiState(mockRepoDetail, null, null), awaitItem())
            cancel()
        }
    }

    private fun buildRepoDetailViewModel(): RepositoryDetailViewModel {

        val getRepoDetailByIdUseCase = GetRepoDetailByIdUseCase(fakeRepository)
        val updateFavReposUseCase = UpdateFavReposUseCase(fakeRepository)

        return RepositoryDetailViewModel(
            repoId = mockRepoEntity.id,
            getRepoDetailByIdUseCase = getRepoDetailByIdUseCase,
            updateFavReposUseCase = updateFavReposUseCase
        )
    }
}