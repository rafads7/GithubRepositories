package com.rafaelduransaez.githubrepositories.ui.classical.detail

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rafaelduransaez.domain.models.Error
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.ui.common.navigateTo
import com.rafaelduransaez.githubrepositories.ui.common.toast
import com.rafaelduransaez.githubrepositories.utils.toString

fun Fragment.buildDetailState() = DetailState(requireContext())

class DetailState(
    private val context: Context
) {

    fun navigateToGithub(url: String) = context.navigateTo(url)

    fun errorToMessage(error: Error?) = error?.toString(context)

    fun handleError(state: RepositoryDetailViewModel.UiState) {
        state.actionError?.let {
            context.toast(context.getString(R.string.str_error_updating_fav), Toast.LENGTH_SHORT)
            context.toast(context.getString(R.string.str_error_reason, it.toString(context)))
        }
    }
}