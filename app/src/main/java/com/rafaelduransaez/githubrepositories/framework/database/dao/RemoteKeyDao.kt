package com.rafaelduransaez.githubrepositories.framework.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelduransaez.githubrepositories.framework.database.entities.RemoteKey
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(repos: List<RemoteKey>): List<Long>

    @Query("SELECT * FROM remote_keys")
    suspend fun remoteKey(): RemoteKey

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): RemoteKey?

    @Query("DELETE FROM repositories")
    fun clearAll(): Int
}