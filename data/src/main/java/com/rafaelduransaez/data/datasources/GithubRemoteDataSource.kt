package com.rafaelduransaez.data.datasources

import com.rafaelduransaez.domain.Repository

interface GithubRemoteDataSource {
    suspend fun getBestRatedRepositories(): List<Repository>
}