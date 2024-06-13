package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubReposRepositoryImpl
import com.rafaelduransaez.domain.models.RepoDetailModel
import javax.inject.Inject

class UpdateFavReposUseCase @Inject constructor(
    private val repository: GithubReposRepositoryImpl
) {
    suspend operator fun invoke(repo: RepoDetailModel) = repository.updateFavRepo(repo)

}