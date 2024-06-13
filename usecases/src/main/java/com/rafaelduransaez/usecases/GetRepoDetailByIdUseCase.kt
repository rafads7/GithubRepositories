package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubReposRepositoryImpl
import javax.inject.Inject

class GetRepoDetailByIdUseCase @Inject constructor(
    private val repository: GithubReposRepositoryImpl
) {

    operator fun invoke(id: Int) = repository.getRepoDetailById(id)
}