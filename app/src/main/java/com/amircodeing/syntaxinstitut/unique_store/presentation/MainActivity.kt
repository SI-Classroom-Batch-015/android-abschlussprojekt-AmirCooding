package com.amircodeing.syntaxinstitut.unique_store.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.ActivityMainBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.favorite.FavoriteFragment
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeDrawable.BadgeGravity
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import kotlinx.coroutines.delay


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel.updateFavoriteProductCount()
        viewModel.updateCartProductCount()
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHost.navController
        binding.buttonNav.setupWithNavController(navController)

        binding.floatActionButton.setOnClickListener {
            navController.navigate(R.id.cartFragment)
        }


        viewModel.cartBadge.observe(this) { badgeCount ->
            badgeCount?.let {
                badgeSetupFloatActionButton(it)
            }
        }
        viewModel.showCountFavorites.observe(this) { badgeCount ->
            badgeCount?.let {
                badgeSetup(R.id.favoriteFragment, it)
                budgeClear(it)
            }
        }


    }

    private fun badgeSetup(id: Int, alerts: Int) {
        val badge = binding.buttonNav.getOrCreateBadge(id)
        if (alerts > 0) {
            badge.isVisible = true
            badge.number = alerts

        }
    }

    private fun badgeSetupFloatActionButton(alerts: Int) {
        binding.floatActionButton.viewTreeObserver.addOnGlobalLayoutListener(@ExperimentalBadgeUtils object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.floatActionButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val badgeDrawable = BadgeDrawable.create(this@MainActivity)
                badgeDrawable.number = alerts
                badgeDrawable.isVisible = true
                BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.floatActionButton, null)
            }
        })
    }

    private fun budgeClear(id: Int) {
        val badgeDrawable = binding.buttonNav.getBadge(id)
        if (badgeDrawable != null) {
            badgeDrawable.isVisible = true
            badgeDrawable.clearNumber()
            badgeDrawable.backgroundColor =
                ContextCompat.getColor(binding.root.context, R.color.primaryDark)

            binding.buttonNav.removeBadge(id)
        }
    }
}