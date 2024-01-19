package com.rafaelduransaez.githubrepositories.ui.screen.list

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.utils.truncate

class RepositorySimpleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val MAX_CHAR = 200
    }

    fun setRepo(repo: Repository) = repo.apply {
        text = buildSpannedString {

            bold { append("ID: ") }
            appendLine(id.toString())

            bold { append("Name: ") }
            appendLine(name)

            bold { append("Description: ") }
            appendLine(description.truncate(MAX_CHAR))

            bold { append("Number of stars: ") }
            appendLine(starsCount.toString())
        }
    }
}