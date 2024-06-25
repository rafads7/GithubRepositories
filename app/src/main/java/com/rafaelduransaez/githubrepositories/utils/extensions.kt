package com.rafaelduransaez.githubrepositories.utils

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rafaelduransaez.domain.models.Error
import com.rafaelduransaez.domain.models.RepoModel
import com.rafaelduransaez.domain.models.RepoDetailModel
import com.rafaelduransaez.domain.models.UserDetailModel
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.sources.local.database.entities.FavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.sources.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.sources.local.database.entities.RepoUserEntity
import com.rafaelduransaez.githubrepositories.sources.local.database.entities.UserEntity
import com.rafaelduransaez.githubrepositories.sources.remote.entities.GithubRepoRemoteOwnerEntity
import retrofit2.HttpException
import java.io.IOException

fun GithubRepoRemoteOwnerEntity.toUserEntity() = UserEntity(id, avatarUrl, login, repos_url, type, url)

fun RepoEntity.toRepository() =
    RepoModel(id, name, description.orEmpty(), starsCount, forksCount, language.orEmpty(), favourite)

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

fun RepoUserEntity.toRepositoryDetail() = RepoDetailModel(
    repo.id, repo.name, repo.description, repo.starsCount, repo.forksCount, repo.language, repo.url,
    favourite = repo.favourite,
    owner = UserDetailModel(user.userName, user.avatarUrl)
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
    is ActivityNotFoundException -> Error.UI
    else -> Error.Unknown(message.orEmpty())
}

fun Error.toString(context: Context) = when (this) {
    is Error.Connection -> context.getString(R.string.str_connection_error)
    is Error.Server -> context.getString(R.string.str_server_error, code)
    is Error.Unknown -> context.getString(R.string.str_unknown_error, message)
    is Error.UI -> context.getString(R.string.str_activity_error)
    is Error.Database -> context.getString(R.string.str_database_error)
}

@Composable
fun Error.toComposableString() = when (this) {
    is Error.Connection -> stringResource(id = R.string.str_connection_error)
    is Error.Server -> stringResource(id = R.string.str_server_error, code)
    is Error.Unknown -> stringResource(id = R.string.str_unknown_error, message)
    is Error.UI -> stringResource(id = R.string.str_activity_error)
    is Error.Database -> stringResource(id = R.string.str_database_error)
}
