package com.rafaelduransaez.githubrepositories.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.AppNavGraph
import com.rafaelduransaez.githubrepositories.ui.compose.ui.theme.GithubRepositoriesTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubRepositoriesComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubRepositoriesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}