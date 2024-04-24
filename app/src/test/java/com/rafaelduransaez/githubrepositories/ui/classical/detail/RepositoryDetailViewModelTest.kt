package com.rafaelduransaez.githubrepositories.ui.classical.detail

import com.rafaelduransaez.domain.Error.*
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.CoroutinesTestRule
import com.rafaelduransaez.usecases.GetRepoDetailByIdUseCase
import com.rafaelduransaez.usecases.UpdateFavReposUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailViewModelTest {

    private var getRepoDetailByIdUseCase: GetRepoDetailByIdUseCase = mock()
    private var updateFavReposUseCase: UpdateFavReposUseCase = mock()

    private lateinit var viewModel: RepositoryDetailViewModel

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

/*    @Before
    fun setup() {
        viewModel = RepositoryDetailViewModel(1, getRepoDetailByIdUseCase, updateFavReposUseCase)
    }
    
    @Test
    fun `getRepository should update state with repo detail`() = testScope.runBlockingTest {
        // Given
        val repoDetail = RepositoryDetail(id = 1, name = "Test Repo", favourite = false)
        whenever(getRepoDetailByIdUseCase(1)).thenReturn(flowOf(repoDetail))

        // When
        viewModel.getRepository()

        // Then
        val state = viewModel.state.first()
        assertEquals(repoDetail, state.repo)
        assertEquals(null, state.error)
        assertEquals(null, state.actionError)
    }

    @Test
    fun `getRepository should update state with error`() = testScope.runBlockingTest {
        // Given
        whenever(getRepoDetailByIdUseCase(1)).thenReturn(flowOf(null).catch { throw it })

        // When
        viewModel.getRepository()

        // Then
        val state = viewModel.state.first()
        assertEquals(null, state.repo)
        assertEquals(Database, state.error)
        assertEquals(null, state.actionError)
    }

    @Test
    fun `onFavButtonClicked should update state with action error`() = testScope.runBlockingTest {
        // Given
        val repoDetail = RepositoryDetail(id = 1, name = "Test Repo", favourite = false)
        viewModel.state.value = RepositoryDetailViewModel.UiState(repo = repoDetail)
        whenever(updateFavReposUseCase(repoDetail.copy(favourite = true))).thenReturn(Error.Database)

        // When
        viewModel.onFavButtonClicked()

        // Then
        val state = viewModel.state.first()
        assertEquals(repoDetail, state.repo)
        assertEquals(null, state.error)
        assertEquals(Database, state.actionError)
    }*/
}