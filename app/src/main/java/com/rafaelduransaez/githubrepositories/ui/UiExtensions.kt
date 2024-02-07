package com.rafaelduransaez.githubrepositories.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.MAX_CHAR
import com.rafaelduransaez.githubrepositories.utils.truncate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

fun Context.toast(message: String, lenght: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, lenght).show()
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

fun <T: Any> LazyPagingItems<T>.isEmpty() = itemCount == 0

fun Repository.toAnnotatedString(maxChar: Int = MAX_CHAR) = buildAnnotatedString {
        append("ID: ")
        appendLine(id.toString())

        append("Name: ")
        appendLine(name)

        append("Description: ")
        appendLine(description.truncate(maxChar))

        //withStyle(style = MaterialTheme.typography.h6.toSpanStyle()) {
        append("Number of stars: ")
        //}
        append(starsCount.toString())
    }