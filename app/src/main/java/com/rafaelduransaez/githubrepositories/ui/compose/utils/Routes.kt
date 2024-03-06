package com.rafaelduransaez.githubrepositories.ui.compose.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.githubrepositories.R

sealed class Routes(
    val route: String,
    @StringRes val navLabelResourceId: Int,
    val icon: ImageVector? = null
) {
    private companion object {
        const val REPOS_LIST = "repos_list"
        const val REPO_DETAIL = "repo_detail"
        const val FAV_LIST = "fav_repos"
    }

    data object ReposListScreen :
        Routes(REPOS_LIST, R.string.str_all, Icons.Default.List)

    data object RepoDetailScreen :
        Routes(
            "$REPO_DETAIL/{${NavArgs.RepoId.key}}",
            R.string.str_repo_detail_title
        ) {
        fun createRoute(id: Int) = "$REPO_DETAIL/$id" //"repo_detail/{id}"
    }

    data object FavouritesReposScreen :
        Routes(FAV_LIST, R.string.str_favourites, Icons.Default.Favorite)

}

sealed class NavArgs(val key: String) {
    private companion object {
        const val REPO_ID = "id"
    }

    data object RepoId : NavArgs(REPO_ID)
}