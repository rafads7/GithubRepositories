package com.rafaelduransaez.githubrepositories.di

import androidx.lifecycle.SavedStateHandle
import com.rafaelduransaez.githubrepositories.ui.screen.RepositoryDetailFragmentArgs
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
    fun provideMovieId(savedStateHandle: SavedStateHandle) =
        RepositoryDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).repoId

}