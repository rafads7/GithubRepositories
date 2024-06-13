package com.rafaelduransaez.githubrepositories.ui.compose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rafaelduransaez.domain.models.Error
import com.rafaelduransaez.domain.models.RepoDetailModel
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.IconComponent
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.RepoDetailScreenNavActions
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.toAnnotatedString
import com.rafaelduransaez.githubrepositories.ui.common.navigateTo
import com.rafaelduransaez.githubrepositories.ui.classical.detail.RepositoryDetailViewModel
import com.rafaelduransaez.githubrepositories.utils.toComposableString
import com.rafaelduransaez.githubrepositories.utils.toString
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RepoDetailScreen(
    viewModel: RepositoryDetailViewModel = hiltViewModel(),
    actions: RepoDetailScreenNavActions
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopDetailBar(
                title = state.repo?.name.orEmpty(),
                onBackClicked = { actions.onBackClicked() },
                onOpenInBrowserClicked = {
                    state.repo?.url?.let { url ->
                        context.navigateTo(url)?.let {
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(it.toString(context = context))
                            }
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            val icon =
                if (state.repo?.favourite == true) Icons.Default.Favorite
                else Icons.Default.FavoriteBorder
            LikeFloatingActionButton(icon) {
                viewModel.onFavButtonClicked()
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val modifier = Modifier
            .padding(paddingValues = paddingValues)
            .padding(horizontal = 16.dp)
        with(state) {
            repo?.let { Body(modifier, repo = it) }
                ?: ErrorBody(modifier) { viewModel.onRetryClicked() }

            error?.let {
                ErrorBody(modifier) { viewModel.onRetryClicked() }
            }

            actionError?.let { coroutineScope.launch {
                snackBarHostState.showSnackbar(it.toString(context)) }
            }
        }
    }
}

@Composable
fun ErrorBody(
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = Error.Database.toComposableString(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Button(onClick = { onRetryClicked() }) {
            Text(text = stringResource(id = R.string.str_alt_retry))
        }
    }
}

@Composable
fun Body(modifier: Modifier, repo: RepoDetailModel) {
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
        }
    )
}


@Composable
fun LikeFloatingActionButton(
    imageVector: ImageVector,
    onFabClicked: () -> Unit = {}
) {

    FloatingActionButton(
        onClick = { onFabClicked() },
        shape = ShapeDefaults.ExtraLarge,
        modifier = Modifier.testTag("fab_like")
    ) {
        IconComponent(
            imageVector = imageVector,
            contentDescription = "Mark as favourite",
            modifier = Modifier.testTag("fab_like_icon")
        )
    }
}

