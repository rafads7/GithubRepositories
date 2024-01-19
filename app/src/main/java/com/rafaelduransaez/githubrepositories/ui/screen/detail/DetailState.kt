package com.rafaelduransaez.githubrepositories.ui.screen.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment


fun Fragment.buildDetailState() = DetailState(requireContext())

class DetailState(
    private val context: Context
) {

    fun navigateToGithub(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}