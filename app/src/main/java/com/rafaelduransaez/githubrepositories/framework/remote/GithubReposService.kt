package com.rafaelduransaez.githubrepositories.framework.remote

import com.rafaelduransaez.githubrepositories.framework.remote.entities.GithubReposRemoteEntityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubReposService {

    companion object {
        const val per_page = "20"
    }

    @GET("search/repositories?q=stars:>0&sort=stars&order=desc&per_page=$per_page")
    suspend fun bestRatedRepos(@Query("page") page: Int): GithubReposRemoteEntityResponse

}

