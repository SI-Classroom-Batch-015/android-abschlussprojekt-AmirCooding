package com.amircodeing.syntaxinstitut.unique_store.utils

import android.app.Activity
import android.view.View
import com.amircodeing.syntaxinstitut.unique_store.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ChangeButtonNavVisibility {
    companion object{

        fun inVisibilityNavButton(activity: Activity) {
            val bottomNavigationView =
                activity.findViewById<BottomNavigationView>(R.id.button_nav)
            val bottomAppBar = activity.findViewById<BottomAppBar>(R.id.bottom_appBar)
            val floatActionButton = activity.findViewById<FloatingActionButton>(R.id.float_action_button)
            bottomNavigationView.visibility = View.INVISIBLE
            bottomAppBar.visibility = View.INVISIBLE
            floatActionButton.visibility = View.INVISIBLE
        }


        fun visibilityNavButton(activity: Activity) {
            val bottomNavigationView =
                activity.findViewById<BottomNavigationView>(R.id.button_nav)
            val bottomAppBar = activity.findViewById<BottomAppBar>(R.id.bottom_appBar)
            val floatActionButton =
                activity.findViewById<FloatingActionButton>(R.id.float_action_button)
            bottomNavigationView.visibility = View.VISIBLE
            bottomAppBar.visibility = View.VISIBLE
            floatActionButton.visibility = View.VISIBLE
        }

    }


}