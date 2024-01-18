package com.rafaelduransaez.githubrepositories.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.di.RepoId
import com.rafaelduransaez.githubrepositories.utils.toError
import com.rafaelduransaez.usecases.GetRepoDetailByIdUseCase
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
    private val getRepoDetailByIdUseCase: GetRepoDetailByIdUseCase
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getRepoDetailByIdUseCase(repoId)
                .catch { t -> _state.update { UiState(error = t.toError()) } }
                .collect { repo -> _state.update { UiState(repo) }}
        }
    }

    data class UiState(val repo: RepositoryDetail? = null, val error: Error? = null)

}