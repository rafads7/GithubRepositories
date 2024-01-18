package com.rafaelduransaez.githubrepositories.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.FragmentRepositoryDetailBinding
import com.rafaelduransaez.githubrepositories.ui.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryDetailFragment: Fragment(R.layout.fragment_repository_detail) {

    private val viewModel: RepositoryDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepositoryDetailBinding.bind(view).apply {
            repoDetailToolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            state.repo?.let {
                binding.repo = it
            }
        }
    }

}