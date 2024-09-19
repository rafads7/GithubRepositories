package com.rafaelduransaez.githubrepositories.framework.local.mapper

import com.rafaelduransaez.apptestshared.mockRepoEntity
import com.rafaelduransaez.apptestshared.mockRepoEntityFav
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.KeyEntity
import com.rafaelduransaez.githubrepositories.framework.local.mapper.toKeyEntityList
import org.junit.Assert.assertEquals
import org.junit.Test

class GithubReposLocalMapperTest {

    @Test
    fun `toKeyEntityList should return KeyEntity list`() {
        val expected = listOf(
            KeyEntity(repoId = mockRepoEntity.id, prevKey = 1, nextKey = 2),
            KeyEntity(repoId = mockRepoEntityFav.id, prevKey = 1, nextKey = 2),
        )

        val result = listOf(mockRepoEntity, mockRepoEntityFav).toKeyEntityList(1, 2)

        assertEquals(expected, result)
    }
}