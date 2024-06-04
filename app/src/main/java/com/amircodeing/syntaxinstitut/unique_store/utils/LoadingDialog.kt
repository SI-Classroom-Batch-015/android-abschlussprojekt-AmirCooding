package com.amircodeing.syntaxinstitut.unique_store.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.amircodeing.syntaxinstitut.unique_store.R

/**
 * Create a custom view class that inflates the custom layout.
 *
 * Create a new Java or Kotlin class named CustomProgressBar in your project.
 *
 *
 */
class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.progressbar_custom, this, true)
    }
}

