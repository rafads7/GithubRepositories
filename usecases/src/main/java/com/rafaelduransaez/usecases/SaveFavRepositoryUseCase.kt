package com.rafaelduransaez.usecases

import com.rafaelduransaez.domain.models.RepoDetailModel
import com.rafaelduransaez.domain.repositories.GithubReposRepository
import javax.inject.Inject

class SaveFavRepositoryUseCase @Inject constructor(
    private val repository: GithubReposRepository
) {
    suspend operator fun invoke(repo: RepoDetailModel) = repository.updateFavRepo(repo)

}