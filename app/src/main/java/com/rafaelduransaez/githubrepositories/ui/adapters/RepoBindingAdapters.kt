package com.rafaelduransaez.githubrepositories.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.ui.loadUrl
import com.rafaelduransaez.githubrepositories.ui.screen.RepositoryDetailedView
import com.rafaelduransaez.githubrepositories.ui.screen.RepositorySimpleView


@BindingAdapter("repository")
fun RepositorySimpleView.setRepo(repository: Repository?) {
    if (repository != null) {
        setRepo(repository)
    }
}

@BindingAdapter("repository")
fun RepositoryDetailedView.setRepo(repository: RepositoryDetail?) {
    if (repository != null) {
        setRepo(repository)
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("randomBackgroundColor")
fun RepositorySimpleView.setRandomBackgroundColor(colorArray: IntArray?) {
    colorArray?.random()?.let {
        setBackgroundColor(it)
    }
}
