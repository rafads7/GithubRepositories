package com.rafaelduransaez.githubrepositories.ui.compose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.FavouritesReposScreen
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.RepoDetailScreen
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.ReposListScreen
import com.rafaelduransaez.githubrepositories.ui.compose.utils.NavArgs
import com.rafaelduransaez.githubrepositories.ui.compose.utils.Routes
import com.rafaelduransaez.githubrepositories.ui.navigateTo
import com.rafaelduransaez.githubrepositories.ui.screen.list.RepositoriesViewModel

@Composable
fun AppNavGraph(
    appNavController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val viewModel: RepositoriesViewModel = hiltViewModel()
    val repos = viewModel.bestRatedRepos.collectAsLazyPagingItems()

    NavHost(
        navController = appNavController,
        startDestination = "main"
    ) {

        composable("main") {
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
                    override val onOpenRepoInBrowserButtonClicked: (String) -> Unit
                        get() = { context.navigateTo(it) }

                }
            )
        }
    }
}


@Composable
fun RepositoriesNavGraph(
    bottomNavBarController: NavHostController,
    repos: LazyPagingItems<Repository>,
    onAppNavigateTo: (String) -> Unit
) {

    NavHost(
        navController = bottomNavBarController,
        startDestination = Routes.ReposListScreen.route
    ) {
        composable(Routes.ReposListScreen.route) {
            ReposListScreen(repos) { repoId ->
                onAppNavigateTo(Routes.RepoDetailScreen.createRoute(repoId))
                //bottomNavBarController.navigate(Routes.RepoDetailScreen.createRoute(repoId))
            }
        }
        composable(Routes.FavouritesReposScreen.route) {
            FavouritesReposScreen() { repoId ->
                onAppNavigateTo(Routes.RepoDetailScreen.createRoute(repoId))
                //bottomNavBarController.navigate(Routes.RepoDetailScreen.createRoute(repoId))
            }
        }

        /*composable(
            Routes.RepoDetailScreen.route,
            arguments = listOf(
                navArgument(NavArgs.RepoId.key) {
                    defaultValue = -1
                    NavType.IntType
                }
            )
        ) {
            RepoDetailScreen(navController)
        }*/
    }
}


/*
@Composable
fun MainNavGraph(
    navController: NavHostController,
    viewModel: RepositoriesViewModel = hiltViewModel()
) {
    val repos = viewModel.bestRatedRepos.collectAsLazyPagingItems()

    NavHost(navController = navController, startDestination = Routes.ReposListScreen.route) {
        composable(Routes.ReposListScreen.route) { ReposListScreen(navController, repos) }
        composable(Routes.FavouritesReposScreen.route) { FavouritesReposScreen(navController) }
        composable(
            Routes.RepoDetailScreen.route,
            arguments = listOf(
                navArgument(NavArgs.RepoId.key) {
                    defaultValue = -1
                    NavType.IntType
                }
            )
        ) {
            RepoDetailScreen(navController)
        }
    }
}*/

interface RepoDetailScreenNavActions {

    val onBackClicked: () -> Unit
    val onOpenRepoInBrowserButtonClicked: (String) -> Unit
}
