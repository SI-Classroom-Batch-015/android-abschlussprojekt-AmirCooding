package com.amircodeing.syntaxinstitut.unique_store.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentFavoriteBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.BottomNavController
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.amircodeing.syntaxinstitut.unique_store.utils.setToolbar

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding : FragmentFavoriteBinding
    private  val viewModel: FavoriteViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding .inflate(inflater, container, false)
        viewModel.getAllFavorite()
        setToolbar(ToolbarComponents(view = binding.root, title = "Favorite", visibility = false,
            backButtonVisibility = true,
            path = R.id.toolbar_favorite,
            icon = null
        ))
        return  binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showFavorites.observe(viewLifecycleOwner){
                favorites ->
            if (favorites.isNotEmpty()){
            binding.favoriteListRV.adapter = FavoriteAdapter(favorites , viewModel )
                binding.favoriteListRV.visibility = View.VISIBLE
              binding.emptyCart.visibility = View.GONE

            }else{
                binding.favoriteListRV.visibility = View.GONE
             binding.emptyCart.visibility = View.VISIBLE

            }

        }


    }

    override fun onResume() {
        super.onResume()
        activity?.let { BottomNavController.visibilityNavButton(it) }
    }


}


