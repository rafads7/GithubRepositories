package com.rafaelduransaez.githubrepositories.framework.api

import com.rafaelduransaez.domain.RemoteReposResult
import retrofit2.http.GET

interface RepositoriesService {

    @GET("search/repositories?q=stars:>1&sort=stars")
    suspend fun bestRatedRepos(): RemoteReposResult
}

