package com.amircodeing.syntaxinstitut.unique_store.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.ActivityMainBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils

const val TAG = "Main Activity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHost.navController
        binding.buttonNav.setupWithNavController(navController)

        binding.floatActionButton.setOnClickListener {
            navController.navigate(R.id.cartFragment)
        }

        // Call badges method here
        /*   callBadges() */
    }

    fun setupBadgeFavorite() {
        try {
            viewModel.showCountFavoritesLiveData.observe(this) { numberProductInFavorites ->
                Log.d(TAG, "Number of products in favorites: $numberProductInFavorites")
                val badge = binding.buttonNav.getOrCreateBadge(R.id.favoriteFragment)
                badge.number = numberProductInFavorites
                badge.isVisible = numberProductInFavorites > 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception in setupBadgeFavorite: ${e.message}")
        }
    }


    fun setupBadgeFAB() {
        val badgeDrawable by lazy { BadgeDrawable.create(this@MainActivity) }
        viewModel.showCountCartLiveData.observe(this) { numberProductInCarts ->
            Log.d(TAG, "Number of products in cart: $numberProductInCarts")
            binding.floatActionButton.viewTreeObserver.addOnGlobalLayoutListener(@ExperimentalBadgeUtils object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    try {
                        binding.floatActionButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        badgeDrawable.number = numberProductInCarts
                        badgeDrawable.isVisible = numberProductInCarts > 0
                        BadgeUtils.attachBadgeDrawable(
                            badgeDrawable,
                            binding.floatActionButton,
                            null
                        )
                        Log.d(TAG, "Badge set up with $numberProductInCarts alerts")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG, "Exception in setupBadgeFAB: ${e.message}")
                    }
                }
            })
        }
    }
}
