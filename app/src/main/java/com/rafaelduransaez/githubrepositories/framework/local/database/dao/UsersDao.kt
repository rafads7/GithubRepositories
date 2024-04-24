package com.rafaelduransaez.githubrepositories.framework.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("SELECT * FROM users WHERE id = :id")
    fun get(id: Int): Flow<UserEntity>

    @Query("DELETE FROM users")
    fun clearAll(): Int
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(repos: List<UserEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(repos: UserEntity): Long

}