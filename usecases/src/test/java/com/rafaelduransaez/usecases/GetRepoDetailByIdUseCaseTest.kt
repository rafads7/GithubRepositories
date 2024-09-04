package com.rafaelduransaez.usecases

import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.domain.RepositoryDetail
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetRepoDetailByIdUseCaseTest {

    private lateinit var getRepoDetailByIdUseCase: GetRepoDetailByIdUseCase

    private val repository: GithubRepository = mock()

    @Before
    fun setUp() {
        getRepoDetailByIdUseCase = GetRepoDetailByIdUseCase(repository)
    }

    @Test
    fun `when invoked calls repository to get repo detail by id`() = runTest {

        whenever(repository.getRepoDetailById(anyInt())).doReturn(flowOf(mock()))

        getRepoDetailByIdUseCase.invoke(anyInt())

        verify(repository).getRepoDetailById(anyInt())
    }

    @Test
    fun `getRepoDetailByIdUseCase returns same repo detail than repository`() = runTest {

        val repoDetailMock = flowOf(mock<RepositoryDetail>())

        getRepoDetailByIdUseCase = GetRepoDetailByIdUseCase(mock {
            on { getRepoDetailById(anyInt()) } doReturn repoDetailMock
        })

        val repoDetail = getRepoDetailByIdUseCase.invoke(anyInt())

        assertEquals(repoDetailMock, repoDetail)
    }

}