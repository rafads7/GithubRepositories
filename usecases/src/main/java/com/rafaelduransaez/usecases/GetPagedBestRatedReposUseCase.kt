package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.GithubRepository
import javax.inject.Inject

class GetPagedBestRatedReposUseCase @Inject constructor(
    private val repository: GithubRepository)
{
    operator fun invoke() = repository.getPagedBestRatedRepositories()
}