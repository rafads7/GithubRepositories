package com.rafaelduransaez.githubrepositories.ui.compose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.ErrorMessage
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.IconComponent
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.LoadingNextPageItem
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.PageLoader
import com.rafaelduransaez.githubrepositories.ui.isEmpty
import com.rafaelduransaez.githubrepositories.ui.toComposableAnnotatedString

const val MAX_CHAR = 200

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

        item { Spacer(modifier = Modifier.padding(4.dp)) }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoItem(
    repo: Repository,
    onRepoClicked: () -> Unit = {}
) {

    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small,
        onClick = { onRepoClicked() }

    ) {
        Row(Modifier.padding(8.dp)) {
            Text(
                text = repo.toComposableAnnotatedString(),
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            if (repo.favourite) {
                IconComponent(imageVector = Icons.Default.Favorite)
            }
        }

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
    }
}
