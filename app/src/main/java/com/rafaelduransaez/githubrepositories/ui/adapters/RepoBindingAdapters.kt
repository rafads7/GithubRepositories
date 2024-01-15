package com.rafaelduransaez.githubrepositories.ui.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
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

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}
