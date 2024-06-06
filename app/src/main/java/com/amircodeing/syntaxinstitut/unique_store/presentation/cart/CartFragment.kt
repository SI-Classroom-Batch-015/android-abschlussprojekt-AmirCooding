package com.amircodeing.syntaxinstitut.unique_store.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        val adapter = CartAdapter(viewModel)
        binding.cartRV.adapter = adapter
        viewModel.showCart.observe(viewLifecycleOwner) { cart ->
            if (cart != null) {
                adapter.submitList(cart.items)
                binding.totalPrice.text = String.format("%.2f", cart.totalCost)
                binding.transferPriceTV.text = String.format("%.2f", cart.shippingPrice)
                binding.subtotalPriceCartTV.text = String.format("%.2f", cart.subTotal)
            } else {
                adapter.submitList(emptyList())
                binding.totalPrice.text = "0.00"
                binding.transferPriceTV.text = "0.00"
                binding.subtotalPriceCartTV.text = "0.00"
            }
        }
        binding.customButtonMyCart.setOnClickListener {
            viewModel.showCart.value?.let { cart ->
                if (cart.items.isEmpty()) {
                    Toast.makeText(context, "Cart is Empty", Toast.LENGTH_SHORT).show()
                } else {
                    val navController = Navigation.findNavController(binding.root)
                    navController.navigate(R.id.checkOutFragment)
                }
            } ?: run {
                Toast.makeText(context, "Unable to retrieve cart information", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
