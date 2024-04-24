package com.rafaelduransaez.githubrepositories.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.databinding.ErrorLayoutBinding

class ErrorLayoutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private val binding: ErrorLayoutBinding

    var errorMessage: String
        get() = binding.errorMsg.text.toString()
        set(value) {
            binding.errorLayout.isVisible = true
            binding.errorMsg.isVisible = true
            binding.errorMsg.text = value
        }

    var onClickAction: (() -> Unit)? = null
        set(value) {
            field = value
            binding.retryButton.setOnClickListener { value?.invoke() }
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = ErrorLayoutBinding.inflate(inflater, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ErrorLayoutView)
        val errorMessage = typedArray.getString(R.styleable.ErrorLayoutView_error_message)
        typedArray.recycle()

        errorMessage?.let { this.errorMessage = it }
    }

}
