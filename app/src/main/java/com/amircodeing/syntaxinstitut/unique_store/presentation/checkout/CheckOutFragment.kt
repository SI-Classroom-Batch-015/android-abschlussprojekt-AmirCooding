package com.amircodeing.syntaxinstitut.unique_store.presentation.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentCheckOutBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class CheckOutFragment : Fragment() {
    private lateinit var binding: FragmentCheckOutBinding
    private val viewModel: CheckOutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        viewModel.getUserInfo()
        viewModel.getCartItems()
        CustomToolbar.setToolbar(
            ToolbarComponents(
                view = binding.root,
                title = "Check Out",
                visibility = false,
                backButtonVisibility = false, path = R.id.toolbar_checkOut,
                icon = null
            )
        )
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            val adapter = CheckOutAdapter()
            viewModel.items.observe(viewLifecycleOwner) { cart ->
                binding.subTotalPaymentTV.text =
                    String.format("%.2f €", viewModel.items.value?.subTotal)
                binding.shippingPrice.text =
                    String.format("%.2f €", viewModel.items.value?.shippingPrice)
                binding.totalPricePaymentTV.text =
                    String.format("%.2f €", viewModel.items.value?.totalCost)
                binding.recyclerView.adapter = adapter
                adapter.submitList(cart.items)

            }
            viewModel.user.observe(viewLifecycleOwner) { info ->
                paymentFullNameTV.text = info.fullName
                paymentStreetTV.text = info.address?.street
                paymentNumberTV.text = info.address?.number
                paymentZipTV.text = info.address?.zip
                paymentCityTV.text = info.address?.city
                paymentCountryTV.text = info.address?.country


            }
        }
    }
}



