package com.rafaelduransaez.githubrepositories.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.RepositoryListItemLayoutBinding
import com.rafaelduransaez.githubrepositories.ui.basicDiffUtil
import com.rafaelduransaez.githubrepositories.ui.inflate

/**
 *
 * NOT USED. See PagedReposAdapter
 */
class RepositoriesAdapter(private val listener: (Repository) -> Unit) :
    ListAdapter<Repository, RepositoriesAdapter.ViewHolder>(
        basicDiffUtil { old, new -> old.id == new.id }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.repository_list_item_layout)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        holder.bind(repository)
        holder.itemView.setOnClickListener { listener(repository) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RepositoryListItemLayoutBinding.bind(view)
        fun bind(repository: Repository) {
            binding.repo = repository
        }
    }

    class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RepositoryListItemLayoutBinding.bind(view)
        fun bind(repository: Repository) {
            binding.repo = repository
        }
    }
}