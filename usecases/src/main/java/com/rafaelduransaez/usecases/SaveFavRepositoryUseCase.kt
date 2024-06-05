package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.domain.RepoDetailModel
import javax.inject.Inject

class UpdateFavReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(repo: RepoDetailModel) = repository.updateFavRepo(repo)

}