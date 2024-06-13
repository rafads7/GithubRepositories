package com.rafaelduransaez.githubrepositories.ui.classical.list

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafaelduransaez.domain.models.RepoModel
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.RepositoryListItemLayoutBinding
import com.rafaelduransaez.githubrepositories.di.ColorArray
import com.rafaelduransaez.githubrepositories.ui.common.basicDiffUtil
import com.rafaelduransaez.githubrepositories.ui.common.inflate
import javax.inject.Inject


class PagedReposAdapter @Inject constructor(
    @ColorArray val colors: IntArray? = null,
    private val listener: (RepoModel) -> Unit
) :
    PagingDataAdapter<RepoModel, PagedReposAdapter.ViewHolder>(
        basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.repository_list_item_layout)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { repo ->
            holder.bind(repo, colors)
            holder.itemView.setOnClickListener { listener(repo) }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RepositoryListItemLayoutBinding.bind(view)
        fun bind(repository: RepoModel?, colors: IntArray?) {
            binding.repo = repository
        }
    }

    fun isEmpty() = itemCount == 0
}


/*

override fun onCreateViewHolder(
parent: ViewGroup,
viewType: Int
) {
/*
= when (viewType) {
    R.layout.item -> ViewHolder(parent)
    else -> SeparatorModelViewHolder(parent)
 */
val view = parent.inflate(R.layout.repository_list_item_layout)
return PagedReposAdapter.ViewHolder(view)
}

*/

/*
override fun getItemViewType(position: Int) {
    // Use peek over getItem to avoid triggering page fetch / drops, since
    // recycling views is not indicative of the user's current scroll position.
    return when (peek(position)) {
        is UiModel.UserModel -> R.layout.item
        is UiModel.SeparatorModel -> R.layout.separator_item
        null -> throw IllegalStateException("Unknown view")
    }
}

 */

/*
override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
) {
    /*
    val item = getItem(position)
    if (holder is UserModelViewHolder) {
        holder.bind(item as UserModel)
    } else if (holder is SeparatorModelViewHolder) {
        holder.bind(item as SeparatorModel)
    }

     */
}

 */
