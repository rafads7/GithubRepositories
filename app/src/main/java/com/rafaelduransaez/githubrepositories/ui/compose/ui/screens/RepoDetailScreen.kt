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
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.IconComponent
import com.rafaelduransaez.githubrepositories.ui.screen.detail.RepositoryDetailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RepoDetailScreen(
    navController: NavController,
    viewModel: RepositoryDetailViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopDetailBar(state.repo?.name.orEmpty(), scrollBehavior) {
                navController.popBackStack()
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            OpenInBrowserFloatingActionButton() {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(state.repo.toString())
                }
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
            Text(
                text = buildAnnotatedString {
                    Property(name = "Name", value = name)
                    Property(name = "Description", value = description)
                    Property(name = "Stars count", value = starsCount.toString())
                    Property(name = "Forks count", value = forksCount.toString())
                    Property(name = "Language", value = language)
                    Property(name = "Owner", value = owner.userName, true)
                }
            )
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


@Preview
@Composable
fun BottomNavigationBarComponent(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit = {}
) {
    var index by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    ) {
        NavigationBarItem(
            label = { Text(text = stringResource(id = R.string.str_all)) },
            selected = index == 0, onClick = {
                index = 0
                onItemClicked("All")
            },
            icon = { IconComponent(imageVector = Icons.Default.List) })

        NavigationBarItem(
            label = { Text(text = stringResource(id = R.string.str_favourites)) },
            selected = index == 1, onClick = {
                index = 1
                onItemClicked("Fav")
            },
            icon = { IconComponent(imageVector = Icons.Default.Favorite) })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopDetailBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClicked: () -> Unit
) {

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                IconComponent(imageVector = Icons.Default.ArrowBack)
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Preview
@Composable
fun OpenInBrowserFloatingActionButton(onFabClicked: () -> Unit = {}) {

    FloatingActionButton(
        onClick = { onFabClicked() },
        shape = ShapeDefaults.ExtraLarge
    ) {
        IconComponent(
            imageVector = Icons.Default.OpenInBrowser,
            contentDescription = "Navigate to github repository's button",
        )
    }
}

@Composable
private fun AnnotatedString.Builder.Property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 16.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (end) append("\n")
    }
}