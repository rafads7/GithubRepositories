package com.rafaelduransaez.githubrepositories.ui.common

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.ui.loadUrl
import com.rafaelduransaez.githubrepositories.ui.screen.detail.RepositoryDetailedView
import com.rafaelduransaez.githubrepositories.ui.screen.list.RepositorySimpleView


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

@BindingAdapter("error_message")
fun setError(view: ErrorLayoutView, errorMessage: String?) {
    errorMessage?.let {
        view.errorMessage = it
    }
}

@BindingAdapter("app:imageUrl", "app:collapsingToolbarLayout")
fun setDynamicTitleColor(
    imageView: ImageView,
    imageUrl: String?,
    collapsingToolbarLayout: CollapsingToolbarLayout
) {
    if (imageUrl != null) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(imageUrl)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        Palette.from(it).generate { palette ->
                            val titleColor = palette?.dominantSwatch?.titleTextColor ?: Color.WHITE
                            collapsingToolbarLayout.setExpandedTitleColor(titleColor)
                        }
                    }
                    return false                    }

            })
            .into(imageView)
    }
}

@BindingAdapter("app:onClick")
fun setClickListener(view: View, clickListener: View.OnClickListener?) {
    view.setOnClickListener(clickListener)
}


@BindingAdapter("randomBackgroundColor")
fun RepositorySimpleView.setRandomBackgroundColor(colorArray: IntArray?) {
    colorArray?.random()?.let {
        setBackgroundColor(it)
    }
}
