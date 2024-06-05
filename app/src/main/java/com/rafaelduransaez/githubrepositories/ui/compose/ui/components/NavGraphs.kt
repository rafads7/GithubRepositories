package com.rafaelduransaez.githubrepositories.ui.compose.ui.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rafaelduransaez.domain.RepoModel
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.FavouritesReposScreen
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.RepoDetailScreen
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.ReposListScreen
import com.rafaelduransaez.githubrepositories.ui.compose.utils.NavArgs
import com.rafaelduransaez.githubrepositories.ui.compose.utils.Routes
import com.rafaelduransaez.githubrepositories.ui.classical.list.RepositoriesViewModel

@Composable
fun AppNavGraph(
    appNavController: NavHostController = rememberNavController(),
    reposViewModel: RepositoriesViewModel = hiltViewModel()

) {
    val repos = reposViewModel.bestRatedRepos.collectAsLazyPagingItems()

    NavHost(
        navController = appNavController,
        startDestination = Routes.ReposListScreen.route
    ) {

        composable(Routes.ReposListScreen.route) {
            RepositoriesScaffold(repos = repos) { route ->
                appNavController.navigate(route)
            }
        }

        composable(
            Routes.RepoDetailScreen.route,
            arguments = listOf(
                navArgument(NavArgs.RepoId.key) {
                    defaultValue = -1
                    NavType.IntType
                }
            )
        ) {
            RepoDetailScreen(
                actions = object : RepoDetailScreenNavActions {
                    override val onBackClicked: () -> Unit
                        get() = { appNavController.popBackStack() }
                }
            )
        }
    }
}


@Composable
fun RepositoriesNavGraph(
    bottomNavBarController: NavHostController,
    repos: LazyPagingItems<RepoModel>,
    minimizedGrid: Boolean = false,
    onAppNavigateTo: (String) -> Unit
) {

    NavHost(
        navController = bottomNavBarController,
        startDestination = Routes.ReposListScreen.route
    ) {
        composable(Routes.ReposListScreen.route) {
            ReposListScreen(repos) { repoId ->
                onAppNavigateTo(Routes.RepoDetailScreen.createRoute(repoId))
            }
        }
        composable(Routes.FavouritesReposScreen.route) {
            FavouritesReposScreen(minimizedGrid) { repoId ->
                onAppNavigateTo(Routes.RepoDetailScreen.createRoute(repoId))
            }
        }
    }
}


interface RepoDetailScreenNavActions {

    val onBackClicked: () -> Unit
}
