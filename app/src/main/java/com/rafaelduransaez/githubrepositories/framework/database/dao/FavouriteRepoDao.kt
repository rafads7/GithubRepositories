package com.rafaelduransaez.githubrepositories.framework.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelduransaez.githubrepositories.framework.database.entities.FavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRepoDao {

    @Query("SELECT * FROM favourite_repo")
    fun getAll(): Flow<List<FavouriteRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(repo: FavouriteRepoEntity): Long
    @Query("DELETE FROM favourite_repo WHERE githubId = :githubId")
    suspend fun delete(githubId: Int): Int
}