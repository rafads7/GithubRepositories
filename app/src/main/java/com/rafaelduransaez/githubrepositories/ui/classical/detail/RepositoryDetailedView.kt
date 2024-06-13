package com.rafaelduransaez.githubrepositories.ui.classical.detail


import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.rafaelduransaez.domain.models.RepoDetailModel

class RepositoryDetailedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setRepo(repo: RepoDetailModel) = repo.apply {
        text = buildSpannedString {

            bold { append("User name: ") }
            appendLine(owner.userName)

            bold { append("Description: ") }
            appendLine(description)

            bold { append("Number of stars: ") }
            appendLine(starsCount.toString())

            bold { append("Number of forks: ") }
            appendLine(forksCount.toString())

            bold { append("Language: ") }
            appendLine(language)
        }
    }
}