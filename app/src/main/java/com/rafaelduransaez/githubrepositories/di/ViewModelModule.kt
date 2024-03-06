package com.rafaelduransaez.githubrepositories.di

import androidx.lifecycle.SavedStateHandle
import com.rafaelduransaez.githubrepositories.ui.compose.utils.NavArgs
import com.rafaelduransaez.githubrepositories.ui.screen.detail.RepositoryDetailFragmentArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    @RepoId
    fun provideRepoId(savedStateHandle: SavedStateHandle): Int {

        val repoId = RepositoryDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).repoId //nav component
        return if (repoId > -1) repoId
        else savedStateHandle[NavArgs.RepoId.key] ?: -1 //compose nav
    }

}

