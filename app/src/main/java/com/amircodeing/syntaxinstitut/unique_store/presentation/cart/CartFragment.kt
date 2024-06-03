package com.amircodeing.syntaxinstitut.unique_store.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentCartBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class CartFragment : Fragment(R.layout.fragment_cart) {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        viewModel.getAllProductFromFirebase()
        CustomToolbar.setToolbar(
            ToolbarComponents(
                view = binding.root,
                title = "Cart",
                visibility = false,
                backButtonVisibility = false,
                path = R.id.toolbar_cart,
                icon = null
            )
        )
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(viewModel)
        binding.cartRV.adapter = cartAdapter

        viewModel.showCart.observe(viewLifecycleOwner) { carts ->
            cartAdapter.submitList(carts.flatMap { it.items })
            viewModel.calculateTotals(carts)

        }
        viewModel.totalCart.observe(viewLifecycleOwner) { cart ->
            binding.subtotalPriceCartTV.text = String.format("%.2f", cart.subTotal)
            binding.totalPrice.text = String.format("%.2f", cart.totalCost)
            binding.transferPriceTV.text = String.format("%.2f", cart.shippingPrice)
        }

        binding.customButtonMyCart.setOnClickListener {
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.checkOutFragment)
        }

    }
}
