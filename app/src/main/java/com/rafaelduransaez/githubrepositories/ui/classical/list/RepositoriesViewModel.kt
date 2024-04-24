package com.rafaelduransaez.githubrepositories.ui.classical.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rafaelduransaez.usecases.GetFavouriteReposUseCase
import com.rafaelduransaez.usecases.GetPagedBestRatedReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    getPagedBestRatedReposUseCase: GetPagedBestRatedReposUseCase,
    getFavouriteReposUseCase: GetFavouriteReposUseCase
) : ViewModel() {

    val bestRatedRepos = getPagedBestRatedReposUseCase()
        .cachedIn(viewModelScope)

    val favRepos = getFavouriteReposUseCase()
}
