package com.rafaelduransaez.githubrepositories.ui.classical.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.usecases.GetFavouriteReposUseCase
import com.rafaelduransaez.usecases.GetPagedBestRatedReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    getPagedBestRatedReposUseCase: GetPagedBestRatedReposUseCase,
    getFavouriteReposUseCase: GetFavouriteReposUseCase
) : ViewModel() {

    val bestRatedRepos: Flow<PagingData<Repository>> = getPagedBestRatedReposUseCase()
        .cachedIn(viewModelScope)

    val favRepos: Flow<List<RepositoryDetail>> = getFavouriteReposUseCase()
}
