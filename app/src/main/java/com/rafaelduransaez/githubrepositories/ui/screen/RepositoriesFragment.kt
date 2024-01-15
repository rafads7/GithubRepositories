package com.rafaelduransaez.githubrepositories.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.FragmentRepositoriesBinding
import com.rafaelduransaez.githubrepositories.ui.adapters.PagedReposAdapter
import com.rafaelduransaez.githubrepositories.ui.adapters.ReposLoadStateAdapter
import com.rafaelduransaez.githubrepositories.ui.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoriesFragment: Fragment(R.layout.fragment_repositories) {

    private val viewModel: RepositoriesViewModel by viewModels()
    private val adapter = PagedReposAdapter {
        toast("Clicked repo: ${it.id}: ${it.name}")
        findNavController().navigate(RepositoriesFragmentDirections.toDetail(it.id))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepositoriesBinding.bind(view)
        binding.bindState()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    adapter.submitData(it.dataSource)
                    binding.error = it.error
                    binding.loading = it.loading
                }
            }
        }
    }

    private fun FragmentRepositoriesBinding.bindState() {
        list.adapter = adapter.withLoadStateHeaderAndFooter(
            ReposLoadStateAdapter {
                adapter.retry()
            },
            ReposLoadStateAdapter {
                adapter.retry()
            }
        )
    }
}