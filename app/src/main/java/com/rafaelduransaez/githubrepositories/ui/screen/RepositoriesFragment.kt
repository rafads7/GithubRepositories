package com.rafaelduransaez.githubrepositories.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.rafaelduransaez.githubrepositories.databinding.FragmentRepositoriesBinding
import com.rafaelduransaez.githubrepositories.ui.adapters.RepositoriesAdapter
import com.rafaelduransaez.githubrepositories.ui.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoriesFragment: Fragment() {

    private val viewModel: RepositoriesViewModel by viewModels()
    private val adapter = RepositoriesAdapter {
        toast("Clicked repo: ${it.id}: ${it.name}")
        findNavController().navigate(RepositoriesFragmentDirections.toDetail(it.id))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    adapter.submitList(it.repositories)
                }
            }
        }

        return binding.root
    }
}