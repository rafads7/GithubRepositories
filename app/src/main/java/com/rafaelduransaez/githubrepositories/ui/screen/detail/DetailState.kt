package com.rafaelduransaez.githubrepositories.ui.screen.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.ui.common.ErrorLayoutView
import com.rafaelduransaez.githubrepositories.ui.toast
import com.rafaelduransaez.githubrepositories.utils.toString


fun Fragment.buildDetailState() = DetailState(requireContext())

class DetailState(
    private val context: Context
) {

    fun navigateToGithub(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun errorToMessage(error: Error?) = error?.toString(context)

    fun manageError(state: RepositoryDetailViewModel.UiState) {
        state.actionError?.let {
            context.toast(context.getString(R.string.str_error_updating_fav), Toast.LENGTH_SHORT)
            context.toast(context.getString(R.string.str_error_reason, it.toString(context)))
        }
    }
}