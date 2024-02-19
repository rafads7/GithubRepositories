package com.rafaelduransaez.githubrepositories.ui.screen.list

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.ReposLoadStateFooterViewItemBinding
import com.rafaelduransaez.githubrepositories.ui.inflate
import com.rafaelduransaez.githubrepositories.utils.toError

class ReposLoadStateAdapter(
    private val mainState: MainState,
    private val retry: () -> Unit
) : LoadStateAdapter<ReposLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = parent.inflate(R.layout.repos_load_state_footer_view_item)
        return ViewHolder(view, retry, mainState)
    }
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
    class ViewHolder(
        view: View,
        retry: () -> Unit,
        private val mainState: MainState
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ReposLoadStateFooterViewItemBinding.bind(view)

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = mainState.errorToMessage(loadState.error.toError())
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }
}