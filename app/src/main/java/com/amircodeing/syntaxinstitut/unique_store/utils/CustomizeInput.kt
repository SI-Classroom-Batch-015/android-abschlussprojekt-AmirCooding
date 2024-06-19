package com.amircodeing.syntaxinstitut.unique_store.utils

import android.content.Context
import android.text.Editable
import android.text.InputType.TYPE_CLASS_NUMBER
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.amircodeing.syntaxinstitut.unique_store.R
import com.google.android.material.textfield.TextInputEditText

/**
 * A custom input view that combines a label, an input field, and a visibility toggle for password input.
 *
 * @param context the Context in which the view is running
 * @param attrs the attributes of the XML tag that is inflating the view
 * @param defStyleAttr an attribute in the current theme that contains a reference to a style resource
 * that supplies default values for the view. Can be 0 to not look for defaults.
 */
class CustomizeInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    // Declare UI elements
    private var labelTextView: TextView
    private var inputEditText: TextInputEditText
    private var visibilityToggle: ImageView

    init {
        // Inflate the custom layout and set the orientation
        LayoutInflater.from(context).inflate(R.layout.custom_input_layout, this, true)
        orientation = VERTICAL

        // Initialize UI elements
        labelTextView = findViewById(R.id.label)
        inputEditText = findViewById(R.id.edit_text_hint)
        visibilityToggle = findViewById(R.id.toggle_visibility)

        // Set up visibility toggle button click listener
        visibilityToggle.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    /**
     * Sets the text for the label TextView.
     *
     * @param text the text to set on the label
     */
    fun setLabelText(text: String) {
        labelTextView.text = text
    }

    /**
     * Sets the text for the input TextInputEditText.
     *
     * @param text the text to set in the input field
     */
    fun setInputText(text: String) {
        inputEditText.text = Editable.Factory.getInstance().newEditable(text)
    }

    /**
     * Sets the hint for the input TextInputEditText.
     *
     * @param hint the hint to set in the input field
     */
    fun setInputHint(hint: String) {
        inputEditText.hint = hint
        // Set input type to number if the label indicates a numeric input
        if (labelTextView.text == "Mobile" || labelTextView.text == "Nr" || labelTextView.text == "Zip") {
            inputEditText.inputType = TYPE_CLASS_NUMBER
        }
    }

    /**
     * Retrieves the text from the input TextInputEditText.
     *
     * @return the text in the input field as a String
     */
    fun getText(): String {
        return inputEditText.text.toString()
    }

    /**
     * Clears the text in the input TextInputEditText.
     */
    fun clearInputs() {
        inputEditText.text?.clear()
    }

    /**
     * Enables or disables password mode, toggling the input type and visibility of the toggle button.
     *
     * @param enabled whether to enable password mode
     */
    fun setPasswordMode(enabled: Boolean) {
        if (enabled) {
            inputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            visibilityToggle.visibility = View.VISIBLE
        } else {
            inputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT
            visibilityToggle.visibility = View.GONE
        }
    }

    /**
     * Toggles the visibility of the password in the input field.
     */
    private fun togglePasswordVisibility() {
        if (inputEditText.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            inputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            visibilityToggle.setImageResource(R.drawable.icon_visibility_off)
        } else {
            inputEditText.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            visibilityToggle.setImageResource(R.drawable.icon_visibility)
        }
        // Move cursor to end of text
        inputEditText.setSelection(inputEditText.text?.length ?: 0)
    }
}
