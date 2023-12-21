package com.rafaelduransaez.githubrepositories.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.usecases.GetBestRatedReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val getBestRatedReposUseCase: GetBestRatedReposUseCase
): ViewModel() {

    private var _state: MutableStateFlow<UiState> = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getBestRatedRepositories()
    }

    private fun getBestRatedRepositories() {
        viewModelScope.launch(Dispatchers.IO) {
            val repositories = getBestRatedReposUseCase()
            _state.value = UiState(
                loading = false, repositories = repositories
            )
        }
    }
}

data class UiState(
    var loading: Boolean = false,
    var error: Boolean = false,
    var repositories: List<Repository> = emptyList()
)
