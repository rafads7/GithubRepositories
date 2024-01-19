package com.rafaelduransaez.githubrepositories.framework.remote

import com.rafaelduransaez.data.datasources.GithubRemoteDataSource
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(
    private val apiService: RepositoriesService,
) : GithubRemoteDataSource {

}