package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubReposRepositoryImpl
import com.rafaelduransaez.domain.repositories.GithubReposRepository
import javax.inject.Inject

class GetFavouriteReposUseCase @Inject constructor(
    private val repository: GithubReposRepository
)
{
    operator fun invoke() = repository.getFavouriteRepositories()
}