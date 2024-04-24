package com.rafaelduransaez.githubrepositories.framework.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReposDao {

    @Query("SELECT * FROM repositories")
    fun pagingSource(): PagingSource<Int, RepoEntity>

    @Transaction
    @Query("SELECT * FROM repositories")
    fun pagingRepos(): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM repositories WHERE :query")
    fun pagingSource(query: String): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM repositories WHERE id = :id")
    fun get(id: Int): Flow<RepoEntity>

    @Transaction
    @Query("SELECT * FROM repositories WHERE id = :id")
    fun getRepoDetail(id: Int): Flow<RepoUserEntity>

    @Transaction
    @Query("SELECT * FROM repositories WHERE favourite = 1")
    fun getFavouriteRepos(): Flow<List<RepoUserEntity>>

    @Query("DELETE FROM repositories")
    fun clearAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(repos: List<RepoEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(repos: RepoEntity): Long

    @Query("UPDATE repositories SET favourite = :favourite WHERE id = :id")
    suspend fun update(id: Int, favourite: Boolean): Int

    @Query(
        """
        UPDATE repositories
        SET favourite = 
            (CASE WHEN EXISTS 
            (SELECT 1 FROM favourite_repo fav 
                WHERE repositories.githubId = fav.githubId AND fav.githubId IN (:reposGithubIds))
            THEN 1 ELSE 0 END) 
        WHERE githubId IN (:reposGithubIds)
        """
    )
    suspend fun updateFavoriteStatus(reposGithubIds: List<Int>): Int

}