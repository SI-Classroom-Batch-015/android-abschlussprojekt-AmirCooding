package com.amircodeing.syntaxinstitut.unique_store.presentation.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentFavoriteBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding : FragmentFavoriteBinding
    private  val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentFavoriteBinding .inflate(inflater, container, false)
        CustomToolbar.setToolbar(ToolbarComponents(view = binding.root, title = "Favorite", visibility = false,
            backButtonVisibility = true,
            path = R.id.toolbar_favorite,
            icon = null
        ))
        return  binding.root
    }



}