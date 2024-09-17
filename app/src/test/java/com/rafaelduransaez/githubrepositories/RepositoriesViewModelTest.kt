package com.rafaelduransaez.githubrepositories

import androidx.paging.PagingData
import app.cash.turbine.test
import com.rafaelduransaez.githubrepositories.entities.buildMockRepoDetail
import com.rafaelduransaez.githubrepositories.entities.buildMockRepository
import com.rafaelduransaez.githubrepositories.ui.classical.list.RepositoriesViewModel
import com.rafaelduransaez.usecases.GetFavouriteReposUseCase
import com.rafaelduransaez.usecases.GetPagedBestRatedReposUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RepositoriesViewModelTest {

    private lateinit var viewModel: RepositoriesViewModel
    private val favUseCase: GetFavouriteReposUseCase = mock()
    private val pagedUseCase: GetPagedBestRatedReposUseCase = mock()

    private val mockRepoList = listOf(buildMockRepository())
    private val mockPagedRepoList = PagingData.from(mockRepoList)

    private val mockRepoDetailList = listOf(buildMockRepoDetail())

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        whenever(pagedUseCase()).doReturn(flowOf(mockPagedRepoList))
        whenever(favUseCase()).doReturn(flowOf(mockRepoDetailList))

        viewModel = RepositoriesViewModel(pagedUseCase, favUseCase)
    }

    @After
    fun tearDown() = Unit

    //Could not manage to make it work...

/*  @Test
    fun `getPagedBestRatedReposUseCase is called in constructor`() = runTest{

        //val data = viewModel.bestRatedRepos.asSnapshot()
        //assertEquals(mockRepoList, data)

        viewModel.bestRatedRepos.test {
            val data = viewModel.bestRatedRepos.asSnapshot()
            assertEquals(mockRepoList, data)
            cancelAndIgnoreRemainingEvents()
        }

        verify(pagedUseCase).invoke()
    }
*/

    @Test
    fun `getFavouriteReposUseCase is called in constructor`() = runTest {

        viewModel.favRepos.test {
            assertEquals(mockRepoDetailList, awaitItem())
            awaitComplete()
        }
        verify(favUseCase).invoke()
    }
}