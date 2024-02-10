package com.rafaelduransaez.githubrepositories.ui.compose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.IconComponent
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.RepoDetailScreenNavActions
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.toAnnotatedString
import com.rafaelduransaez.githubrepositories.ui.screen.detail.RepositoryDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RepoDetailScreen(
    viewModel: RepositoryDetailViewModel = hiltViewModel(),
    actions: RepoDetailScreenNavActions
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopDetailBar(
                title = state.repo?.name.orEmpty(),
                scrollBehavior = scrollBehavior,
                onBackClicked = { actions.onBackClicked() },
                onOpenInBrowserClicked = {
                    state.repo?.url?.let { url -> actions.onOpenRepoInBrowserButtonClicked(url) }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            val icon =
                if (state.repo?.favourite == true) Icons.Default.Favorite
                else Icons.Default.FavoriteBorder
            OpenInBrowserFloatingActionButton(icon) {
                viewModel.onFavButtonClicked()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        state.repo?.let { repo ->
            Body(
                Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 16.dp), repo = repo
            )
        }
    }
}

@Composable
fun Body(modifier: Modifier, repo: RepositoryDetail) {
    Column(modifier = modifier) {
        with(repo) {
            Text(text = toAnnotatedString())
        }

        AsyncImage(
            model = repo.owner.avatarUrl, contentDescription = "Owner avatar",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopDetailBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClicked: () -> Unit,
    onOpenInBrowserClicked: () -> Unit
) {

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                IconComponent(imageVector = Icons.Default.ArrowBack)
            }
        },
        actions = {
            IconButton(onClick = { onOpenInBrowserClicked() }) {
                IconComponent(imageVector = Icons.Default.OpenInBrowser)
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
fun OpenInBrowserFloatingActionButton(
    imageVector: ImageVector,
    onFabClicked: () -> Unit = {}
) {

    FloatingActionButton(
        onClick = { onFabClicked() },
        shape = ShapeDefaults.ExtraLarge
    ) {
        IconComponent(
            imageVector = imageVector,
            contentDescription = "Mark as favourite",
        )
    }
}

