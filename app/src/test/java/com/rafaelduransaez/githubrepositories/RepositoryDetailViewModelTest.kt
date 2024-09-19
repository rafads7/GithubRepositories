package com.rafaelduransaez.githubrepositories

import app.cash.turbine.test
import com.rafaelduransaez.apptestshared.buildMockRepoDetail
import com.rafaelduransaez.githubrepositories.ui.classical.detail.RepositoryDetailViewModel
import com.rafaelduransaez.githubrepositories.ui.classical.detail.RepositoryDetailViewModel.UiState
import com.rafaelduransaez.usecases.GetRepoDetailByIdUseCase
import com.rafaelduransaez.usecases.UpdateFavReposUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailViewModelTest {

    private var getRepoDetailByIdUseCase: GetRepoDetailByIdUseCase = mock()
    private var updateFavReposUseCase: UpdateFavReposUseCase = mock()

    private lateinit var viewModel: RepositoryDetailViewModel

    private val mockRepoDetail = buildMockRepoDetail()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        whenever(getRepoDetailByIdUseCase(anyInt())).doReturn(flowOf(mockRepoDetail))

        viewModel =
            RepositoryDetailViewModel(REPO_ID, getRepoDetailByIdUseCase, updateFavReposUseCase)
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
        // Given
        whenever(getRepoDetailByIdUseCase(anyInt())).doReturn(flowOf(mockRepoDetail))

        // When
        viewModel.onRetryClicked()

        // Then
        viewModel.state.test {
            assertEquals(UiState(mockRepoDetail, null, null), awaitItem())
            cancel()
        }
        verify(getRepoDetailByIdUseCase).invoke(anyInt())

    }


    @Test
    fun `onFavButtonClicked should update repo to favourite`() = runTest {
        // Given
        whenever(updateFavReposUseCase(mock())).doReturn(null)

        // When
        viewModel.onFavButtonClicked()

        // Then
        viewModel.state.test {
            assertEquals(null, awaitItem().actionError)
            cancel()
        }
    }


    companion object {
        private const val REPO_ID = 1
    }
}