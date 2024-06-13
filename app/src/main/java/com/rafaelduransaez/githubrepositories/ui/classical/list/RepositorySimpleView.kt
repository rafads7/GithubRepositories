package com.rafaelduransaez.githubrepositories.ui.classical.list

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.rafaelduransaez.domain.models.RepoModel
import com.rafaelduransaez.githubrepositories.ui.common.toAnnotatedString

class RepositorySimpleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val MAX_CHAR = 200
    }

    fun setRepo(repo: RepoModel) = repo.apply {
        text = toAnnotatedString(MAX_CHAR)
    }
}