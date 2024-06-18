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
    val badgeDrawable by lazy { BadgeDrawable.create(this) }
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
        viewModel.showCountFavoritesLiveData.observe(this) { numberProductInFavorites ->
            setFavoritesBadge(numberProductInFavorites)
        }
        viewModel.showCountCartLiveData.observe(this) { numberProductInCarts ->
            setCartsBadgeFAB(numberProductInCarts)
        }

    }

    private fun setFavoritesBadge(count: Int) {
        Log.d(TAG, "Number of products in favorites: $count")
        val badge = binding.buttonNav.getOrCreateBadge(R.id.favoriteFragment)
        badge.number = count
        badge.isVisible = count > 0
    }


     fun setCartsBadgeFAB(count : Int) {
        binding.floatActionButton.viewTreeObserver.addOnGlobalLayoutListener(@ExperimentalBadgeUtils object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                    binding.floatActionButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    badgeDrawable.number = count
                    badgeDrawable.isVisible = count > 0
                    BadgeUtils.attachBadgeDrawable(
                        badgeDrawable,
                        binding.floatActionButton,
                        null
                    )
            }
        })
    }
}
