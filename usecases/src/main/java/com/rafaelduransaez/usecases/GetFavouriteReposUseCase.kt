package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.GithubRepository
import javax.inject.Inject

class GetFavouriteReposUseCase @Inject constructor(
    private val repository: GithubRepository
)
{
    operator fun invoke() = repository.getFavouriteRepositories()
}