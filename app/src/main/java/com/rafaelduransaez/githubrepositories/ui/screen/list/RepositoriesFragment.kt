package com.rafaelduransaez.githubrepositories.ui.screen.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.FragmentRepositoriesBinding
import com.rafaelduransaez.githubrepositories.ui.toast
import com.rafaelduransaez.githubrepositories.utils.toError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {

    private val viewModel: RepositoriesViewModel by viewModels()
    private val mainState by lazy { buildMainState() }

    private val adapter = PagedReposAdapter() { mainState.onRepoClicked(it)}
    private val header by lazy { ReposLoadStateAdapter(mainState) { adapter.retry() } }
    private val footer by lazy { ReposLoadStateAdapter(mainState) { adapter.retry() } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentRepositoriesBinding.bind(view).apply {
            bindState()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    adapter.submitData(it.dataSource)
                }
            }
        }
    }

    private fun FragmentRepositoriesBinding.bindState() {
        list.adapter = adapter.withLoadStateHeaderAndFooter(header, footer)
        errorView.onClickAction = { adapter.retry() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect { loadState ->
                    // Show a retry header if there was an error refreshing, and items were previously
                    // cached OR default to the default prepend state
                    header.loadState =
                        loadState.mediator?.refresh?.takeIf { it is LoadState.Error && !adapter.isEmpty() }
                            ?: loadState.prepend

                    // Show message when no items available
                    emptyListMssg.isVisible =
                        loadState.refresh is LoadState.NotLoading && adapter.isEmpty()

                    // Show the retry state if initial load or refresh fails.
                    val refreshError = loadState.mediator?.refresh as? LoadState.Error
                    errorView.isVisible = refreshError != null && adapter.isEmpty()
                    refreshError?.error?.let {
                        errorView.errorMessage = mainState.errorToMessage(it.toError())
                    }

                    // Only show the list if refresh succeeds, either from the the local db or the remote.
                    list.isVisible =
                        loadState.source.refresh is LoadState.NotLoading ||
                                loadState.mediator?.refresh is LoadState.NotLoading

                    // Show loading spinner during initial load or refresh.
                    progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading

                    // Toast on any error, regardless of whether it came from RemoteMediator or Paging
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error

                    errorState?.let {
                        toast(getString(R.string.str_unexpected_error))
                    }
                }
            }
        }
    }
}
