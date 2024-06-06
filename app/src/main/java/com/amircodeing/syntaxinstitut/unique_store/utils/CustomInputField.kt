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
 *  @param JvmOverloads annotation generates multiple constructors in the Java bytecode,
 *  making it easier to instantiate the class from Java code with default parameter values.
 */
class CustomInputField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var labelTextView: TextView
    private var inputEditText: TextInputEditText
    private var visibilityToggle: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_input_layout, this, true)
        orientation = VERTICAL

        labelTextView = findViewById(R.id.label)
        inputEditText = findViewById(R.id.edit_text_hint)
        visibilityToggle = findViewById(R.id.toggle_visibility)

        // Initialize visibility toggle button
        visibilityToggle.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    /**
     *  Methods to set Label and hint the text to the TextInputEditText
     */
    fun setLabelText(text: String) {
        labelTextView.text = text
    }

    fun setInputText(text: String) {
        inputEditText.text = Editable.Factory.getInstance().newEditable(text)
    }
    fun setInputHint(hint: String) {
        inputEditText.hint = hint
        if(labelTextView.text == "Mobile" || labelTextView.text =="Nr" || labelTextView.text =="Zip") {
        inputEditText.inputType = TYPE_CLASS_NUMBER
        }
    }
    /**
     *    Method to retrieve the text from the TextInputEditText
     */

    fun getText(): String {
        return inputEditText.text.toString()
    }

    fun clearInputs(){
        inputEditText.text?.clear()
    }


    fun setPasswordMode(enabled: Boolean) {
        if (enabled) {
            inputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            visibilityToggle.visibility = View.VISIBLE
        } else {
            inputEditText.inputType = android.text.InputType.TYPE_CLASS_TEXT
            visibilityToggle.visibility = View.GONE
        }
    }

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
