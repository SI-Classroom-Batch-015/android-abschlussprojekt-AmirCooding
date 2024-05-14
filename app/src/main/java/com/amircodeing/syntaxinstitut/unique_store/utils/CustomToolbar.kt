package com.amircodeing.syntaxinstitut.unique_store.utils
import com.amircodeing.syntaxinstitut.unique_store.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController


class CustomToolbar {
    companion object {
        fun setToolbar(components: ToolbarComponents) {
            val toolbar = components.view.findViewById<Toolbar>(components.path)
            val selectedIcon = toolbar.findViewById<ImageView>(R.id.icon_selectable_IV)
            val selectedBackButton = toolbar.findViewById<ImageView>(R.id.back_button_detail)
            val title = toolbar.findViewById<TextView>(R.id.title_toolbar_TV)
            title.text = components.title
            if (components.visibility) {
                selectedIcon.visibility = View.VISIBLE
                components.icon?.let { selectedIcon.setImageResource(it) }
            } else {
                //TODO change wight space between text and icon
                // selectedIcon.visibility = View.GONE
            }
            selectedBackButton.setOnClickListener {
                components.view.findNavController().navigateUp()
            }
        }
    }
}

data class ToolbarComponents(val view: View, val title: String, val visibility: Boolean, val path: Int, val icon: Int?)

