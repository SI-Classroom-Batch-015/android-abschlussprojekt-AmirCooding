package com.amircodeing.syntaxinstitut.unique_store.presentation.cart

import CartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentCartBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class CartFragment : Fragment(R.layout.fragment_cart) {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()

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

        viewModel.users.observe(viewLifecycleOwner) { users ->
            if (users != null && users.isNotEmpty()) {
                val currentUser = users[0]  // Assuming the first user for now
                binding.subtotalPriceCartTV.text = currentUser.cart?.subTotal.toString() +" €"
                binding.totalPriceCartTV.text = currentUser.cart?.totalCost.toString() + " €"
                binding.cartRV.adapter = currentUser.cart?.items?.let {
                    CartAdapter(it, currentUser.id, viewModel)
                }
            }
        }
    }
}
