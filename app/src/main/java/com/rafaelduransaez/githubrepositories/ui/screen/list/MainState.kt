package com.rafaelduransaez.githubrepositories.ui.screen.list

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.R


fun Fragment.buildMainState() = MainState(requireContext(), findNavController())
class MainState(
    private val context: Context,
    private val navController: NavController
) {

    val colors = context.resources.getIntArray(R.array.random_colors)

    fun errorToMessage(error: Error) = when (error) {
        is Error.Connection -> context.getString(R.string.connection_error)
        is Error.Server -> context.getString(R.string.server_error, error.code)
        is Error.Unknown -> context.getString(R.string.unknown_error, error.message)
    }

    fun onRepoClicked(it: Repository) {
        navController.navigate(RepositoriesFragmentDirections.toDetail(it.id))
    }
}