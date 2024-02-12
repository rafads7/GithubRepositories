package com.rafaelduransaez.githubrepositories.utils

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.domain.UserDetail
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.framework.database.entities.FavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoUser
import com.rafaelduransaez.githubrepositories.framework.database.entities.UserEntity
import com.rafaelduransaez.githubrepositories.framework.remote.entities.RemoteOwner
import com.rafaelduransaez.githubrepositories.framework.remote.entities.RemoteRepo
import retrofit2.HttpException
import java.io.IOException

fun RemoteRepo.toRepoEntity() =
    RepoEntity(
        name = name,
        githubId = id,
        description = description ?: "",
        starsCount = stargazers_count,
        forksCount = forks_count,
        language = language ?: "",
        url = html_url,
        ownerId = owner.id
    )

fun RemoteOwner.toUserEntity() = UserEntity(id, avatarUrl, login, repos_url, type, url)

fun RepoEntity.toRepository() =
    Repository(id, name, description ?: "", starsCount, forksCount, language ?: "", favourite)

fun RepoEntity.toFavouriteRepo() =
    FavouriteRepoEntity(
        githubId = githubId,
        name = name,
        description = description,
        starsCount = starsCount,
        forksCount = forksCount,
        language = language,
        url = url,
        ownerId = ownerId
    )

fun RepoUser.toRepositoryDetail() = RepositoryDetail(
    repo.id, repo.name, repo.description, repo.starsCount, repo.forksCount, repo.language, repo.url,
    favourite = repo.favourite,
    owner = UserDetail(user.userName, user.avatarUrl)
)

fun String.truncate(limit: Int): String {
    return if (length > limit) {
        substring(0, limit - 1) + "..."
    } else {
        this
    }
}

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connection
    is HttpException -> Error.Server(code())
    is ActivityNotFoundException -> Error.Activity
    else -> Error.Unknown(message ?: "")
}

fun Error.toString(context: Context) = when (this) {
    is Error.Connection -> context.getString(R.string.str_connection_error)
    is Error.Server -> context.getString(R.string.str_server_error, code)
    is Error.Unknown -> context.getString(R.string.str_unknown_error, message)
    is Error.Activity -> context.getString(R.string.str_activity_error)
    is Error.Database -> context.getString(R.string.str_database_error)
}

@Composable
fun Error.toComposableString() = when (this) {
    is Error.Connection -> stringResource(id = R.string.str_connection_error)
    is Error.Server -> stringResource(id = R.string.str_server_error, code)
    is Error.Unknown -> stringResource(id = R.string.str_unknown_error, message)
    is Error.Activity -> stringResource(id = R.string.str_activity_error)
    is Error.Database -> stringResource(id = R.string.str_database_error)
}
