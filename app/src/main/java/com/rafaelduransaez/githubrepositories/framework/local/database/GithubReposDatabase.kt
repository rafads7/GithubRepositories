package com.rafaelduransaez.githubrepositories.framework.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.FavouriteRepoDao
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.RemoteKeyDao
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.UsersDao
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.FavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.KeyEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.UserEntity

@Database(
    entities = [RepoEntity::class, KeyEntity::class, UserEntity::class, FavouriteRepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubReposDatabase : RoomDatabase() {

    abstract fun reposDao(): ReposDao
    abstract fun usersDao(): UsersDao
    abstract fun favouriteRepoDao(): FavouriteRepoDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}