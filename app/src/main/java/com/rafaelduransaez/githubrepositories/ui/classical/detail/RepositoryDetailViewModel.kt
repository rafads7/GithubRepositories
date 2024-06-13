package com.rafaelduransaez.githubrepositories.ui.classical.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelduransaez.domain.models.Error
import com.rafaelduransaez.domain.models.RepoDetailModel
import com.rafaelduransaez.githubrepositories.di.RepoId
import com.rafaelduransaez.githubrepositories.utils.toError
import com.rafaelduransaez.usecases.GetRepoDetailByIdUseCase
import com.rafaelduransaez.usecases.SaveFavRepositoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    @RepoId private val repoId: Int,
    private val getRepoDetailByIdUseCase: GetRepoDetailByIdUseCase,
    private val updateFavReposUseCase: SaveFavRepositoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getRepository()
    }

    private fun getRepository() {
        viewModelScope.launch {
            getRepoDetailByIdUseCase(repoId)
                .catch { t -> _state.update { it.copy(error = t.toError()) } }
                .collect { repo -> _state.update { UiState(repo = repo) } }
        }
    }

    fun onFavButtonClicked() {
        _state.value.repo?.let { repo ->
            viewModelScope.launch {
                val error = updateFavReposUseCase(repo.copy(favourite = !repo.favourite))
                _state.update { state -> state.copy(actionError = error) }
            }
        }
    }

    fun onRetryClicked() {
        getRepository()
    }

    data class UiState(
        val repo: RepoDetailModel? = null,
        val error: Error? = null,
        val actionError: Error? = null
    )
}
