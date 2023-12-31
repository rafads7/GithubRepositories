package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.GithubRepository
import javax.inject.Inject

class GetBestRatedReposUseCase @Inject constructor(private val repository: GithubRepository) {

    suspend operator fun invoke() = repository.getBestRatedRepositories()
}