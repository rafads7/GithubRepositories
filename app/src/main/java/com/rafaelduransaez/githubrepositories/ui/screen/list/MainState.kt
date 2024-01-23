package com.rafaelduransaez.githubrepositories.ui.screen.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.utils.toString


fun Fragment.buildMainState() = MainState(requireContext(), findNavController())
class MainState(
    private val context: Context,
    private val navController: NavController
) {

    val colors = context.resources.getIntArray(R.array.random_colors)

    fun errorToMessage(error: Error) = error.toString(context)

    fun onRepoClicked(it: Repository) {
        navController.navigate(RepositoriesFragmentDirections.toDetail(it.id))
    }
}