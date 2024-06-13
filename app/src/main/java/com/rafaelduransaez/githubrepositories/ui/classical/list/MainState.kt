package com.rafaelduransaez.githubrepositories.ui.classical.list

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.RepoModel
import com.rafaelduransaez.githubrepositories.utils.toString


fun Fragment.buildMainState() = MainState(requireContext(), findNavController())
class MainState(
    private val context: Context,
    private val navController: NavController
) {
    fun errorToMessage(error: Error) = error.toString(context)

    fun onRepoClicked(it: RepoModel) {
        navController.navigate(RepositoriesFragmentDirections.toDetail(it.id))
    }
}