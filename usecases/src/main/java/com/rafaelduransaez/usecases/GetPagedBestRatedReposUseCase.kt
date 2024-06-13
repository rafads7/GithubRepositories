package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubReposRepositoryImpl
import com.rafaelduransaez.data.repositories.PagedGithubReposRepository
import com.rafaelduransaez.domain.repositories.GithubReposRepository
import javax.inject.Inject

class GetPagedBestRatedReposUseCase @Inject constructor(
    private val repository: PagedGithubReposRepository
)
{
    operator fun invoke() = repository.getPagedBestRatedRepositories()
}