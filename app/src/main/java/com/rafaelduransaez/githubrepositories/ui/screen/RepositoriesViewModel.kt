package com.rafaelduransaez.githubrepositories.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.usecases.GetBestRatedReposUseCase
import com.rafaelduransaez.usecases.GetPagedBestRatedReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val getPagedBestRatedReposUseCase: GetPagedBestRatedReposUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<UiState> = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState(loading = true)
            getPagedBestRatedReposUseCase().cachedIn(viewModelScope)
                .catch { _state.update {
                    it.copy(error = true, loading = false) }
                }
                .collect { repos ->
                    _state.update { it.copy(dataSource = repos,
                        loading = false, error = false) }
                }
        }
    }
}

data class UiState(
    var loading: Boolean = false,
    var error: Boolean = false,
    val dataSource: PagingData<Repository> = PagingData.empty(),
)
