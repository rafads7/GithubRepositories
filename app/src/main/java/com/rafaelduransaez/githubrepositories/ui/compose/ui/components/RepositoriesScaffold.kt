package com.rafaelduransaez.githubrepositories.ui.compose.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.ui.compose.utils.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RepositoriesScaffold(
    bottomNavBarController: NavHostController = rememberNavController(),
    repos: LazyPagingItems<Repository>,
    onAppNavigateTo: (String) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val backStackEntryState by bottomNavBarController.currentBackStackEntryAsState()

    Scaffold(
        topBar = { TopReposAppBar() },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        bottomBar = {
            BottomNavigationBar(navBackStackEntry = backStackEntryState) { route ->
                bottomNavBarController.navigate(route) {
                    popUpTo(bottomNavBarController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            RepositoriesNavGraph(
                bottomNavBarController = bottomNavBarController,
                onAppNavigateTo = onAppNavigateTo,
                repos = repos
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopReposAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_best_rated_repos)) })
}


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry?,
    navDestinations: List<Routes> = listOfNavDestinations(),
    onBottomNavItemClicked: (String) -> Unit
) {

    NavigationBar(
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    ) {

        val currentDestination = navBackStackEntry?.destination

        navDestinations.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = stringResource(id = screen.navLabelResourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    onBottomNavItemClicked(screen.route)
                },
                icon = { screen.icon?.let { IconComponent(imageVector = it) } })
        }
    }
}

fun listOfNavDestinations() = listOf(
    Routes.ReposListScreen,
    Routes.FavouritesReposScreen
)

