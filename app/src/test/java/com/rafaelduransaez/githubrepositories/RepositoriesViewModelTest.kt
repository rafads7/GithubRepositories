package com.rafaelduransaez.githubrepositories

import com.rafaelduransaez.githubrepositories.ui.classical.list.RepositoriesViewModel
import com.rafaelduransaez.usecases.GetFavouriteReposUseCase
import com.rafaelduransaez.usecases.GetPagedBestRatedReposUseCase
import org.junit.After
import org.junit.Before
import org.mockito.kotlin.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RepositoriesViewModelTest {

    private lateinit var vm: RepositoriesViewModel
    private val favUseCase: GetFavouriteReposUseCase = mock()
    private val pagedUseCase: GetPagedBestRatedReposUseCase = mock()

    @Before
    fun setUp() {
        vm = RepositoriesViewModel(pagedUseCase, favUseCase)
    }

    @After
    fun tearDown() = Unit

/*
    @Test
    fun `getPagedBestRatedReposUseCase is called in constructor`() {
        verify(pagedUseCase).invoke()
    }

    @Test
    fun `getFavouriteReposUseCase is called in constructor`() {
        verify(favUseCase).invoke()
    }*/
}