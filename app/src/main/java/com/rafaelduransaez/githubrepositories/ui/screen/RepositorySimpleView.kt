package com.rafaelduransaez.githubrepositories.ui.screen

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.rafaelduransaez.domain.Repository

class RepositorySimpleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setRepo(repo: Repository) = repo.apply {
        text = buildSpannedString {

            bold { append("ID: ") }
            appendLine(id.toString())

            bold { append("Name: ") }
            appendLine(name)

            bold { append("Description: ") }
            appendLine(description)

            bold { append("Number of stars: ") }
            appendLine(starsCount.toString())
        }
    }
}