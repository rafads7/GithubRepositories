package com.rafaelduransaez.githubrepositories.framework.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReposDao {

    @Query("SELECT * FROM repositories")
    fun pagingSource(): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM repositories WHERE :query")
    fun pagingSource(query: String): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM repositories WHERE id = :id")
    fun get(id: Int): Flow<RepoEntity>

    @Query("DELETE FROM repositories")
    fun clearAll(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(repos: List<RepoEntity>): List<Long>
}