package com.rafaelduransaez.githubrepositories.ui.compose.ui.components
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.RepoDetailScreen
import com.rafaelduransaez.githubrepositories.ui.compose.ui.screens.ReposListScreen
import com.rafaelduransaez.githubrepositories.ui.compose.utils.NavArgs
import com.rafaelduransaez.githubrepositories.ui.compose.utils.Routes

@Composable
fun NavComponent(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Routes.ReposListScreen.route) {
        composable(Routes.ReposListScreen.route) { ReposListScreen(navController) }
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
}