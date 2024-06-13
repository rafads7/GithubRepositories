package com.rafaelduransaez.githubrepositories.ui.compose.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.rafaelduransaez.domain.models.RepoModel
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.ui.compose.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RepositoriesScaffold(
    bottomNavBarController: NavHostController = rememberNavController(),
    repos: LazyPagingItems<RepoModel>,
    onAppNavigateTo: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val backStackEntryState by bottomNavBarController.currentBackStackEntryAsState()
    var minimizeGrid by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopReposAppBar(scrollBehavior = scrollBehavior,
                backStackEntryState = backStackEntryState,
                minimizedGrid = minimizeGrid) {
                minimizeGrid = !minimizeGrid
            }
        },
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
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        ) {
            RepositoriesNavGraph(
                bottomNavBarController = bottomNavBarController,
                onAppNavigateTo = onAppNavigateTo,
                minimizedGrid = minimizeGrid,
                repos = repos
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopReposAppBar(
    backStackEntryState: NavBackStackEntry?,
    minimizedGrid: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior,
    onChangeGridSizeButtonClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.str_best_rated_repos)) },
        actions = {
            backStackEntryState?.let {
                if (it.destination.route == Routes.FavouritesReposScreen.route)
                    IconButton(onClick = { onChangeGridSizeButtonClicked() }) {
                        IconComponent(
                            imageVector =
                            if (minimizedGrid) Icons.Default.ListAlt
                            else Icons.Default.GridView
                        )
                    }
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry?,
    navDestinations: List<Routes> = listOfNavDestinations(),
    onBottomNavItemClicked: (String) -> Unit
) {

    NavigationBar(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {

        val currentDestination = navBackStackEntry?.destination

        navDestinations.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = stringResource(id = screen.navLabelResourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    onBottomNavItemClicked(screen.route)
                },
                icon = { screen.icon?.let { IconComponent(imageVector = it) } }
            )
        }
    }
}

fun listOfNavDestinations() = listOf(
    Routes.ReposListScreen,
    Routes.FavouritesReposScreen
)

