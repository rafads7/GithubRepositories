@file:Suppress("TooManyFunctions")
package com.rafaelduransaez.githubrepositories.ui.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.rafaelduransaez.domain.models.Error
import com.rafaelduransaez.domain.models.RepoModel
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.Property
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.MAX_CHAR
import com.rafaelduransaez.githubrepositories.utils.toError
import com.rafaelduransaez.githubrepositories.utils.truncate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun Context.navigateTo(url: String): Error? =
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        null
    } catch (e: ActivityNotFoundException) {
        e.toError()
    }


fun Fragment.toast(message: String, lenght: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), message, lenght).show()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun <T : Any> basicDiffUtil(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}

fun LifecycleOwner.launchWhenStarted(
    block: () -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}

fun <T : Any> LazyPagingItems<T>.isEmpty() = itemCount == 0

@Composable
fun RepoModel.toComposableAnnotatedString(maxChar: Int = MAX_CHAR) = buildAnnotatedString {
    Property(name = "Name", value = name)
    Property(name = "Description", value = description.truncate(maxChar))
    Property(name = "Stars count", value = starsCount.toString())
}

fun RepoModel.toAnnotatedString(maxChar: Int = MAX_CHAR) = buildAnnotatedString {

    append("Name: ")
    appendLine(name)

    append("Description: ")
    appendLine(description.truncate(maxChar))

    append("Number of stars: ")
    append(starsCount.toString())
}