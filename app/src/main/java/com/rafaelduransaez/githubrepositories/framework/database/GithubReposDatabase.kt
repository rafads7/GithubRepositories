package com.rafaelduransaez.githubrepositories.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafaelduransaez.githubrepositories.framework.database.dao.RemoteKeyDao
import com.rafaelduransaez.githubrepositories.framework.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.framework.database.dao.UsersDao
import com.rafaelduransaez.githubrepositories.framework.database.entities.RemoteKey
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.database.entities.UserEntity

@Database(entities = [RepoEntity::class, RemoteKey::class, UserEntity::class], version = 1, exportSchema = false)
abstract class GithubReposDatabase : RoomDatabase() {

    abstract fun reposDao(): ReposDao
    abstract fun usersDao(): UsersDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}