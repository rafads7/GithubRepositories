package com.rafaelduransaez.githubrepositories.ui.compose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.ErrorMessage
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.LoadingNextPageItem
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.PageLoader
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.RepoItem
import com.rafaelduransaez.githubrepositories.ui.compose.utils.Routes
import com.rafaelduransaez.githubrepositories.ui.isEmpty

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReposListScreen(
    repos: LazyPagingItems<Repository>,
    onRepoClicked: (Int) -> Unit
) {
    LazyColumn {
        handleRefreshState(repos)
        listRepos(repos, onRepoClicked)
        handleAppendState(repos)

        //applyRepos(repos, navController)

        item { Spacer(modifier = Modifier.padding(4.dp)) }
    }
}


private fun LazyListScope.listRepos(
    repos: LazyPagingItems<Repository>,
    onRepoClicked: (Int) -> Unit
) {
    items(count = repos.itemCount, key = repos.itemKey { it.id }) { index ->
        repos[index]?.let { repo ->
            RepoItem(
                repo,
                onRepoClicked = { onRepoClicked(repo.id) }
            )
        }
    }
}

private fun LazyListScope.handleAppendState(repos: LazyPagingItems<Repository>) {
    when (val appendState = repos.loadState.append) {
        is LoadState.Loading -> {
            item { LoadingNextPageItem(modifier = Modifier.fillMaxWidth()) }
        }

        is LoadState.Error -> {
            item {
                ErrorMessage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    message = appendState.error.localizedMessage.orEmpty(),
                    onClickRetry = { repos.retry() }
                )
            }
        }

        else -> {}
    }
}

private fun LazyListScope.handleRefreshState(repos: LazyPagingItems<Repository>) {
    when (val refreshState = repos.loadState.refresh) {
        is LoadState.Loading -> {
            item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
        }

        is LoadState.Error -> {
            item {
                val modifier = if (repos.isEmpty())
                    Modifier.fillParentMaxSize()
                else Modifier.fillMaxWidth()
                ErrorMessage(
                    modifier = modifier,
                    message = refreshState.error.localizedMessage.orEmpty(),
                    onClickRetry = { repos.retry() }
                )
            }
        }

        else -> {}
        /*is LoadState.NotLoading -> {
            if (repos.isEmpty()) {
                item {
                    ErrorMessage(
                        modifier = Modifier.fillParentMaxSize(),
                        message = stringResource(id = R.string.str_no_results),
                        onClickRetry = { repos.retry() }
                    )
                }
            }
        }*/
    }
}

private fun LazyListScope.applyRepos(
    repos: LazyPagingItems<Repository>,
    navController: NavController
) {
    items(count = repos.itemCount, key = repos.itemKey { it.id }) { index ->
        repos[index]?.let { repo ->
            RepoItem(
                repo,
                onRepoClicked = {
                    navController.navigate(Routes.RepoDetailScreen.createRoute(repo.id))
                }
            )
        }
    }

    repos.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
            }

            loadState.refresh is LoadState.Error -> {
                val error = loadState.refresh as LoadState.Error
                item {
                    val modifier = if (repos.isEmpty())
                        Modifier.fillParentMaxSize()
                    else Modifier.fillMaxWidth()
                    ErrorMessage(
                        modifier = modifier,
                        message = error.error.localizedMessage.orEmpty(),
                        onClickRetry = { repos.retry() }
                    )
                }
            }

            loadState.refresh is LoadState.NotLoading -> {
                if (repos.isEmpty()) {
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = stringResource(id = R.string.str_no_results),
                            onClickRetry = { repos.retry() }
                        )
                    }
                }
            }

            loadState.append is LoadState.Loading -> {
                item { LoadingNextPageItem(modifier = Modifier.fillMaxWidth()) }
            }

            loadState.append is LoadState.Error -> {
                val error = loadState.append as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        message = error.error.localizedMessage.orEmpty(),
                        onClickRetry = { repos.retry() }
                    )
                }
            }
        }
    }
}
