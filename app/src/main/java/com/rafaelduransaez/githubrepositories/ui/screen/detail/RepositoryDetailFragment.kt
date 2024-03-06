package com.rafaelduransaez.githubrepositories.ui.screen.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.FragmentRepositoryDetailBinding
import com.rafaelduransaez.githubrepositories.ui.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private val viewModel: RepositoryDetailViewModel by viewModels()
    private val state by lazy { buildDetailState() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepositoryDetailBinding.bind(view).apply {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            favButton.setOnClickListener { viewModel.onFavButtonClicked() }
            state = this@RepositoryDetailFragment.state
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.repo = it.repo
            binding.error = it.error
            state.handleError(it)
        }
    }
}
