package com.rafaelduransaez.githubrepositories.ui.adapters

import androidx.databinding.BindingAdapter
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.ui.screen.RepositoryDetailedView
import com.rafaelduransaez.githubrepositories.ui.screen.RepositorySimpleView

@BindingAdapter("repository")
fun RepositorySimpleView.setRepo(repository: Repository?) {
    if (repository != null) {
        setRepo(repository)
    }
}

@BindingAdapter("repository")
fun RepositoryDetailedView.setRepo(repository: Repository?) {
    if (repository != null) {
        setRepo(repository)
    }
}