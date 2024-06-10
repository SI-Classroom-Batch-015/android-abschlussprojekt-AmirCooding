package com.amircodeing.syntaxinstitut.unique_store.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.ActivityMainBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import kotlinx.coroutines.flow.distinctUntilChanged


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
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
         //set Badge for Favorite
        viewModel.showCountFavoritesLiveData.observe(this) { numberProductInFavorites ->
            binding.buttonNav.getOrCreateBadge(R.id.favoriteFragment).number =
                numberProductInFavorites
        }
        // set badge for Cart
        viewModel.showCountCartLiveData.observe(this) { numberProductInCarts ->
            setupBadgeFAB(numberProductInCarts)
        }
    }

    private val badgeDrawable by lazy { BadgeDrawable.create(this@MainActivity) }
    private fun setupBadgeFAB(alerts: Int) {
        binding.floatActionButton.viewTreeObserver.addOnGlobalLayoutListener(@ExperimentalBadgeUtils object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.floatActionButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
                badgeDrawable.number = alerts
                badgeDrawable.isVisible = true
                BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.floatActionButton, null)
            }
        })
    }
}