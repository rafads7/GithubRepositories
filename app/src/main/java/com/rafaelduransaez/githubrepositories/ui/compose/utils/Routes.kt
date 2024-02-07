package com.rafaelduransaez.githubrepositories.ui.compose.utils

sealed class Routes(val route: String) {
    private companion object {
        const val REPOS_LIST = "repos_list"
        const val REPO_DETAIL = "repo_detail"
    }

    data object ReposListScreen: Routes(REPOS_LIST)
    data object RepoDetailScreen: Routes("$REPO_DETAIL/{${NavArgs.RepoId.key}}") { //"repo_detail/{id}"
        fun createRoute(id: Int) = "$REPO_DETAIL/$id"
    }

}

sealed class NavArgs(val key: String) {
    private companion object {
        const val REPO_ID = "id"
    }

    data object RepoId: NavArgs(REPO_ID)
}