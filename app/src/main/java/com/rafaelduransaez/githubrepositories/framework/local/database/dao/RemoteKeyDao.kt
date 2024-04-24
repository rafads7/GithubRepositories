package com.rafaelduransaez.githubrepositories.framework.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.KeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(repos: List<KeyEntity>): List<Long>

    @Query("SELECT * FROM remote_keys")
    suspend fun remoteKey(): KeyEntity

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): KeyEntity?

    @Query("DELETE FROM repositories")
    fun clearAll(): Int
}