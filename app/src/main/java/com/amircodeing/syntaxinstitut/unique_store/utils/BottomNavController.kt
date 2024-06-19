package com.amircodeing.syntaxinstitut.unique_store.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.amircodeing.syntaxinstitut.unique_store.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView




/**
 * Hides the navigation buttons in the provided activity by setting their visibility to INVISIBLE.
 *
 * @param activity the Activity in which the navigation buttons are to be hidden
 */
fun inVisibilityNavButton(activity: Activity) {
    // Find the BottomNavigationView by its ID and set its visibility to INVISIBLE
    val bottomNavigationView = activity.findViewById<BottomNavigationView>(R.id.button_nav)

    // Find the BottomAppBar by its ID and set its visibility to INVISIBLE
    val bottomAppBar = activity.findViewById<BottomAppBar>(R.id.bottom_appBar)

    // Find the FloatingActionButton (assumed to be an ImageView) by its ID and set its visibility to INVISIBLE
    val floatActionButton = activity.findViewById<ImageView>(R.id.float_action_button)

    bottomNavigationView.visibility = View.INVISIBLE
    bottomAppBar.visibility = View.INVISIBLE
    floatActionButton.visibility = View.INVISIBLE
}

/**
 * Shows the navigation buttons in the provided activity by setting their visibility to VISIBLE.
 *
 * @param activity the Activity in which the navigation buttons are to be shown
 */
fun visibilityNavButton(activity: Activity) {
    // Find the BottomNavigationView by its ID and set its visibility to VISIBLE
    val bottomNavigationView = activity.findViewById<BottomNavigationView>(R.id.button_nav)

    // Find the BottomAppBar by its ID and set its visibility to VISIBLE
    val bottomAppBar = activity.findViewById<BottomAppBar>(R.id.bottom_appBar)

    // Find the FloatingActionButton (assumed to be an ImageView) by its ID and set its visibility to VISIBLE
    val floatActionButton = activity.findViewById<ImageView>(R.id.float_action_button)

    bottomNavigationView.visibility = View.VISIBLE
    bottomAppBar.visibility = View.VISIBLE
    floatActionButton.visibility = View.VISIBLE
}



