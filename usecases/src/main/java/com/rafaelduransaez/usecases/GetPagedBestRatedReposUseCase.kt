package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubReposRepositoryImpl
import javax.inject.Inject

class GetPagedBestRatedReposUseCase @Inject constructor(
    private val repository: GithubReposRepositoryImpl
)
{
    operator fun invoke() = repository.getPagedBestRatedRepositories()
}