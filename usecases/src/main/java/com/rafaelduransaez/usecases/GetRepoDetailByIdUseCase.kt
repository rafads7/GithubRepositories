package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubRepository
import javax.inject.Inject

class GetRepoDetailByIdUseCase @Inject constructor(
    private val repository: GithubRepository
) {

    operator fun invoke(id: Int) = repository.getRepoDetailById(id)
}